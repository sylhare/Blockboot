package com.github.blockchain.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.blockchain.coin.CoinService;
import com.github.blockchain.controller.models.BlockBuy;
import com.github.blockchain.controller.models.BlockSell;
import com.github.blockchain.controller.models.BlockStatus;

@Service
public class BlockchainService {

    private static final String DEFAULT_AMOUNT = "1000";

    @Autowired
    private CoinService coinService;

    public BlockStatus get(String publicAddress) {
        return new BlockStatus(coinService.read(publicAddress).toString());
    }

    public void buy(String fromAddress, BlockBuy blockBuy) {
        try {
            coinService.write(BlockTransaction.convert(fromAddress, blockBuy), DEFAULT_AMOUNT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sell(String toAddress, BlockSell blockSell) {
        try {
            coinService.write(BlockTransaction.convert(toAddress, blockSell), DEFAULT_AMOUNT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
