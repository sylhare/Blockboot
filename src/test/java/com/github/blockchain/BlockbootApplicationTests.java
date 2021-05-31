package com.github.blockchain;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class BlockbootApplicationTests {

    @Test
    void contextLoads() {
    }
}
