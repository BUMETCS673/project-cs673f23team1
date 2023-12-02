package com.aceteam.tm;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.aceteam.tm.Entity.User;
import com.aceteam.tm.Service.UserService;
import com.aceteam.tm.UserRepository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


/**
 * @description: UserServiceTest based on TDD
 * @author: haoran
 */
@SpringBootTest
public class UserServiceTest {

    @Test
    public void testCreateUser() {
        // Creating a new user object
        User newUser = new User("user123", "admin123");


    }
}
