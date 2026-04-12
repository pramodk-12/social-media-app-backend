package com.pramod.user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pramod.user.UserApplication;
import com.pramod.user.dto.UserDto;
import com.pramod.user.entities.UserEntity;
import com.pramod.user.repositories.UserRepository;
import com.pramod.user.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@DisplayName("User Controller - Web Layer Test")
@WithMockUser
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getById_Success() throws Exception {
        UserEntity mockUser = UserEntity.builder()
                .id("123").username("pramod_dev").email("pramod@test.com").build();
        when(userService.getUserById("123")).thenReturn(mockUser);

        mockMvc.perform(get("/api/users/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("pramod_dev"))
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void update_Success() throws Exception {
        UserDto mockUser = UserDto.builder().username("pramod_dev").email("pramod@test.com").build();
        UserEntity mockUserEntity = UserEntity.builder().username("pramod_dev").id("123").email("pramod@test.com").build();
        when(userService.updateUser("123",mockUser)).thenReturn(mockUserEntity);
        mockMvc.perform(put("/api/users/123")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("pramod_dev"));
    }


    @Test
    @DisplayName("DELETE /api/users/{id} - Should return 204 No Content")
    void delete_Success() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/users/123").with(csrf()))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser("123");
    }

    @Test
    void getCurrentUser_Success() throws Exception {
        mockMvc.perform(get("/api/users/me")
                        .header("X-User-Name", "vijay")
                        .header("X-User-Id", "userId_1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("vijay"))
                .andExpect(jsonPath("$.userId").value("userId_1"));
    }




}
