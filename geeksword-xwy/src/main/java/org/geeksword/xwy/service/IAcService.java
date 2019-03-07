package org.geeksword.xwy.service;

import org.geeksword.xwy.model.Account;

/*
 * @Author :xwy
 * @Date :Created in 2018/11/22  6:03 PM
 * @Description:集成mybatis接口
 */
public interface IAcService {
    int deleteByPrimaryId(Integer id);

    int insert(Account record);

    void insertSelective(Account record);

    Account selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);
}
