package io.github.solomkinmv.transactions.service.validate;

import io.github.solomkinmv.transactions.controller.dto.CardRequest;
import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class PanValidatorTest {

    private final PanValidator panValidator = new PanValidator();

    @Test
    void shouldReturnErrorIfPanIsNull() {
        assertResult(null, "PAN should be present");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "1",
            "12",
            "12345",
            "12345678901234",
            "123456789012345",
            "12345678901234567",
            "1234567890123456789"
    })
    void shouldReturnErrorIfPanHasIncorrectLength(String panValue) {
        assertResult(panValue, "PAN should have length 16");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1234567890123456"
    })
    void shouldReturnErrorIfLuhnCheckFailed(String panValue) {
        assertResult(panValue, "PAN should pass a Luhn check");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "4200000000000001"
    })
    void shouldReturnNullIfPanIsValid(String panValue) {
        assertResult(panValue, null);
    }

    private void assertResult(String panValue, String expectedValidationResponse) {
        String actualResult = panValidator.validate(new SubmitTransactionRequest(
                null, null, null, null,
                new CardRequest(panValue, null, null)));

        assertThat(actualResult)
                .isEqualTo(expectedValidationResponse);
    }
}