package com.ExpenseTracker.Service;

import com.ExpenseTracker.Model.UserEntity;
import com.ExpenseTracker.Repository.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public UserService(UserRepo userRepo, AuthenticationManager authenticationManager, JWTService jwtService){
        this.userRepo = userRepo;
        this.encoder = new BCryptPasswordEncoder(12);
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public UserEntity getAuthenticatedUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntity user = userRepo.findByUserName(username);

        if(user == null) throw new Exception("User not found!");
        else return user;
    }

    public UserEntity getUser() throws Exception{

        return getAuthenticatedUser();
    }

    public UserEntity registerUser(UserEntity user) throws Exception {
        if(userRepo.findByUserName(user.getUserName()) != null) throw new Exception("User already exists!");
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public String validateUser(UserEntity user) throws Exception {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));

        if(authentication.isAuthenticated()) return jwtService.generateToken(user.getUserName());
        else throw new Exception("Invalid User!");
    }

    public UserEntity updateUser(UserEntity user) throws Exception{

        UserEntity authenticatedUser = getAuthenticatedUser();

        authenticatedUser.setUserName(user.getUserName());
        authenticatedUser.setEmail(user.getEmail());
        authenticatedUser.setPassword(encoder.encode(user.getPassword()));

        return userRepo.save(authenticatedUser);
    }

    public UserEntity deleteUser() throws Exception{

        UserEntity user = getAuthenticatedUser();
        userRepo.delete(user);

        return user;
    }

}
