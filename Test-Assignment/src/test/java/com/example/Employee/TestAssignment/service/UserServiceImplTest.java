package com.example.Employee.TestAssignment.service;

import com.example.Employee.TestAssignment.exceptions.CustomException;
import com.example.Employee.TestAssignment.models.RequestDto.UserCreateDto;
import com.example.Employee.TestAssignment.models.entity.User;
import com.example.Employee.TestAssignment.repository.UserRepository;
import com.example.Employee.TestAssignment.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterNewUser_Success() {
        // Prepare test data
        UserCreateDto userDto = new UserCreateDto("testUsername", "test@example.com", "ROLE_USER");

        // Mock repository method calls
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Call the method under test
        String result = userService.registerNewUser(userDto);

        // Verify the result
        assertEquals("Registered", result);

        // Verify repository method calls
        verify(userRepository, times(1)).findByUsername(anyString());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testRegisterNewUser_UsernameExists() {
        // Prepare test data
        UserCreateDto userDto = new UserCreateDto("existingUsername", "test@example.com", "ROLE_USER");

        // Mock repository method calls
        when(userRepository.findByUsername(anyString())).thenReturn(new User());

        // Call the method under test and verify exception
        assertThrows(CustomException.class, () -> userService.registerNewUser(userDto),
                "Expected CustomException for existing username");

        // Verify repository method calls
        verify(userRepository, times(1)).findByUsername(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

}
