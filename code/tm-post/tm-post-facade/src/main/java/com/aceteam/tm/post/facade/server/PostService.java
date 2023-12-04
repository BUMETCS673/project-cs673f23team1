package com.aceteam.tm.post.facade.server;

import com.aceteam.tm.post.facade.dto.*;
import com.aceteam.tm.post.facade.dto.PostDTO;
import com.acteam.tm.user.facade.dto.LikeSearchDTO;
import com.github.pagehelper.PageInfo;
import com.liang.nansheng.common.auth.UserSsoDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
public interface PostService {

    /**
     * get All Pass Post
     * @param startTime
     * @param endTime
     * @return
     */
    List<PostDTO> getPassAll(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * get Post
     *
     * @param postSearchDTO
     * @param currentUser
     * @param postStateEnum
     * @return
     */
    PageInfo<PostDTO> getList(PostSearchDTO postSearchDTO, UserSsoDTO currentUser, PostStateEnum postStateEnum);

    /**
     * Get User Post Count
     *
     * @param createUser
     * @param postStateEnum
     * @return
     */
    Long getUserPostCount(Long createUser, PostStateEnum postStateEnum);

    /**
     * Get Pending Review Post
     *`
     * @param postSearchDTO
     * @param currentUser
     * @return
     */
    PageInfo<PostDTO> getPendingReviewPosts(PostSearchDTO postSearchDTO, UserSsoDTO currentUser);

    /**
     * Get Disabled Post
     *
     * @param postSearchDTO
     * @param currentUser
     * @return
     */
    PageInfo<PostDTO> getDisabledPosts(PostSearchDTO postSearchDTO, UserSsoDTO currentUser);

    /**
     * Get Post Status
     *
     * @param postDTO
     * @param currentUser
     * @return
     */
    Boolean updateState(PostDTO postDTO, UserSsoDTO currentUser);

    /**
     * Get Liked Post
     *
     * @param likeSearchDTO
     * @param currentUser
     * @return
     */
    PageInfo<PostDTO> getLikesPost(LikeSearchDTO likeSearchDTO, UserSsoDTO currentUser);

    /**
     * Get Post By Id
     *
     * @param ids
     * @param isPv Whether to increase the number of article views
     * @param currentUser
     * @return
     */
    List<PostDTO> getByIds(List<Integer> ids, Boolean isPv, UserSsoDTO currentUser);

    /**
     * Get Post By Id (Basic)
     *
     * @param ids
     * @return
     */
    List<PostDTO> getBaseByIds(List<Integer> ids, PostStateEnum postStateEnum);

    /**
     * Get Post By Id (No Picture)
     *
     * @param postDTO
     * @param currentUser
     */
    Boolean create(PostDTO postDTO, List<Integer> labelIds, UserSsoDTO currentUser);

    /**
     * 更新文章（无配图）
     * Update Post (No Picture)
     *
     * @param postDTO
     * @param currentUser
     */
    Boolean update(PostDTO postDTO, List<Integer> labelIds, UserSsoDTO currentUser);

    /**
     * Write posts
     *
     * @param bytes
     * @param sourceFileName
     * @param postDTO
     * @param currentUser
     */
    Boolean create(byte[] bytes, String sourceFileName, PostDTO postDTO, List<Integer> labelIds, UserSsoDTO currentUser);

    /**
     * update Post
     *
     * @param bytes
     * @param sourceFileName
     * @param postDTO
     * @param currentUser
     */
    Boolean update(byte[] bytes, String sourceFileName, PostDTO postDTO, List<Integer> labelIds, UserSsoDTO currentUser);

    /**
     * Upload Picture (One) - mavonEditor
     *
     * @param bytes
     * @param sourceFileName
     * @return
     */
    String uploadPicture(byte[] bytes, String sourceFileName);

    /**
     * Get Post Comment Visit Total
     *
     * @return
     */
    TotalDTO getPostCommentVisitTotal();

    /**
     * Get Post Count
     *
     * @return
     */
    Long getTotal();

    /**
     * Get User Read Count
     *
     * @param userIds
     * @return
     */
    List<PostReadDTO> getUserReadCount(List<Long> userIds);

    /**
     * Get Some Statistics Data of Post
     *
     * @param id
     * @param currentUser
     * @return
     */
    PostCountDTO getCountById(Integer id, UserSsoDTO currentUser);

    /**
     * Increase the number of article views
     *
     * @param postDTO
     */
    Boolean updatePv(PostDTO postDTO);

    /**
     * Get Post Information by User
     *
     * @param userId
     * @return
     */
    List<PostDTO> getByUserId(Long userId);

    /**
     * Post Top/Cancel Top
     *
     * @param id
     * @param top 是否置顶 Whether to top (true: top, false: cancel top)
     * @param currentUser
     * @return
     */
    Boolean postTop(Integer id, Boolean top, UserSsoDTO currentUser);

    /**
     * Get the maximum value of the top of the article
     *
     * @return
     */
    Integer getMaxTop();

    /**
     * delete
     *
     * @param id
     * @param currentUser
     * @return
     */
    Boolean delete(Integer id, UserSsoDTO currentUser);

    /**
     * Post Review Data Volume
     *
     * @param title
     * @return
     */
    PostCheckCountDTO getPostCheckCount(String title);
    
    
}
