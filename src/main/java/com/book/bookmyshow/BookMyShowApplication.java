package com.book.bookmyshow;

import com.book.bookmyshow.controllers.UserController;
import com.book.bookmyshow.dtos.SignUpRequestDTO;
import com.book.bookmyshow.dtos.SignUpResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookMyShowApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BookMyShowApplication.class, args);
    }

    @Autowired
    UserController userController;

    @Override
    public void run(String... args) throws Exception {
        SignUpRequestDTO request = new SignUpRequestDTO();
        request.setEmail("rexemanl007@gmail.com");
        request.setPassword("rexemanl007");

        //this is a test comment
        //this is a develop branch commit
        //fjdksldjfrmelkddsl
        SignUpResponseDTO responseDTO = userController.signUp(request);
    }
}
