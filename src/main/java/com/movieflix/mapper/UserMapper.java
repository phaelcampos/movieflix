package com.movieflix.mapper;

import com.movieflix.entity.User;
import com.movieflix.request.UserRequest;
import com.movieflix.response.UserResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public User toUser(UserRequest request){
        return User.builder()
                .email(request.email())
                .password(request.password())
                .name(request.name())
                .build();

    }

    public UserResponse toUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
