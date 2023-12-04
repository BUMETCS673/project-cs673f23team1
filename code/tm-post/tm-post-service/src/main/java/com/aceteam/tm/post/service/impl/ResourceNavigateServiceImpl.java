package com.aceteam.tm.post.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: some desc
 * @author: haoran
 */
@Slf4j
@Component
@Service
public class ResourceNavigateServiceImpl implements ResourceNavigateService {
    @Autowired
    private ResourceNavigatePoMapper resourceNavigatePoMapper;

    @Reference
    private FileService fileService;

    /**
     * Access to resources navigation
     *
     * @param resourceNavigateSearchDTO
     * @return
     */
    @Override
    public PageInfo<ResourceNavigateDTO> getList(ResourceNavigateSearchDTO resourceNavigateSearchDTO) {
        ResourceNavigatePoExample example = new ResourceNavigatePoExample();
        ResourceNavigatePoExample.Criteria criteria = example.createCriteria().andIsDeletedEqualTo(false);
        if (StringUtils.isNotBlank(resourceNavigateSearchDTO.getCategory())) {
            criteria.andCategoryEqualTo(resourceNavigateSearchDTO.getCategory());
        }
        example.setOrderByClause("`id` desc");
        PageHelper.startPage(resourceNavigateSearchDTO.getCurrentPage(), resourceNavigateSearchDTO.getPageSize());

        return ResourceNavigateMS.INSTANCE.toPage(new PageInfo<>(resourceNavigatePoMapper.selectByExample(example)));
    }

    /**
     * Added resource navigation
     *
     * @param resourceNavigateDTO
     * @param currentUser
     * @return
     */
    @Override
    public Boolean create(ResourceNavigateDTO resourceNavigateDTO, UserSsoDTO currentUser) {
        if (StringUtils.isBlank(resourceNavigateDTO.getResourceName()) ||
                StringUtils.isBlank(resourceNavigateDTO.getCategory()) ||
                StringUtils.isBlank(resourceNavigateDTO.getDesc()) ||
                StringUtils.isBlank(resourceNavigateDTO.getLink())) {
            throw BusinessException.build(ResponseCode.NOT_EXISTS, "参数不合规");
        }
        if (isNameExist(null, resourceNavigateDTO.getResourceName())) {
            throw BusinessException.build(ResponseCode.NAME_EXIST, "资源导航名重复");
        }

        resourceNavigateDTO.setIsDeleted(false);
        resourceNavigateDTO.setCreateUser(currentUser.getUserId());
        resourceNavigateDTO.setUpdateUser(currentUser.getUserId());
        LocalDateTime now = LocalDateTime.now();
        resourceNavigateDTO.setCreateTime(now);
        resourceNavigateDTO.setUpdateTime(now);
        ResourceNavigatePo resourceNavigatePo = ResourceNavigateMS.INSTANCE.toPo(resourceNavigateDTO);
        if (resourceNavigatePoMapper.insertSelective(resourceNavigatePo) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "新增资源导航失败");
        }

        return true;
    }

    /**
     * Upload Resources Navigation logo
     *
     * @param bytes
     * @param sourceFileName
     * @return
     */
    @Override
    public String uploadResourceNavigateLogo(byte[] bytes, String sourceFileName) {
        try {
            // File upload (cut)
            return fileService.fileCutUpload(bytes, sourceFileName, ImageTypeEnum.resourceNavigatePicture.name());
        } catch (Exception e) {
            log.error("Resource Navigation Logo upload exception!", e);
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "Resource Navigation Logo upload exception!");
        }
    }

    /**
     * Updated resource navigation
     *
     * @param resourceNavigateDTO
     * @param currentUser
     * @return
     */
    @Override
    public Boolean update(ResourceNavigateDTO resourceNavigateDTO, UserSsoDTO currentUser) {
        if (StringUtils.isBlank(resourceNavigateDTO.getResourceName()) ||
                StringUtils.isBlank(resourceNavigateDTO.getCategory()) ||
                StringUtils.isBlank(resourceNavigateDTO.getDesc()) ||
                StringUtils.isBlank(resourceNavigateDTO.getLink())) {
            throw BusinessException.build(ResponseCode.NOT_EXISTS, "Parameter non-compliance");
        }
        if (isNameExist(resourceNavigateDTO.getId(), resourceNavigateDTO.getResourceName())) {
            throw BusinessException.build(ResponseCode.NAME_EXIST, "Duplicate resource navigation names");
        }
        resourceNavigateDTO.setIsDeleted(null);
        resourceNavigateDTO.setCreateUser(null);
        resourceNavigateDTO.setUpdateUser(currentUser.getUserId());
        resourceNavigateDTO.setCreateTime(null);
        resourceNavigateDTO.setUpdateTime(LocalDateTime.now());
        ResourceNavigatePo resourceNavigatePo = ResourceNavigateMS.INSTANCE.toPo(resourceNavigateDTO);
        if (resourceNavigatePoMapper.updateByPrimaryKeySelective(resourceNavigatePo) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "更新资源导航失败");
        }

        return true;
    }

    /**
     * Delete resource navigation
     *
     * @param id
     * @return
     */
    @Override
    public Boolean delete(Integer id) {
        ResourceNavigatePo resourceNavigatePo = new ResourceNavigatePo();
        resourceNavigatePo.setId(id);
        resourceNavigatePo.setIsDeleted(true);
        if (resourceNavigatePoMapper.updateByPrimaryKeySelective(resourceNavigatePo) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "Failed to delete");
        }

        return true;
    }

    /**
     * Get resource navigation for all categories
     *
     * @return
     */
    @Override
    public List<String> getCategorys() {
        List<String> categorys = new ArrayList<>();
        ResourceNavigatePoExample example = new ResourceNavigatePoExample();
        example.createCriteria().andIsDeletedEqualTo(false);
        List<ResourceNavigateDTO> resourceNavigateDTOS = ResourceNavigateMS.INSTANCE.toDTO(resourceNavigatePoMapper.selectByExample(example));
        if (CollectionUtils.isNotEmpty(resourceNavigateDTOS)) {
            categorys = resourceNavigateDTOS.stream().map(ResourceNavigateDTO::getCategory).distinct().collect(Collectors.toList());
        }
        return categorys;
    }

    /**
     * Determine if a resource navigation name already exists
     *
     * @param resourceId
     * @param resourceName
     * @return
     */
    private boolean isNameExist(Integer resourceId, String resourceName) {
        ResourceNavigatePoExample example = new ResourceNavigatePoExample();
        example.createCriteria().andIsDeletedEqualTo(false)
                .andResourceNameEqualTo(resourceName);
        List<ResourceNavigatePo> resourceNavigatePos = resourceNavigatePoMapper.selectByExample(example);
        if (resourceNavigatePos.size() > 1) {
            return true;
        } else if (resourceNavigatePos.size() == 1) {
            return !resourceNavigatePos.get(0).getId().equals(resourceId);
        }
        return false;
    }
}
