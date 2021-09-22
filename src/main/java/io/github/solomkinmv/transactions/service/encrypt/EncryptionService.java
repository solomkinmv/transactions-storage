package io.github.solomkinmv.transactions.service.encrypt;

public interface EncryptionService {

    String encrypt(String originalValue);

    String decrypt(String encryptedValue);
}
