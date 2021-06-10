package com.github.blockchain.coin;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.github.blockchain.domain.BlockTransaction;
import com.github.blockchain.domain.TransactionStatus;

@Profile("mock")
@Service
public class MockCoinService implements CoinService {

    @Override
    public TransactionStatus read(String publicAddress) {
        return TransactionStatus.REFUSED;
    }

    @Override
    public void write(BlockTransaction blockTransaction, String amount) throws Exception {
    }
}
