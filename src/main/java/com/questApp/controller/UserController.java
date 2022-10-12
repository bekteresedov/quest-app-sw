package com.questApp.controller;

import com.questApp.data.dto.response.UserResponse;
import com.questApp.data.entity.User;
import com.questApp.exception.UserNotFoundException;
import com.questApp.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    final UserService userService;

    @GetMapping("/getAll")
    public List<UserResponse>getAllUsers(){
        return userService.getAllUsers().stream().map(u->new UserResponse(u)).collect(Collectors.toList());
    }


    @PostMapping("/new")
    public ResponseEntity<Void>CreateUser(@RequestBody User user){
        User u=userService.saveOneUser(user);
        if(user!=null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/get/{userId}")
    public UserResponse getOneUser(@PathVariable  Long userId){
        User user=userService.getOneUserById(userId);
        if(user==null){
            throw  new UserNotFoundException();
        }
        return new UserResponse(user);
    }


    @PutMapping("/update/{userId}")
    public ResponseEntity<Void>updateOneUser(@PathVariable Long userId,@RequestBody User user){
        User u=userService.updateOneUser(userId,user);
        if (user==null)
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/delete/{userId}")
    public void deleteOneUser(@PathVariable Long userId) {
        userService.deleteById(userId);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleUserNotFound() {

    }
}
