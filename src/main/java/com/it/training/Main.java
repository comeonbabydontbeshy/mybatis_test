package com.it.training;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.it.training.entity.User;
import com.it.training.service.UserService;

import java.util.List;

import java.util.ArrayList;

import org.mybatis.spring.annotation.MapperScan;

@Configuration
@ComponentScan
@EnableTransactionManagement
@MapperScan("com.it.training.mapper")
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        UserService userService = context.getBean(UserService.class);

        List<User> users = new ArrayList<>();
        users.add(new User((long) 0, "Tracy@email.com", "passwd3", "Tracy"));
        users.add(new User((long) 0, "Alice@email.com", "passwd3", "Alice"));
        users.add(new User((long) 0, "Bob@email.com", "passwd3", "Bob"));
        users.add(new User((long) 0, "Candy@email.com", "passwd3", "Candy"));

        try {
        userService.register(users);
        } catch(RuntimeException e) {
            System.out.println(e.getMessage());
            return;
        }

        users.forEach(item -> {
            userService.deleteById(item.getId());
        });
    }
}
