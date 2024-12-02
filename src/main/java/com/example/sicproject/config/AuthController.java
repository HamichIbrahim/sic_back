package com.example.sicproject.config;


import com.example.sicproject.controller.LoginRequest;
import com.example.sicproject.controller.LoginResponse;
import com.example.sicproject.model.User;
import com.example.sicproject.repository.UserRepo;
import jdk.jshell.spi.ExecutionControl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserRepo userRepository;
    private PasswordEncoder passwordEncoder ;
    private JwtUtil tokenProvider;
    private CustomUserDetailsService customUserService;

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> createUserHandler (@RequestBody User user) throws ExecutionControl.UserException {
        String username = user.getUsername();
        String password = user.getPassword();
    
        if (username == null || username.trim().isEmpty()) {
            return new ResponseEntity("Username cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (password == null || password.trim().isEmpty()) {
            return new ResponseEntity("Password cannot be empty", HttpStatus.BAD_REQUEST);
        }
        // Check if the user already exists
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            return new ResponseEntity("User already exists", HttpStatus.CONFLICT);
        }

        User createdUser = new User() ;
        createdUser.setUsername(username);
        createdUser.setPassword(passwordEncoder.encode(password));
        System.out.println(passwordEncoder.encode(password));
        userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(String.valueOf(authentication));
        LoginResponse res = new LoginResponse(createdUser.getId(),createdUser.getUsername(),jwt);
        return new ResponseEntity<LoginResponse>(res,HttpStatus.ACCEPTED) ;
    }
    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> loginHandler(@RequestBody LoginRequest req){
        String password = req.getPassword();
        Authentication authentication = authenticate(req.getUsername(), password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<User> isUser = userRepository.findByUsername(req.getUsername());
        String jwt = tokenProvider.generateToken(String.valueOf(authentication));

        LoginResponse res = new LoginResponse(isUser.get().getId(),isUser.get().getUsername(),jwt);

        return new ResponseEntity<LoginResponse>(res, HttpStatus.ACCEPTED) ;
    }

    public Authentication authenticate(String username, String password){
        UserDetails userDetails = customUserService.loadUserByUsername(username);
        System.out.println(userDetails.getUsername());
        if(userDetails == null){
            throw new BadCredentialsException("invalid password or username");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("invalid password or username");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}