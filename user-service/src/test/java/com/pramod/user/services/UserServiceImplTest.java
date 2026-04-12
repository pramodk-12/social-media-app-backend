package com.pramod.user.services;

import com.pramod.user.dto.UserDto;
import com.pramod.user.entities.UserEntity;
import com.pramod.user.repositories.UserRepository;
import com.pramod.user.services.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

// Core JUnit 5 Assertions
import static org.junit.jupiter.api.Assertions.*;

// Mockito Static Imports (These are the ones usually causing errors)
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Service Implementation - Unit Tests")
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto userDto;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .username("vijay_dev")
                .email("vijay@example.com")
                .bio("Java Developer")
                .build();

        userEntity = UserEntity.builder()
                .id("123")
                .username("vijay_dev")
                .email("vijay@example.com")
                .bio("Java Developer")
                .accountStatus("ACTIVE")
                .build();
    }

    // 1. Test Create - Behavior Verification
    @Test
    @DisplayName("Create User: Should successfully map DTO and save to repository")
    void createUser_ShouldSaveUser() {
        // Act
        userService.createUser(userDto);

        // Assert: Verify the repository's save method was called exactly once
        // We use any() because the ID and timestamp are generated inside the method
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    // 2. Test GetById - Success (State Verification)
    @Test
    void getUserById_Success() {
        // Arrange
        when(userRepository.findById("123")).thenReturn(Optional.of(userEntity));

        // Act
        UserEntity result = userService.getUserById("123");

        // Assert
        assertNotNull(result);
        assertEquals("vijay_dev", result.getUsername());
    }

    // 3. Test GetById - Failure (Exception Testing)
    @Test
    void getUserById_NotFound_ThrowsException() {
        // Arrange
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById("999");
        });

        assertEquals("User not found with ID: 999", exception.getMessage());
    }

    // 4. Test Update - Combined Arrange & Act
    @Test
    void updateUser_ShouldUpdateAndSave() {
        // Arrange
        when(userRepository.findById("123")).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDto updateDto = UserDto.builder()
                .username("vijay_dev")
                .email("vijay@example.com")
                .bio("Java Developer")
                .build();

        // Act
        UserEntity updatedUser = userService.updateUser("123", updateDto);

        // Assert
        assertEquals("vijay_dev", updatedUser.getUsername());
        verify(userRepository).save(userEntity);
    }

    // 5. Test Delete - Behavior Verification
    @Test
    void deleteUser_Success() {
        // Arrange
        when(userRepository.existsById("123")).thenReturn(true);

        // Act
        userService.deleteUser("123");

        // Assert
        verify(userRepository).deleteById("123");
    }

    @Test
    void deleteUser_NotFound_ThrowsException() {
        // Arrange
        when(userRepository.existsById("999")).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.deleteUser("999"));

        // Verify delete was NEVER called
        verify(userRepository, never()).deleteById(anyString());
    }
}