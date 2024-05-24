package com.it.training.service;

import java.util.List;
import java.util.stream.Collectors;
import com.it.training.entity.User;
import com.it.training.mapper.UserMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@Component
@Transactional
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void register(List<User> users) {
        users = users.stream().map(item -> {
            User user = userMapper.getByEmail(item.getEmail());
            if (null != user) {
                return user;
            }
            userMapper.insert(item);
            return item;
        }).collect(Collectors.toList());

        User user = userMapper.getByEmail("Alice@email.com");
        System.out.println(user);

        users.forEach(item -> {
            item.setPassword("PASSWD4");
            userMapper.update(item);
        });

        user = userMapper.getByEmail("Alice@email.com");
        System.out.println(user);
    }


    public void deleteById(long id) {
        userMapper.deleteById(id);
    }
}
