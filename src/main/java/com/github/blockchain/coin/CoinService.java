package com.github.blockchain.coin;

import com.github.blockchain.domain.BlockTransaction;
import com.github.blockchain.domain.TransactionStatus;

public interface CoinService {
    TransactionStatus read(String publicAddress);

    void write(BlockTransaction blockTransaction, long amount) throws Exception;
}
