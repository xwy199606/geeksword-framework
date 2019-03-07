package org.geeksword.xwy.service.impl;

import org.geeksword.xwy.mapper.AccountMapper;
import org.geeksword.xwy.model.Account;
import org.geeksword.xwy.service.IAcService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/*
 * @Author :xwy
 * @Date :Created in 2018/11/22  6:07 PM
 * @Description:
 */
@Service
public class AcServiceImpl implements IAcService {

    @Resource
    private AccountMapper accountMapper;

    @Override
    public int deleteByPrimaryId(Integer id) {
        return accountMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Account record) {
        return accountMapper.insert(record);
    }

    @Override
    public void insertSelective(Account record) {
        accountMapper.insertSelective(record);
    }

    @Override
    public Account selectByPrimaryKey(Integer id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Account record) {
        return accountMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Account record) {
        return accountMapper.updateByPrimaryKey(record);
    }
}
