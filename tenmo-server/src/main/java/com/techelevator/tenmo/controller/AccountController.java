package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {
    private AccountDAO accountDAO;
    private UserDao userDao;
    private TransferDAO transferDAO;

    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public AccountController(AccountDAO accountDAO, UserDao userDao, TransferDAO transferDAO) {
        this.accountDAO = accountDAO;
        this.userDao = userDao;
        this.transferDAO = transferDAO;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    // TODO get user by ID
    // TODO transfers by user_id
    // TODO create request
    // TODO create transfer
    // TODO get account info by ID
    // TODO get balance by ID


}


