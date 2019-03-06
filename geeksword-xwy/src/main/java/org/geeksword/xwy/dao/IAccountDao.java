package org.geeksword.xwy.dao;



import org.geeksword.xwy.model.Account;

import java.util.List;

/*
 * @Author xwy
 * @date 2018/9/28下午4:43
 */
public interface IAccountDao {
    int add(Account account);

    int update(Account account);

    int delete(int id);

    Account findAccountById(int id);

    List<Account> findAccountList();

}
