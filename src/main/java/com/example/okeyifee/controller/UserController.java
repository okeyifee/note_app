//package com.example.okeyifee.controller;
//
//import com.example.okeyifee.models.User;
//import com.example.okeyifee.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
//@RequestMapping(value = "/auth/v1/users")
//public class UserController{
//
//    private UserService userService;
//
//
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @PostMapping("/signup")
//    public void signUp(@RequestBody User user) {
//        userService.save(user);
//    }
//
//    @PostMapping("/get_users")
//    public void findUsers(@RequestBody User user) {
//        userService.findByUsername(user.getUsername());
//    }
//
//    @PostMapping("/get_user")
//    public void findUser(@RequestBody User user) {
//        userService.findByUsername(user.getUsername());
//        System.out.println(user.getPassword());
//        System.out.println(user.getUsername());
//    }
//
//
//    @PostMapping("/delete_user")
//    public void deleteUsers(@RequestBody User user) {
//        userService.findByUsername(user.getUsername());
//        System.out.println(user.getPassword());
//        System.out.println(user.getUsername());
//    }
//
//
//    @PostMapping("/delete_users")
//    public void deleteUser(@RequestBody User user) {
//        userService.findByUsername(user.getUsername());
//        System.out.println(user.getPassword());
//        System.out.println(user.getUsername());
//    }
//
//    @PostMapping("/edit_user")
//    public void edit(@RequestBody User user) {
//        userService.findByUsername(user.getUsername());
//        System.out.println(user.getPassword());
//        System.out.println(user.getUsername());
//    }
//
//}
