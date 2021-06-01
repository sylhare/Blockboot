package com.github.blockchain.coin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("mock")
@AutoConfigureMockMvc
class MockCoinServiceTest {

    @Autowired
    private MockCoinService coinService;

    @Test
    void contextLoads() {
    }
}
