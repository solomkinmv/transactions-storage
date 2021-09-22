package io.github.solomkinmv.transactions.service.validate;

import io.github.solomkinmv.transactions.controller.dto.CardRequest;
import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class ExpiryValidatorTest {

    private final LocalDateTime fixedDate = LocalDateTime.of(2021, 9, 22, 1, 1);
    private final Clock fixedClock = Clock.fixed(fixedDate.toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
    private final ExpiryValidator expiryValidator = new ExpiryValidator(fixedClock);

    @Test
    void returnErrorIfExpiryIsNull() {
        assertResult(null, "Expiry should be present");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1",
            "12",
            "123",
            "12345",
            "123456"
    })
    void returnsErrorIfExpiryHasIncorrectLength(String expiryValue) {
        assertResult(expiryValue, "Expiry should have length 4 and have format MMYY");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "a234",
            "1a34",
            "12a4",
            "123a",
            "123 ",
            "1.34",
    })
    void returnsErrorIfExpiryHasNonDigitCharacters(String expiryValue) {
        assertResult(expiryValue, "Expiry should contain only digits and have format MMYY");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0034",
            "1334",
            "1434",
            "9934",
            "1534"
    })
    void returnsErrorIfExpirationMonthIsIncorrect(String expiryValue) {
        assertResult(expiryValue, "Expiration month should be between 1 and 12");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1000",
            "1004",
            "1010",
            "1019"
    })
    void returnsErrorIfCardExpired(String expiryValue) {
        assertResult(expiryValue, "Expiration date should be in future");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0921",
            "1021",
            "0130",
            "1222"
    })
    void returnsNullIfExpirationDateIsValidAndInFuture(String expiryValue) {
        assertResult(expiryValue, null);
    }

    // todo: add more tests for situations with different current dates: last day of the month, first day of the month

    private void assertResult(String expiryValue, String expectedValidationResponse) {
        String actualResult = expiryValidator.validate(new SubmitTransactionRequest(
                null, null, null, null,
                new CardRequest(null, expiryValue, null)));

        assertThat(actualResult)
                .isEqualTo(expectedValidationResponse);
    }
}