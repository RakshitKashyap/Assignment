package com.example.Employee.TestAssignment.controller;

import com.example.Employee.TestAssignment.models.RequestDto.UserCreateDto;
import com.example.Employee.TestAssignment.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testAddNewUser_Success() throws Exception {
        // Prepare mock data
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setUsername("John");
        userCreateDto.setEmail("john@example.com");

        // Mock userService behavior
        when(userService.registerNewUser(any(UserCreateDto.class))).thenReturn("Registered");

        // Perform POST request
        MvcResult result = mockMvc.perform(post("/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John\", \"email\": \"john@example.com\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        // Assert response
        String content = result.getResponse().getContentAsString();
        assertEquals("Registered", content);
    }

    @Test
    public void testAddNewUser_Failure() throws Exception {
        // Prepare mock data
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setUsername("John");
        userCreateDto.setEmail("john@example.com");

        // Mock userService behavior
        when(userService.registerNewUser(any(UserCreateDto.class))).thenReturn(null);

        // Perform POST request
        mockMvc.perform(post("/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John\", \"email\": \"john@example.com\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testTestPostRequest() throws Exception {
        // Perform POST request
        mockMvc.perform(post("/v1/user/test-request"))
                .andExpect(status().isOk());
    }
}
