package com.group.libraryapp.service.user;

import com.group.libraryapp.UserResponse;
import com.group.libraryapp.controller.domain.user.User;
import com.group.libraryapp.controller.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {

    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserCreateRequest request) {
        userRepository.save(new User(request.getName(), request.getAge()));
    }

    public List<UserResponse> getUsers() {

        List<User> users = userRepository.findAll();
//      return users.stream()
//                .map(user -> new UserResponse(user.getId(), user.getName(), user.getAge()))
//                .collect(Collectors.toList());
        // 이중콜론 표현식 UserResponse 인스턴스의 new 생성자를
        // 매개변수에 맞게 찾아서 생성해준다.
        return users.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public void updateUser(UserUpdateRequest request) {
        // id를 가진 user를 찾고 null일 경우 throw Exception
        User user = userRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException::new);

        user.updateName(request.getName());
        userRepository.save(user);

    }
}