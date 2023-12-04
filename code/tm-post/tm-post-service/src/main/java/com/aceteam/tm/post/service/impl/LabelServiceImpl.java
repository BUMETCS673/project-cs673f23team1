package com.aceteam.tm.post.service.impl;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
@Slf4j
@Component
@Service
public class LabelServiceImpl implements LabelService{
    @Autowired
    private LabelPoMapper labelPoMapper;

    @Autowired
    private PostLabelService postLabelService;

    @Reference
    private FileService fileService;

    /**
     * Get Tags
     *
     * @param labelSearchDTO
     * @return
     */
    @Override
    public PageInfo<LabelDTO> getList(LabelSearchDTO labelSearchDTO) {
        LabelPoExample example = new LabelPoExample();
        LabelPoExample.Criteria criteria = example.createCriteria().andIsDeletedEqualTo(false);
        if (labelSearchDTO.getId() != null) {
            criteria.andIdEqualTo(labelSearchDTO.getId());
        }
        if (StringUtils.isNotBlank(labelSearchDTO.getLabelName())) {
            criteria.andLabelNameLike("%" + labelSearchDTO.getLabelName() + "%");
        }
        example.setOrderByClause("`id` desc");

        PageHelper.startPage(labelSearchDTO.getCurrentPage(), labelSearchDTO.getPageSize());
        PageInfo<LabelDTO> pageInfo = LabelMS.INSTANCE.toPage(new PageInfo<>(labelPoMapper.selectByExample(example)));
        pageInfo.getList().forEach(labelDTO -> {
            labelDTO.setPostUseCount(postLabelService.getCountByLabelId(labelDTO.getId()));
        });

        return pageInfo;
    }

    /**
     * Get tag information through the tag id collection
     *
     * @param ids
     * @return
     */
    @Override
    public List<LabelDTO> getByIds(List<Integer> ids) {
        LabelPoExample example = new LabelPoExample();
        example.createCriteria().andIsDeletedEqualTo(false).andIdIn(ids);
        return LabelMS.INSTANCE.toDTO(labelPoMapper.selectByExample(example));
    }

    /**
     * Add Tags
     *
     * @param labelDTO
     * @param currentUser
     * @return
     */
    @Override
    public Boolean create(LabelDTO labelDTO, UserSsoDTO currentUser) {
        if (StringUtils.isBlank(labelDTO.getLabelName())) {
            throw BusinessException.build(ResponseCode.NOT_EXISTS, "参数不合规");
        }
        if (isNameExist(null, labelDTO.getLabelName())) {
            throw BusinessException.build(ResponseCode.NAME_EXIST, "标签名重复");
        }

        labelDTO.setIsDeleted(false);
        labelDTO.setCreateUser(currentUser.getUserId());
        labelDTO.setUpdateUser(currentUser.getUserId());
        LocalDateTime now = LocalDateTime.now();
        labelDTO.setCreateTime(now);
        labelDTO.setUpdateTime(now);
        LabelPo labelPo = LabelMS.INSTANCE.toPo(labelDTO);
        if (labelPoMapper.insertSelective(labelPo) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "新增标签失败");
        }

        return true;
    }

    /**
     * Upload tag logo
     *
     * @param bytes
     * @param sourceFileName
     * @return
     */
    @Override
    public String uploadLabelLogo(byte[] bytes, String sourceFileName) {
        try {
            // File upload (cut)
            return fileService.fileCutUpload(bytes, sourceFileName, ImageTypeEnum.labelPicture.name());
        } catch (Exception e) {
            log.error("Label Logo Upload Exception!", e);
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "Label Logo Upload Exception!");
        }
    }

    /**
     * Update Tags
     *
     * @param labelDTO
     * @param currentUser
     * @return
     */
    @Override
    public Boolean update(LabelDTO labelDTO, UserSsoDTO currentUser) {
        if (StringUtils.isBlank(labelDTO.getLabelName())) {
            throw BusinessException.build(ResponseCode.NOT_EXISTS, "Parameters are not compliant");
        }
        if (isNameExist(labelDTO.getId(), labelDTO.getLabelName())) {
            throw BusinessException.build(ResponseCode.NAME_EXIST, "Duplicate tag names");
        }
        labelDTO.setIsDeleted(null);
        labelDTO.setCreateUser(null);
        labelDTO.setUpdateUser(currentUser.getUserId());
        labelDTO.setCreateTime(null);
        labelDTO.setUpdateTime(LocalDateTime.now());
        LabelPo labelPo = LabelMS.INSTANCE.toPo(labelDTO);
        if (labelPoMapper.updateByPrimaryKeySelective(labelPo) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "Failed to update tags");
        }

        return true;
    }

    /**
     * Delete Tags
     *
     * @param id
     * @return
     */
    @Override
    public Boolean delete(Integer id) {
        LabelPo labelPo = new LabelPo();
        labelPo.setId(id);
        labelPo.setIsDeleted(true);
        if (labelPoMapper.updateByPrimaryKeySelective(labelPo) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "Failed to delete");
        }

        return true;
    }

    /**
     * Determine if the tag name already exists
     *
     * @param labelId
     * @param labelName
     * @return
     */
    private boolean isNameExist(Integer labelId, String labelName) {
        LabelPoExample example = new LabelPoExample();
        example.createCriteria().andIsDeletedEqualTo(false)
                .andLabelNameEqualTo(labelName);
        List<LabelPo> labelPos = labelPoMapper.selectByExample(example);
        if (labelPos.size() > 1) {
            return true;
        } else if (labelPos.size() == 1) {
            // The labelId has a value when updated.
            return !labelPos.get(0).getId().equals(labelId);
        }
        return false;
    }
}
