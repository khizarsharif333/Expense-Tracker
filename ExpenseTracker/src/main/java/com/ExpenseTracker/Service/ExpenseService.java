package com.ExpenseTracker.Service;

import com.ExpenseTracker.Model.ExpenseEntity;
import com.ExpenseTracker.Model.UserEntity;
import com.ExpenseTracker.Repository.ExpensesRepo;
import com.ExpenseTracker.Repository.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpensesRepo expensesRepo;
    private final UserRepo userRepo;

    public ExpenseService(ExpensesRepo expensesRepo,UserRepo userRepo){
        this.expensesRepo = expensesRepo;
        this.userRepo = userRepo;
    }

    public UserEntity getAuthenticatedUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntity user = userRepo.findByUserName(username);

        if(user == null) throw new Exception("User not found!");
        else return user;
    }


    public ExpenseEntity createExpense(ExpenseEntity expense) throws Exception {

        UserEntity user = getAuthenticatedUser();

        expense.setUser(user);
        expense.setDate(LocalDateTime.now());
        return expensesRepo.save(expense);
    }

    public List<ExpenseEntity> getAllExpenses() throws Exception {

        UserEntity user = getAuthenticatedUser();
        return expensesRepo.findAllByUser(user);

    }

    public ExpenseEntity updateExpense(long id,ExpenseEntity expense){

        ExpenseEntity existingExpense = expensesRepo.findByExpenseId(id);

        existingExpense.setAmount(expense.getAmount());
        existingExpense.setCategory(expense.getCategory());
        existingExpense.setDescription(expense.getDescription());

        return expensesRepo.save(existingExpense);
    }


    public ExpenseEntity deleteExpense(long id) {

        ExpenseEntity existingExpense = expensesRepo.findByExpenseId(id);
        expensesRepo.delete(existingExpense);

        return existingExpense;
    }

    public List<ExpenseEntity> getExpensesLastWeek() throws Exception {

        UserEntity user = getAuthenticatedUser();
        LocalDateTime endDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = endDateTime.minusDays(7);

        return expensesRepo.findByUserAndDateBetween(user,startDateTime,endDateTime);
    }

    public List<ExpenseEntity> getExpensesOfMonths(int i) throws Exception {

        UserEntity user = getAuthenticatedUser();
        LocalDateTime endDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = endDateTime.minusMonths(i);

        return expensesRepo.findByUserAndDateBetween(user,startDateTime,endDateTime);
    }

    public List<ExpenseEntity> getExpensesCustom(LocalDateTime startDateTime, LocalDateTime endDateTime) throws Exception {

        UserEntity user = getAuthenticatedUser();

        return expensesRepo.findByUserAndDateBetween(user,startDateTime,endDateTime);
    }
}
