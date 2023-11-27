package dev.japhethwaswa.demoapi.service;

import dev.japhethwaswa.demoapi.api.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private List<User> userList;

    public UserService() {
        userList = new ArrayList<>();
        User user1 = new User(1,"nagel",23,"nagel@gmail.com");
        User user2 = new User(2,"royce",11,"royce@gmail.com");
        User user3 = new User(3,"caroline",23,"caroline@gmail.com");
        User user4 = new User(4,"janet",23,"janet@gmail.com");
        userList.addAll(Arrays.asList(user1,user2,user3,user4));
    }

    public Optional<User> getUser(Integer id) {
        Optional<User> optional = Optional.empty();
        for(User user:userList){

            if(user.id() ==id)
            {
                optional = Optional.of(user);
                return optional;
            }
        }

        return optional;
    }
}
