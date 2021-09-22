package io.github.solomkinmv.transactions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "audit.file=./audit.txt")
class TransactionsStorageApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void submitsTransaction() throws Exception {
        mockMvc.perform(post("/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                                 "  \"invoice\": 1234567,\n" +
                                                 "  \"amount\": 1299,\n" +
                                                 "  \"currency\": \"EUR\",\n" +
                                                 "  \"cardholder\": {\n" +
                                                 "    \"name\": \"First Last\",\n" +
                                                 "    \"email\": \"email@domain.com\"\n" +
                                                 "  },\n" +
                                                 "  \"card\": {\n" +
                                                 "    \"pan\": \"4200000000000001\",\n" +
                                                 "    \"expiry\": \"0624\",\n" +
                                                 "    \"cvv\": \"789\"\n" +
                                                 "  }\n" +
                                                 "}"))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.approved").value("true"))
               .andExpect(jsonPath("$.errors").doesNotExist());
    }

    @Test
    void returnsErrorResponseOnSubmitOfInvalidTransaction() throws Exception {
        mockMvc.perform(post("/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}"))
               .andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.approved").value("false"))
               .andExpect(jsonPath("$.errors.invoice").value("Invoice should be present"))
               .andExpect(jsonPath("$.errors.amount").value("Amount should be present"))
               .andExpect(jsonPath("$.errors.currency").value("Currency should be present"))
               .andExpect(jsonPath("$.errors.cardholder.name").value("Name should be present"))
               .andExpect(jsonPath("$.errors.cardholder.email").value("Email should be present"))
               .andExpect(jsonPath("$.errors.card.pan").value("PAN should be present"))
               .andExpect(jsonPath("$.errors.card.expiry").value("Expiry should be present"))
               .andExpect(jsonPath("$.errors.card.cvv").value("CVV should be present"));
    }

    @Test
    void returnsSavedTransaction() throws Exception {
        mockMvc.perform(post("/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                                 "  \"invoice\": 1234567,\n" +
                                                 "  \"amount\": 1299,\n" +
                                                 "  \"currency\": \"EUR\",\n" +
                                                 "  \"cardholder\": {\n" +
                                                 "    \"name\": \"First Last\",\n" +
                                                 "    \"email\": \"email@domain.com\"\n" +
                                                 "  },\n" +
                                                 "  \"card\": {\n" +
                                                 "    \"pan\": \"4200000000000001\",\n" +
                                                 "    \"expiry\": \"0624\",\n" +
                                                 "    \"cvv\": \"789\"\n" +
                                                 "  }\n" +
                                                 "}"));

        mockMvc.perform(get("/transactions/1234567"))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.invoice").value("1234567"))
               .andExpect(jsonPath("$.amount").value("1299"))
               .andExpect(jsonPath("$.currency").value("EUR"))
               .andExpect(jsonPath("$.cardholder.name").value("**********"))
               .andExpect(jsonPath("$.cardholder.email").value("email@domain.com"))
               .andExpect(jsonPath("$.card.pan").value("************0001"))
               .andExpect(jsonPath("$.card.cvv").value("****"));
    }

    @Test
    void returnsNotFoundEmptyResponseIfNoTransaction() throws Exception {
        mockMvc.perform(get("/transactions/12345"))
               .andDo(print())
               .andExpect(status().isNotFound());
    }
}
