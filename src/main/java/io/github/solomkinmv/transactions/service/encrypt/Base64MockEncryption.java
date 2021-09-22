package io.github.solomkinmv.transactions.service.encrypt;

import java.util.Base64;

public class Base64MockEncryption implements EncryptionService {

    private final Base64.Encoder encoder = Base64.getEncoder().withoutPadding();
    private final Base64.Decoder decoder = Base64.getDecoder();

    @Override
    public String encrypt(String originalValue) {
        return encoder.encodeToString(originalValue.getBytes());
    }

    @Override
    public String decrypt(String encryptedValue) {
        return new String(decoder.decode(encryptedValue));
    }
}
