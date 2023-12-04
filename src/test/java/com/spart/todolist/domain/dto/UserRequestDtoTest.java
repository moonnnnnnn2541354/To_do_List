package com.spart.todolist.domain.dto;

import static org.assertj.core.api.Assertions.*;

import com.spart.todolist.domain.test.CommonTest;
import com.spart.todolist.domain.user.dto.UserRequestDto;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UserRequestDtoTest implements CommonTest {

    @DisplayName("유저 요청 Dto")
    @Nested
    class createUserRequestDto {

        @DisplayName("성공")
        @Test
        void createUserRequestDto_success() {
            UserRequestDto requestDto = new UserRequestDto(TEST_USER_NAME, TEST_USER_PASSWORD);

            Set<ConstraintViolation<UserRequestDto>> violations = validate(requestDto);

            assertThat(violations).isEmpty();

        }

        @DisplayName("실패_잘못된_username")
        @Test
        void createUserRequestDto_fail_username() {
            UserRequestDto requestDto = new UserRequestDto("Dongmin", TEST_USER_PASSWORD);

            Set<ConstraintViolation<UserRequestDto>> violations = validate(requestDto);

            assertThat(violations).hasSize(1);
            assertThat(violations)
                .extracting("message")
                .contains("\"^[a-z0-9]{4,10}$\"와 일치해야 합니다");

        }

        @DisplayName("실패_잘못된_password")
        @Test
        void createUserRequestDto_fail_password() {
            UserRequestDto requestDto = new UserRequestDto(TEST_USER_NAME, "!123");

            Set<ConstraintViolation<UserRequestDto>> violations = validate(requestDto);

            assertThat(violations).hasSize(1);
            assertThat(violations)
                .extracting("message")
                .contains("\"^[A-Za-z0-9]{8,15}$\"와 일치해야 합니다");

        }

    }

    private Set<ConstraintViolation<UserRequestDto>> validate(UserRequestDto userRequestDTO) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(userRequestDTO);
    }

}
