package com.github.blockchain.coin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("bitcoin")
@AutoConfigureMockMvc
class BitcoinServiceTest {

    @Autowired
    private BitcoinService coinService;

    @Test
    void contextLoads() {
    }
}
