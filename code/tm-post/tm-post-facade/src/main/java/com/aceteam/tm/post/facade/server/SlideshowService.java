package com.aceteam.tm.post.facade.server;

import com.aceteam.tm.post.facade.dto.SlideshowDTO;

import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
public interface SlideshowService {
    /**
     * Get slideshow image information
     *
     * @return
     */
    List<SlideshowDTO> getList();
}
