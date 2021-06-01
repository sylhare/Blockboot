package com.github.blockchain.coin;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.github.blockchain.domain.BlockTransaction;
import com.github.blockchain.domain.TransactionStatus;

@Service
@Profile("mock")
public class MockCoinService implements CoinService {

    @Override
    public TransactionStatus read(String publicAddress) {
        return TransactionStatus.REFUSED;
    }

    @Override
    public void write(BlockTransaction blockTransaction, long amount) throws Exception {

    }
}
