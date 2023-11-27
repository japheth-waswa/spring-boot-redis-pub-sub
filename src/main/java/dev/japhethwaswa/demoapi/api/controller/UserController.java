package dev.japhethwaswa.demoapi.api.controller;

import dev.japhethwaswa.demoapi.api.model.User;
import dev.japhethwaswa.demoapi.configuration.RedisMessagePublisher;
import dev.japhethwaswa.demoapi.configuration.RedisMessageSubscriber;
import dev.japhethwaswa.demoapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private UserService userService;
    @Autowired
    private RedisMessagePublisher messagePublisher;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/")
    public String def(){
        return "Hello world friend";
    }
    @GetMapping("/user")
    public User getUser(@RequestParam Integer id){
        Optional<User> user  =  userService.getUser(id);
        return user.orElse(null);
    }
    @PostMapping("/user/pub")
    public void publish(@RequestBody User user){
        messagePublisher.publish(user.toString());
    }
    @GetMapping("/user/sub")
    public List<String> getMessageList(){
        return RedisMessageSubscriber.getMessagesList();
    }

}
