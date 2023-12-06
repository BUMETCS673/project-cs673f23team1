package com.aceteam.tm.post.service.utils;

import com.aceteam.tm.post.facade.dto.CommentDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: some desc
 * @author: haoran
 */
@Slf4j
public class CommentTreeUtils {
    /**
     * set-to-tree
     *
     * @param commentDTOS
     * @return
     */
    public static List<CommentDTO> toTree(List<CommentDTO> commentDTOS) {
        Map<Integer, CommentDTO> commentDTOMap = commentDTOS.stream().collect(Collectors.toMap(CommentDTO::getId, e -> e));
        List<CommentDTO> root = new ArrayList<>();
        for (CommentDTO dto : commentDTOS) {
            Integer preId = dto.getPreId();
            // is root comment
            if (preId == 0) {
                // Setting comment depth
                dto.setDepth(0);
                root.add(dto);
            } else {
                CommentDTO parent = commentDTOMap.get(preId);
                // Skip comment on child level without parent level
                if (parent == null) {
                    continue;
                }
                List<CommentDTO> children = CollectionUtils.isEmpty(parent.getChild()) ? new ArrayList<>() : parent.getChild();
                // Setting comment depth
                dto.setDepth(parent.getDepth() + 1);
                children.add(dto);
                parent.setChild(children);
            }
        }
        return root;
    }


}

