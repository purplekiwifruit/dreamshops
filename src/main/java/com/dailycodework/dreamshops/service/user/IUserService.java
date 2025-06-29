package com.dailycodework.dreamshops.service.user;

import com.dailycodework.dreamshops.dto.UserDto;
import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.request.CreateUserRequest;
import com.dailycodework.dreamshops.request.UserUpdateRequst;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequst request, Long userId);
    void deleteUser(Long userId);
    UserDto convertToDto(User user);

    User getAuthenticatedUser();
}
