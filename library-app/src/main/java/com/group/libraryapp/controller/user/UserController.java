package com.group.libraryapp.controller.user;

import com.group.libraryapp.UserResponse;
import com.group.libraryapp.controller.domain.user.Fruit;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.service.fruit.FruitService;
import com.group.libraryapp.service.user.UserServiceV1;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserServiceV1 userServiceV1;
    private final FruitService fruitService;
    public UserController(UserServiceV1 userServiceV1, @Qualifier("main") FruitService fruitService)
    {
        this.fruitService = fruitService;
        this.userServiceV1 = userServiceV1;
    }


    @PostMapping("/user")
    public void saveUser(@RequestBody UserCreateRequest request) {
        userServiceV1.saveUser(request.getName(), request.getAge());
    }

    @GetMapping("/fruit")
    public Fruit fruit() {
        return new Fruit("바나나", 2000);
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        return userServiceV1.getUsers();
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request) {
        userServiceV1.updateUser(request);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name) {
        userServiceV1.deleteUser(name);
    }

    @GetMapping("/user/error-test")
    public void errorTest() {
        throw new IllegalArgumentException();
    }
}
