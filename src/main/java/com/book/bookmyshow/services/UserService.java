package com.book.bookmyshow.services;

import com.book.bookmyshow.models.User;
import com.book.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public User signUp(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new RuntimeException("User already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(email);
        user.setFirstName("Temp User");
        user.setLastName("Tempo");
        user.setBookings(new ArrayList<>());

        return userRepository.save(user);


    }
}
