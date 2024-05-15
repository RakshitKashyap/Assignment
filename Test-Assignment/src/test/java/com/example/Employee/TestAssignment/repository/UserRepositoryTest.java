package com.example.Employee.TestAssignment.repository;

import com.example.Employee.TestAssignment.models.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        // Test case for findByUsername method
        // Given
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        userRepository.save(user);

        // When
        User foundUser = userRepository.findByUsername(username);

        // Then
        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
    }

    @Test
    public void testFindByEmail() {
        // Test case for findByEmail method
        // Given
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        userRepository.save(user);

        // When
        User foundUser = userRepository.findByEmail(email);

        // Then
        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
    }
}

