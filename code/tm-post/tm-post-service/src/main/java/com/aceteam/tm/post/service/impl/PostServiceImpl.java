package com.aceteam.tm.post.service.impl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: some desc
 * @author: haoran
 */
@Slf4j
@Component
@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostPoMapper postPoMapper;

    @Autowired
    private PostPoExMapper postPoExMapper;

    @Autowired
    private PostLabelService postLabelService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private CommentService commentService;

    @Reference
    private UserService userService;

    @Reference
    private LikeService likeService;

    @Reference
    private VisitService visitService;

    @Reference
    private FollowService followService;

    @Reference
    private UserLevelService userLevelService;

    @Reference
    private FileService fileService;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Integer contentMax = 200;

    /**
     * Get all reviewed posts
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<PostDTO> getPassAll(LocalDateTime startTime, LocalDateTime endTime) {
        PostPoExample example = new PostPoExample();
        example.createCriteria().andIsDeletedEqualTo(false)
                .andCreateTimeBetween(startTime, endTime)
                .andStateEqualTo(PostStateEnum.enable.getCode());
        return PostMS.INSTANCE.toDTO(postPoMapper.selectByExample(example));
    }

    /**
     * Get posts
     *
     * @param postSearchDTO
     * @param currentUser
     * @param postStateEnum
     * @return
     */
    @Override
    public PageInfo<PostDTO> getList(PostSearchDTO postSearchDTO, UserSsoDTO currentUser, PostStateEnum postStateEnum) {
        List<Integer> postIds = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(postSearchDTO.getLabelIds())) {
            // Get tag information based on tag id collection
            List<PostLabelDTO> postLabelDTOS = postLabelService.getByLabelIds(postSearchDTO.getLabelIds());
            if (CollectionUtils.isNotEmpty(postLabelDTOS)) {
                postIds = postLabelDTOS.stream().map(PostLabelDTO::getPostId).collect(Collectors.toList());
            } else {
                // No post of this tag
                return new PageInfo<>(new ArrayList<>());
            }
        }

        PostPoExample example = new PostPoExample();
        PostPoExample.Criteria criteria = example.createCriteria()
                .andIsDeletedEqualTo(false);
        if (postStateEnum != null) {
            criteria.andStateEqualTo(postStateEnum.getCode());
        }
        if (postSearchDTO.getId() != null) {
            criteria.andIdEqualTo(postSearchDTO.getId());
        }
        if (StringUtils.isNotBlank(postSearchDTO.getTitle())) {
            criteria.andTitleLike("%" + postSearchDTO.getTitle() + "%");
        }
        if (CollectionUtils.isNotEmpty(postIds)) {
            criteria.andIdIn(postIds);
        }
        if (postSearchDTO.getCreateUser() != null) {
            criteria.andCreateUserEqualTo(postSearchDTO.getCreateUser());
        }
        example.setOrderByClause("top desc, create_time desc, `id` desc");

        PageHelper.startPage(postSearchDTO.getCurrentPage(), postSearchDTO.getPageSize());
        List<PostPo> postPos = postPoMapper.selectByExample(example);
        PageInfo<PostDTO> pageInfo = PostMS.INSTANCE.toPage(new PageInfo<>(postPos));
        if (CollectionUtils.isEmpty(pageInfo.getList())) {
            return pageInfo;
        }

        // Build post information
        buildPostInfo(pageInfo.getList(), currentUser);

        return pageInfo;
    }

    @Override
    public Long getUserPostCount(Long createUser, PostStateEnum postStateEnum) {
        PostPoExample example = new PostPoExample();
        PostPoExample.Criteria criteria = example.createCriteria()
                .andIsDeletedEqualTo(false)
                .andCreateUserEqualTo(createUser);
        if (postStateEnum != null) {
            criteria.andStateEqualTo(postStateEnum.getCode());
        }

        return postPoMapper.countByExample(example);
    }

    /**
     * Get posts for review
     *
     * @param postSearchDTO
     * @param currentUser
     * @return
     */
    @Override
    public PageInfo<PostDTO> getPendingReviewPosts(PostSearchDTO postSearchDTO, UserSsoDTO currentUser) {
        return this.getList(postSearchDTO, currentUser, PostStateEnum.pendingReview);
    }

    /**
     * Get Disabled Posts
     *
     * @param postSearchDTO
     * @param currentUser
     * @return
     */
    @Override
    public PageInfo<PostDTO> getDisabledPosts(PostSearchDTO postSearchDTO, UserSsoDTO currentUser) {
        return this.getList(postSearchDTO, currentUser, PostStateEnum.disabled);
    }

    /**
     * Modify Post Approval Status
     *
     * @param postDTO
     * @param currentUser
     * @return
     */
    @Override
    public Boolean updateState(PostDTO postDTO, UserSsoDTO currentUser) {
        PostPo postPo = new PostPo();
        postPo.setId(postDTO.getId());
        postPo.setUpdateTime(LocalDateTime.now());
        if (PostStateEnum.pendingReview.getCode().equals(postDTO.getState())) {
            postPo.setState(PostStateEnum.pendingReview.getCode());
        }
        if (PostStateEnum.disabled.getCode().equals(postDTO.getState())) {
            postPo.setState(PostStateEnum.disabled.getCode());
        }
        if (PostStateEnum.enable.getCode().equals(postDTO.getState())) {
            postPo.setState(PostStateEnum.enable.getCode());
        }

        if (postPoMapper.updateByPrimaryKeySelective(postPo) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "修改文章审批状态失败");
        }

        return true;
    }

    /**
     * Get Liked Posts
     *
     * @param likeSearchDTO
     * @param currentUser
     * @return
     */
    @Override
    public PageInfo<PostDTO> getLikesPost(LikeSearchDTO likeSearchDTO, UserSsoDTO currentUser) {
        PageInfo<PostDTO> pageInfo = new PageInfo<>();
        PageInfo<LikeDTO> likeDTOPageInfo = likeService.getPostByUserId(likeSearchDTO);
        BeanUtils.copyProperties(likeDTOPageInfo, pageInfo);
        if (CollectionUtils.isNotEmpty(likeDTOPageInfo.getList())) {
            List<Integer> postIds = likeDTOPageInfo.getList().stream().distinct().map(LikeDTO::getPostId).collect(Collectors.toList());
            List<PostDTO> postDTOS = getBaseByIds(postIds, PostStateEnum.enable);

            // Build post information
            buildPostInfo(postDTOS, currentUser);
            pageInfo.setList(postDTOS);
        }

        return pageInfo;
    }

    /**
     * Get post information from article id collection
     *
     * @param ids
     * @param isPv        Whether to increase the number of post views
     * @param currentUser
     * @return
     */
    @Override
    public List<PostDTO> getByIds(List<Integer> ids, Boolean isPv, UserSsoDTO currentUser) {
        List<PostDTO> postDTOS = getBaseByIds(ids, null);
        if (CollectionUtils.isEmpty(postDTOS)) {
            return postDTOS;
        }

        // Build post information
        buildPostInfo(postDTOS, currentUser);

        // Get content
        List<PostMarkdownInfo> postMarkdownInfos = getMarkdownByPostIds(ids);
        Map<Integer, List<PostMarkdownInfo>> postId2List = postMarkdownInfos.stream().collect(Collectors.groupingBy(PostMarkdownInfo::getPostId));
        postDTOS.forEach(postDTO -> {
            if (postId2List.containsKey(postDTO.getId())) {
                PostMarkdownInfo postMarkdownInfo = postId2List.get(postDTO.getId()).get(0);
                postDTO.setMarkdown(postMarkdownInfo.getPostMarkdown());
                postDTO.setHtml(postMarkdownInfo.getPostHtml());
            }
        });

        // Increase the number of views
        if (isPv != null && isPv) {
            this.updatePv(postDTOS.get(0));
        }

        return postDTOS;
    }

    /**
     * Get information (the most basic information) through the post id collection
     *
     * @param ids
     * @return
     */
    @Override
    public List<PostDTO> getBaseByIds(List<Integer> ids, PostStateEnum postStateEnum) {
        PostPoExample example = new PostPoExample();
        PostPoExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        if (postStateEnum != null) {
            criteria.andStateEqualTo(postStateEnum.getCode());
        }
        // Sort by increasing order
        String stringIds = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        example.setOrderByClause("field(id, " + stringIds + ")");

        return PostMS.INSTANCE.toDTO(postPoMapper.selectByExample(example));
    }

    /**
     * Writing posts (without pic)
     *
     * @param postDTO
     * @param currentUser
     * @return
     */
    @Override
    public Boolean create(PostDTO postDTO, List<Integer> labelIds, UserSsoDTO currentUser) {
        if (StringUtils.isBlank(postDTO.getTitle()) || StringUtils.isBlank(postDTO.getHtml())) {
            throw BusinessException.build(ResponseCode.NOT_EXISTS, "参数不合规");
        }
        postDTO.setIsDeleted(false);
        String content = CommonUtils.html2Text(postDTO.getHtml());
        postDTO.setContent(content.length() < contentMax ? content : content.substring(0, contentMax));
        postDTO.setCreateUser(currentUser.getUserId());
        postDTO.setUpdateUser(currentUser.getUserId());
        LocalDateTime now = LocalDateTime.now();
        postDTO.setCreateTime(now);
        postDTO.setUpdateTime(now);
        // Enabled only for posts that have passed review (i.e.: pending review by default)
        postDTO.setState(PostStateEnum.pendingReview.getCode());
        PostPo postPo = PostMS.INSTANCE.toPo(postDTO);
        if (postPoMapper.insertSelective(postPo) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "Failure to write posts!");
        }
        // Added file label relationship information
        postLabelService.create(labelIds, postPo.getId(), currentUser);

        // Insert post content (mongo)
        insertPostContent(postPo.getId(), postDTO.getMarkdown(), postDTO.getHtml(), currentUser.getUserId(), now);

        return true;
    }

    /**
     * Updated post (no pictures)
     *
     * @param postDTO
     * @param currentUser
     * @return
     */
    @Override
    public Boolean update(PostDTO postDTO, List<Integer> labelIds, UserSsoDTO currentUser) {
        if (StringUtils.isBlank(postDTO.getTitle()) || StringUtils.isBlank(postDTO.getHtml())) {
            throw BusinessException.build(ResponseCode.NOT_EXISTS, "参数不合规");
        }
        PostPo oldPostPo = postPoMapper.selectByPrimaryKey(postDTO.getId());
        // Only update your own posts
        if (!currentUser.getUserId().equals(oldPostPo.getCreateUser())) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "无法更新，只能更新自己撰写的文章！");
        }

        String content = CommonUtils.html2Text(postDTO.getHtml());
        postDTO.setContent(content.length() < contentMax ? content : content.substring(0, contentMax));
        LocalDateTime now = LocalDateTime.now();
        postDTO.setUpdateTime(now);
        postDTO.setUpdateUser(currentUser.getUserId());
        postDTO.setState(PostStateEnum.pendingReview.getCode());
        PostPo postPo = PostMS.INSTANCE.toPo(postDTO);
        if (postPoMapper.updateByPrimaryKeySelective(postPo) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "更新文章失败");
        }
        // Update file tag relationship information
        postLabelService.update(labelIds, postPo.getId(), currentUser);

        // Update post content (mongo)
        updatePostContent(postPo.getId(), postDTO.getMarkdown(), postDTO.getHtml(), currentUser.getUserId(), now);

        return true;
    }

    /**
     * write a post
     *
     * @param bytes
     * @param sourceFileName
     * @param postDTO
     * @param currentUser
     * @return
     */
    @Override
    public Boolean create(byte[] bytes, String sourceFileName, PostDTO postDTO, List<Integer> labelIds, UserSsoDTO currentUser) {
        try {
            // File upload (scaled compression)
            String picture = fileService.fileScaleUpload(bytes, sourceFileName, ImageTypeEnum.postTitleMap.name());
            postDTO.setTitleMap(picture);
            create(postDTO, labelIds, currentUser);
        } catch (Exception e) {
            log.error("Compose posts exceptionally!", e);
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "Compose posts exceptionally!");
        }

        return null;
    }

    /**
     * Updated Posts
     *
     * @param bytes
     * @param sourceFileName
     * @param postDTO
     * @param currentUser
     * @return
     */
    @Override
    public Boolean update(byte[] bytes, String sourceFileName, PostDTO postDTO, List<Integer> labelIds, UserSsoDTO currentUser) {
        try {
            // File upload (scaled compression)
            String picture = fileService.fileScaleUpload(bytes, sourceFileName, ImageTypeEnum.postTitleMap.name());
            postDTO.setTitleMap(picture);
            update(postDTO, labelIds, currentUser);
        } catch (Exception e) {
            log.error("Compose Posts exceptionally!", e);
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "Compose Posts exceptionally!");
        }

        return null;
    }

    /**
     * Upload a picture (one)- mavonEditor
     *
     * @param bytes
     * @param sourceFileName
     * @return
     */
    @Override
    public String uploadPicture(byte[] bytes, String sourceFileName) {
        // File upload (scaled compression)
        return fileService.fileScaleUpload(bytes, sourceFileName, ImageTypeEnum.postPicture.name());
    }

    /**
     * Get the total number of article comment visits
     *
     * @return
     */
    @Override
    public TotalDTO getPostCommentVisitTotal() {
        TotalDTO totalDTO = new TotalDTO();
        totalDTO.setPostCount(getTotal());
        totalDTO.setCommentCount(commentService.getTotal());
        totalDTO.setVisitCount(visitService.getTotal());
        return totalDTO;
    }

    /**
     * Get the number of posts
     *
     * @return
     */
    @Override
    public Long getTotal() {
        PostPoExample example = new PostPoExample();
        example.createCriteria().andIsDeletedEqualTo(false)
                .andStateEqualTo(PostStateEnum.enable.getCode());
        return postPoMapper.countByExample(example);
    }

    /**
     * Get the number of users reading
     *
     * @param userIds
     * @return
     */
    @Override
    public List<PostReadDTO> getUserReadCount(List<Long> userIds) {
        return postPoExMapper.selectUserReadCount(userIds);
    }

    /**
     * Get some stats on the posts
     *
     * @param id
     * @param currentUser
     * @return
     */
    @Override
    public PostCountDTO getCountById(Integer id, UserSsoDTO currentUser) {
        // Get post information
        PostPo postPo = postPoMapper.selectByPrimaryKey(id);
        PostCountDTO postCountDTO = new PostCountDTO();
        // Get the number of post likes
        postCountDTO.setLikeCount(likeService.getLikeCountPost(Collections.singletonList(id)));
        // Whether or not it has been liked, get followers via fromUser and toUser
        if (currentUser != null) {
            postCountDTO.setIsLike(likeService.isLike(id, currentUser.getUserId()));
            FollowDTO followDTO = followService.getByFromToUser(currentUser.getUserId(), postPo.getCreateUser(), false);
            if (followDTO != null) {
                postCountDTO.setIsFollow(true);
            }
        }
        // Get the number of post comments
        postCountDTO.setCommentCount(commentService.getCommentCountByPost(id));
        // Get User Levels
        List<UserLevelDTO> userLevelDTOS = userLevelService.getByUserId(postPo.getCreateUser());
        if (CollectionUtils.isNotEmpty(userLevelDTOS)) {
            postCountDTO.setLevel(userLevelDTOS.get(0).getLevel());
        }

        return postCountDTO;
    }

    /**
     * Increase the number of post views
     *
     * @param postDTO
     * @return
     */
    @Override
    public Boolean updatePv(PostDTO postDTO) {
        PostPo postPo = PostMS.INSTANCE.toPo(postDTO);
        postPo.setPv(postPo.getPv() + 1);
        if (postPoMapper.updateByPrimaryKeySelective(postPo) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "Failed to increase post views");
        }
        return true;
    }

    /**
     * Getting post information through users
     *
     * @param userId
     * @return
     */
    @Override
    public List<PostDTO> getByUserId(Long userId) {
        PostPoExample example = new PostPoExample();
        example.createCriteria().andIsDeletedEqualTo(false)
                .andStateEqualTo(PostStateEnum.enable.getCode())
                .andCreateUserEqualTo(userId);

        return PostMS.INSTANCE.toDTO(postPoMapper.selectByExample(example));
    }

    @Override
    public Boolean postTop(Integer id, Boolean top, UserSsoDTO currentUser) {
        PostPo postPo = postPoMapper.selectByPrimaryKey(id);
        postPo.setUpdateTime(LocalDateTime.now());
        postPo.setUpdateUser(currentUser.getUserId());
        // pin to top
        if (top) {
            Integer maxTop = this.getMaxTop();
            if (maxTop != null) {
                postPo.setTop(maxTop + 1);
                if (postPoMapper.updateByPrimaryKey(postPo) <= 0) {
                    throw BusinessException.build(ResponseCode.OPERATE_FAIL, "Failed to pin the post top");
                }
            }
        } else {
            // Unpin
            postPo.setTop(null);
            if (postPoMapper.updateByPrimaryKey(postPo) <= 0) {
                throw BusinessException.build(ResponseCode.OPERATE_FAIL, "Failed to unpin post");
            }
        }

        return true;
    }

    @Override
    public Integer getMaxTop() {
        PostPoExample example = new PostPoExample();
        example.setOrderByClause("top desc limit 1");
        List<PostPo> postPos = postPoMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(postPos)) {
            return postPos.get(0).getTop();
        }

        return null;
    }

    @Override
    public Boolean delete(Integer id, UserSsoDTO currentUser) {
        PostPo postPo = postPoMapper.selectByPrimaryKey(id);
        // 只能删除自己的文章
        if (!currentUser.getUserId().equals(postPo.getCreateUser())) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "It can't be deleted, only posts that you have written!");
        }
        postPo.setIsDeleted(true);
        postPo.setUpdateTime(LocalDateTime.now());
        postPo.setUpdateUser(currentUser.getUserId());
        if (postPoMapper.updateByPrimaryKeySelective(postPo) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "Post Deletion Failed");
        }

        return true;
    }

    @Override
    public PostCheckCountDTO getPostCheckCount(String title) {
        PostCheckCountDTO postCheckCountDTO = new PostCheckCountDTO();
        List<Map<String, Object>> list = postPoExMapper.selectPostCheckCount(title);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(stringLongMap -> {
                Integer state = Integer.valueOf(stringLongMap.get("state").toString());
                Long num = Long.valueOf(stringLongMap.get("num").toString());
                if (PostStateEnum.enable.getCode().equals(state)) {
                    postCheckCountDTO.setEnableCount(num);
                }
                if (PostStateEnum.disabled.getCode().equals(state)) {
                    postCheckCountDTO.setDisabledCount(num);
                }
                if (PostStateEnum.pendingReview.getCode().equals(state)) {
                    postCheckCountDTO.setPendingReviewCount(num);
                }
            });
        }

        return postCheckCountDTO;
    }

    @Override
    public List<GoodsDTO> getGoods() {
        ExcelReader reader = ExcelUtil.getReader("./data/goods.xlsx");
        return reader.readAll(GoodsDTO.class);
    }

    /**
     * Get post id to tag information mapping
     *
     * @param postIds
     * @return
     */
    private Map<Integer, List<LabelDTO>> getPostIdToLabels(List<Integer> postIds) {
        // Mapping of post id to tag info (key:post id, value:tag info)
        Map<Integer, List<LabelDTO>> postToLabelMap = new HashMap<>();

        List<PostLabelDTO> postLabelDTOS = postLabelService.getByPostIds(postIds);
        if (CollectionUtils.isNotEmpty(postLabelDTOS)) {
            // Get a collection of all tag ids
            List<Integer> labelIds = postLabelDTOS.stream().distinct().map(PostLabelDTO::getLabelId).collect(Collectors.toList());
            Map<Integer, List<PostLabelDTO>> postIdKeyMap = postLabelDTOS.stream().collect(Collectors.groupingBy(PostLabelDTO::getPostId));
            List<LabelDTO> labelDTOS = labelService.getByIds(labelIds);
            if (CollectionUtils.isNotEmpty(labelDTOS)) {
                Map<Integer, List<LabelDTO>> labelMap = labelDTOS.stream().collect(Collectors.groupingBy(LabelDTO::getId));
                postIdKeyMap.forEach((k, v) -> {
                    List<LabelDTO> result = new ArrayList<>();
                    v.forEach(postLabelDTO -> {
                        List<LabelDTO> labelDTOList = labelMap.get(postLabelDTO.getLabelId());
                        result.addAll(CollectionUtils.isNotEmpty(labelDTOList) ? labelDTOList : new ArrayList<>());
                    });
                    postToLabelMap.put(k, result);
                });
            }
        }

        return postToLabelMap;
    }

    /**
     * Build post information
     *
     * @param postDTOS
     * @param currentUser
     */
    private void buildPostInfo(List<PostDTO> postDTOS, UserSsoDTO currentUser) {
        // Get post id to tag information mapping
        List<Integer> postIds = postDTOS.stream().map(PostDTO::getId).collect(Collectors.toList());
        Map<Integer, List<LabelDTO>> postToLabelMap = getPostIdToLabels(postIds);

        // Get user information from the user id collection
        List<Long> userIds = postDTOS.stream().map(PostDTO::getCreateUser).collect(Collectors.toList());
        // User Basic Information
        Map<Long, List<UserDTO>> idUsers = userService.getByIds(userIds).stream().collect(Collectors.groupingBy(UserDTO::getId));
        // User level information
        Map<Long, List<UserLevelDTO>> userIdToUserLevel = userLevelService.getByUserIds(userIds).stream().collect(Collectors.groupingBy(UserLevelDTO::getUserId));
        // Get name by id
        postDTOS.forEach(postDTO -> {
            if (idUsers.containsKey(postDTO.getCreateUser())) {
                postDTO.setCreateUserName(idUsers.get(postDTO.getCreateUser()).get(0).getName());
                postDTO.setPicture(idUsers.get(postDTO.getCreateUser()).get(0).getPicture());
            }
            if (userIdToUserLevel.containsKey(postDTO.getCreateUser())) {
                postDTO.setLevel(userIdToUserLevel.get(postDTO.getCreateUser()).get(0).getLevel());
            }

            if (!postToLabelMap.isEmpty()) {
                postDTO.setLabelDTOS(postToLabelMap.get(postDTO.getId()));
            }
            postDTO.setPostCountDTO(this.getCountById(postDTO.getId(), currentUser));
        });
    }

    /**
     * Insert post content (mongo)
     *
     * @param postId
     * @param markdown
     * @param html
     * @param userId
     * @param now
     */
    private void insertPostContent(Integer postId, String markdown, String html, Long userId, LocalDateTime now) {
        PostMarkdownInfo postMarkdownInfo = new PostMarkdownInfo();
        postMarkdownInfo.setPostId(postId);
        postMarkdownInfo.setPostMarkdown(markdown);
        postMarkdownInfo.setPostHtml(html);
        postMarkdownInfo.setUserId(userId);
        postMarkdownInfo.setTime(now);
        mongoTemplate.insert(Collections.singletonList(postMarkdownInfo), PostMarkdownInfo.class);
    }

    /**
     * Update post content (mongo)
     *
     * @param postId
     * @param markdown
     * @param html
     * @param userId
     * @param now
     */
    private void updatePostContent(Integer postId, String markdown, String html, Long userId, LocalDateTime now) {
        Query query = new Query(Criteria.where("postId").is(postId));
        Update update = new Update();
        update.set("postMarkdown", markdown);
        update.set("postHtml", html);
        update.set("userId", userId);
        update.set("time", now);
        mongoTemplate.updateMulti(query, update, PostMarkdownInfo.class);
    }

    /**
     * Get post content from post id collection
     *
     * @param postIds
     * @return
     */
    private List<PostMarkdownInfo> getMarkdownByPostIds(List<Integer> postIds) {
        Query query = new Query(Criteria.where("postId").in(postIds));
        return mongoTemplate.find(query, PostMarkdownInfo.class);
    }
}
