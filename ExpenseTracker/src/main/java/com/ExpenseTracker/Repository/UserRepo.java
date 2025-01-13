package com.ExpenseTracker.Repository;

import com.ExpenseTracker.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity,Integer> {

    UserEntity findByUserName(String username);

}
