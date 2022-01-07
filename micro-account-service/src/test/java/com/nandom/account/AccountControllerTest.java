package com.nandom.account;


import com.nandom.account.controller.AccountController;
import com.nandom.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService service;

    @Test
    public void createAccountTest() {
//        given(this.service.createAccount(new {}).willReturn(new Date());
//        this.mvc.perform(get("/schedulePickup")
//                .accept(MediaType.JSON)
//                .andExpect(status().isOk());
    }
}
