package com.book.bookmyshow.controllers;

import com.book.bookmyshow.dtos.ResponseStatus;
import com.book.bookmyshow.dtos.SignUpRequestDTO;
import com.book.bookmyshow.dtos.SignUpResponseDTO;
import com.book.bookmyshow.models.User;
import com.book.bookmyshow.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    public SignUpResponseDTO signUp(SignUpRequestDTO request) {
        SignUpResponseDTO response = new SignUpResponseDTO();

        try{
            User user = userService.signUp(request.getEmail(),request.getPassword());
            response.setStatus(ResponseStatus.SUCCESS);
            response.setUserId(user.getId());
            response.setMessage("Signed up successfully");
        } catch (Exception e) {
            response.setStatus(ResponseStatus.FAILURE);
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
