package org.geeksword.xwy.controller;

import net.sf.json.JSONObject;
import org.geeksword.xwy.model.Account;
import org.geeksword.xwy.service.IAcService;
import org.geeksword.xwy.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * @Author xwy
 * @date 2018/9/28下午6:30
 */

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final static Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IAcService acService;


    @RequestMapping("/add")
    @ResponseBody
    public String addAccount(
            @RequestParam(value = "id",required = false) Integer id,
            @RequestParam(value = "name",required = true) String name,
            @RequestParam(value = "money",required = true) String money
    ){
        logger.info("addAccount() called with: id = [" + id + "], name = [" + name + "], money = [" + money + "]");
        Account account = new Account();
        //account.setId(id);
        account.setName(name);
        account.setMoney(Double.valueOf(money));
        acService.insertSelective(account);
        logger.error("logger-back");
        return "success";
    }


    @RequestMapping("/select")
    @ResponseBody
    public String selectAccount(
            @RequestParam(value = "id",required = true) Integer id
    ){
        log.info("selectAccount() called with: id = [" + id + "]");
        Account account = acService.selectByPrimaryKey(id);
        JSONObject json = JSONObject.fromObject(account);
        log.info(json.toString());
        return json.toString();
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<Account> getAccountList(){
        return accountService.findAccountList();
    }

    @RequestMapping("/getAccountById")
    @ResponseBody
    public String getAccountById(
            @RequestParam(value = "id",required = true) int id
    ){
        Account account = accountService.findAccountById(id);
        JSONObject json = JSONObject.fromObject(account);

        return json.toString();
    }
}
