package io.github.solomkinmv.transactions.service.encrypt;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Base64MockEncryptionTest {

    private final Base64MockEncryption base64MockEncryption = new Base64MockEncryption();

    @Test
    void encodesAndDecodesText() {
        String originalValue = "4200000000000001";

        String encryptedValue = base64MockEncryption.encrypt(originalValue);
        assertThat(encryptedValue).isNotEqualTo(originalValue);

        String actualDecryptedValue = base64MockEncryption.decrypt(encryptedValue);
        assertThat(actualDecryptedValue).isEqualTo(originalValue);
    }
}