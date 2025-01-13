package com.ExpenseTracker.Controller;

import com.ExpenseTracker.Model.ExpenseEntity;
import com.ExpenseTracker.Service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }

    @PostMapping("/add")
    public ResponseEntity<ExpenseEntity> saveExpense(@RequestBody ExpenseEntity expense) {
        try{
            return ResponseEntity.ok(expenseService.createExpense(expense));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ExpenseEntity>> getAllExpenses(){
        try{
            return ResponseEntity.ok(expenseService.getAllExpenses());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ExpenseEntity> updateExpense(@PathVariable long id,@RequestBody ExpenseEntity expense){
        try{
            return ResponseEntity.ok(expenseService.updateExpense(id,expense));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ExpenseEntity> deleteExpense(@PathVariable long id){
        try{
            return ResponseEntity.ok(expenseService.deleteExpense(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getLastWeek")
    public ResponseEntity<List<ExpenseEntity>> getExpensesLastWeek(){
        try{
            return ResponseEntity.ok(expenseService.getExpensesLastWeek());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getLastMonth")
    public ResponseEntity<List<ExpenseEntity>> getExpensesLastMonth(){
        try{
            return ResponseEntity.ok(expenseService.getExpensesOfMonths(1));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getLastThreeMonths")
    public ResponseEntity<List<ExpenseEntity>> getExpensesLastThreeMonth(){
        try{
            return ResponseEntity.ok(expenseService.getExpensesOfMonths(3));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getCustom")
    public ResponseEntity<List<ExpenseEntity>> getExpensesCustom(@RequestParam("startDate") LocalDateTime startDateTime,@RequestParam("endDate") LocalDateTime endDateTime){
        try{
            return ResponseEntity.ok(expenseService.getExpensesCustom(startDateTime,endDateTime));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
