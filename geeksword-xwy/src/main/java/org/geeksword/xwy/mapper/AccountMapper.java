package org.geeksword.xwy.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.geeksword.xwy.model.Account;

@Mapper
public interface AccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    void insertSelective(Account record);

    Account selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);
}