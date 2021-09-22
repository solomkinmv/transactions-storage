package io.github.solomkinmv.transactions.service.validate;

import io.github.solomkinmv.transactions.controller.dto.CardholderRequest;
import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class EmailValidatorTest {

    private final EmailValidator emailValidator = new EmailValidator();

    @Test
    void returnsErrorIfEmailIsNull() {
        assertResult(null, "Email should be present");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "alice.example.com",
            "alice..bob@example.com",
            "alice@.example.com",
            "alice.@example.com",
            "alice..@example.com",
            "alice@example"
    })
    void returnsErrorIfEmailIsNotValid(String emailValue) {
        assertResult(emailValue, "Email should be valid");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "alice@example.com",
            "alice.bob@example.com",
            "alice@example.me.org",
            "alice++@example.com",
            "alice+@example.com"
    })
    void returnsNullIfEmailIsValid(String emailValue) {
        assertResult(emailValue, null);
    }

    private void assertResult(String emailValue, String expectedValidationResponse) {
        String actualResult = emailValidator.validate(new SubmitTransactionRequest(
                null, null, null,
                new CardholderRequest(null, emailValue), null));

        assertThat(actualResult)
                .isEqualTo(expectedValidationResponse);
    }
}