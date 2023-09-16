package com.Ridoh.ExpenseTrackerApplication.Repository;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    Boolean existsByEmailOrUsername(String email, String username);
    User findByUsername(String username);
}
