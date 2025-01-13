package com.ExpenseTracker.Repository;

import com.ExpenseTracker.Model.ExpenseEntity;
import com.ExpenseTracker.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpensesRepo extends JpaRepository<ExpenseEntity,Integer> {

    List<ExpenseEntity> findAllByUser(UserEntity user);

    ExpenseEntity findByExpenseId(long id);

    List<ExpenseEntity> findByUserAndDateBetween(UserEntity user, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
