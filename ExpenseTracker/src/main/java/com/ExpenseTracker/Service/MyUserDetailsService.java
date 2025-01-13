package com.ExpenseTracker.Service;

import com.ExpenseTracker.Model.UserEntity;
import com.ExpenseTracker.Model.UserPrincipal;
import com.ExpenseTracker.Repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public MyUserDetailsService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUserName(username);

        if(user == null){
            throw new UsernameNotFoundException("User Not Found!");
        }

        return new UserPrincipal(user);
    }
}
