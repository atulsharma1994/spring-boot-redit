package com.examples.sprigredditclone.controller;

import com.examples.sprigredditclone.dto.RegisterRequest;
import com.examples.sprigredditclone.exceptions.SpringRedditException;
import com.examples.sprigredditclone.model.User;
import com.examples.sprigredditclone.model.VerificationToken;
import com.examples.sprigredditclone.repository.UserRepository;
import com.examples.sprigredditclone.repository.VerificationTokenRepository;
import com.examples.sprigredditclone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        // On successful return response entity
        return new ResponseEntity<>("User Registration Successful",
                HttpStatus.OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account activated successfully,", HttpStatus.OK);
    }



}
