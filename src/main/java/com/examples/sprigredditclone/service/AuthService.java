package com.examples.sprigredditclone.service;

import com.examples.sprigredditclone.dto.RegisterRequest;
import com.examples.sprigredditclone.exceptions.SpringRedditException;
import com.examples.sprigredditclone.model.NotificationEmail;
import com.examples.sprigredditclone.model.User;
import com.examples.sprigredditclone.model.VerificationToken;
import com.examples.sprigredditclone.repository.UserRepository;
import com.examples.sprigredditclone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository; // Instead of autowiring using constructor injection. Good to use it.
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    @Transactional // Because we are interacting with relatational DB
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedDate(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendEmail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "http://localhost:8080/api/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow( () -> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow( () -> new SpringRedditException("No user found with name =" + username));
        user.setEnabled(true);
        userRepository.save(user);
    }
}
