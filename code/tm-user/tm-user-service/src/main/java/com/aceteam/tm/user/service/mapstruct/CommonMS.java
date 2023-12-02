package com.aceteam.tm.user.service.mapstruct;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
public interface CommonMS<P, D> {
    /**
     * po to dto
     *
     * @param p po
     * @return dto
     */
    D toDTO(P p);

    /**
     * dto to po
     *
     * @param d dto
     * @return po
     */
    P toPo(D d);

    /**
     * po to dto
     *
     * @param pList po
     * @return dto
     */
    List<D> toDTO(List<P> pList);

    /**
     * dto to po
     *
     * @param pList dto
     * @return po
     */
    List<P> toPo(List<D> pList);

    /**
     * po to dto
     *
     * @param pageInfo po
     * @return dto
     */
    PageInfo<D> toPage(PageInfo<P> pageInfo);
}
