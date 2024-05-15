package com.example.Employee.TestAssignment.service;
import com.example.Employee.TestAssignment.models.entity.User;
import com.example.Employee.TestAssignment.repository.UserRepository;
import com.example.Employee.TestAssignment.service.impl.UserInfoUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserInfoUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserInfoUserDetailsService userDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        user.setPassword("password");
        when(userRepository.findByUsername(username)).thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String username = "nonExistentUser";
        when(userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
    }
}
