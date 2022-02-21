package com.example.inflearnrestfulwebservice.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

// 스테레오 타입. Component 보다 명확하게 Service 를 이용한다.
@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(new User(1, "Kenneth", new Date(), "pass1", "701010-1111111"));
        users.add(new User(2, "Kenneth", new Date(), "pass2", "801010-1111111"));
        users.add(new User(3, "Kenneth", new Date(), "pass3", "901010-1111111"));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++usersCount);
        }

        users.add(user);
        return user;
    }

    public User findOne(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }

        return null;
    }

    public User deleteById(int id) {
        final Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()) {
            User user = iterator.next();

            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }

        return null;
    }

    public User editById(User newUser) {
        final User oldUser = this.findOne(newUser.getId());
        // 기존에 있는 아이디를 수정해야한다.
        if(oldUser == null) {
            return null;
        }
        this.deleteById(oldUser.getId());
        return this.save(newUser);
    }
}
