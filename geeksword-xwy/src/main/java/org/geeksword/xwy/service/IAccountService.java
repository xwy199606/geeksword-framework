package org.geeksword.xwy.service;

import org.geeksword.xwy.model.Account;

import java.util.List;

/*
 * @Author xwy
 * @date 2018/9/28下午6:20
 * @description：Jdbc操作数据库接口
 */
public interface IAccountService {
    int add(Account account);

    int update(Account account);

    int delete(int id);

    Account findAccountById(int id);

    List<Account> findAccountList();

}
