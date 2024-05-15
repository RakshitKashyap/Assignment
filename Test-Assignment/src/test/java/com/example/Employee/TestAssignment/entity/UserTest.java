package com.example.Employee.TestAssignment.entity;

import com.example.Employee.TestAssignment.models.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("john_doe");
        user.setEmail("john@example.com");
        user.setPassword("password123");
        user.setRole("ROLE_USER");
        user.setStatus(true);
    }

    @Test
    public void testGettersAndSetters() {
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("john_doe");
        assertThat(user.getEmail()).isEqualTo("john@example.com");
        assertThat(user.getPassword()).isEqualTo("password123");
        assertThat(user.getRole()).isEqualTo("ROLE_USER");
        assertThat(user.isStatus()).isTrue();

        // Test setters
        user.setId(2L);
        user.setUsername("jane_doe");
        user.setEmail("jane@example.com");
        user.setPassword("newpassword");
        user.setRole("ROLE_ADMIN");
        user.setStatus(false);

        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getUsername()).isEqualTo("jane_doe");
        assertThat(user.getEmail()).isEqualTo("jane@example.com");
        assertThat(user.getPassword()).isEqualTo("newpassword");
        assertThat(user.getRole()).isEqualTo("ROLE_ADMIN");
        assertThat(user.isStatus()).isFalse();
    }

    @Test
    public void testNoArgsConstructor() {
        User newUser = new User();
        assertThat(newUser).isNotNull();
    }

    @Test
    public void testAllArgsConstructor() {
        User newUser = new User(3L, "alice", "alice@example.com", "pass123", "ROLE_USER", true);
        assertThat(newUser).isNotNull();
        assertThat(newUser.getId()).isEqualTo(3L);
        assertThat(newUser.getUsername()).isEqualTo("alice");
        assertThat(newUser.getEmail()).isEqualTo("alice@example.com");
        assertThat(newUser.getPassword()).isEqualTo("pass123");
        assertThat(newUser.getRole()).isEqualTo("ROLE_USER");
        assertThat(newUser.isStatus()).isTrue();
    }

    @Test
    public void testToString() {
        String expectedToString = "User(id=1, username=john_doe, email=john@example.com, password=password123, role=ROLE_USER, status=true)";
        assertThat(user.toString()).isEqualTo(expectedToString);
    }
}
