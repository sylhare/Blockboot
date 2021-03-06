package com.github.blockchain.coin;

import static com.github.blockchain.coin.bitcoin.BitcoinWallet.createWallet;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.bitcoinj.core.Coin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.github.blockchain.coin.bitcoin.service.BitcoinService;

@SpringBootTest
@ActiveProfiles("bitcoin")
@AutoConfigureMockMvc
class BitcoinServiceTest {

    @Autowired
    private BitcoinService coinService;

    @Test
    void contextLoads() {
        assertEquals(Coin.ZERO, coinService.getBalance(createWallet().currentChangeAddress()));
    }
}
