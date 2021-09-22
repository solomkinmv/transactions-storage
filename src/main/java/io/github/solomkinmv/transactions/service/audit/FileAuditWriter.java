package io.github.solomkinmv.transactions.service.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.solomkinmv.transactions.persistence.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class FileAuditWriter implements DisposableBean, AuditWriter {

    private final BufferedWriter bufferedWriter;
    private final ObjectMapper objectMapper;

    public FileAuditWriter(String auditFileLocation, ObjectMapper objectMapper) throws IOException {
        bufferedWriter = Files.newBufferedWriter(Paths.get(auditFileLocation), StandardCharsets.UTF_8);
        this.objectMapper = objectMapper;
    }

    public void onSubmit(Transaction transaction) {
        try {
            bufferedWriter.write(objectMapper.writeValueAsString(transaction) + "\n");
        } catch (IOException e) {
            log.warn("Failed to audit transaction {}", transaction, e);
        }
    }

    @Override
    public void destroy() throws Exception {
        log.info("Closing audit write");
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
