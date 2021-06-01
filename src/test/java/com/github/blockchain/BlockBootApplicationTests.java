package com.github.blockchain;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.blockchain.coin.CoinService;
import com.github.blockchain.controller.models.BlockBuy;
import com.github.blockchain.controller.models.BlockSell;
import com.github.blockchain.domain.TransactionStatus;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class BlockBootApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CoinService coinService;

    @BeforeEach
    public void setup() {
        when(coinService.read("address")).thenReturn(TransactionStatus.CONFIRMED);
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void sellToTest() throws Exception {
        mvc.perform(post("/sellTo/address")
                .content(objectMapper.writeValueAsString(new BlockSell("fromAddress", "fromKey")))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void buyFromTest() throws Exception {
        mvc.perform(post("/buyFrom/address")
                .content(objectMapper.writeValueAsString(new BlockBuy("toAddress", "addressKey")))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void getBalanceTest() throws Exception {
        mvc.perform(get("/balance/address"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("status", is(TransactionStatus.CONFIRMED.toString())));
    }
}
