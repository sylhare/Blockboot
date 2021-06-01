package com.github.blockchain.domain;

import com.github.blockchain.controller.models.BlockBuy;
import com.github.blockchain.controller.models.BlockSell;

public class BlockTransaction {
    public String fromAddress;
    public String fromKey;
    public String toAddress;

    public BlockTransaction(String fromAddress, String fromKey, String toAddress) {
        this.fromAddress = fromAddress;
        this.fromKey = fromKey;
        this.toAddress = toAddress;
    }

    public static BlockTransaction convert(String toAddress, BlockSell blockSell) {
        return new BlockTransaction(blockSell.fromAddress, blockSell.privateKey, toAddress);
    }

    public static BlockTransaction convert(String fromAddress, BlockBuy blockBuy) {
        return new BlockTransaction(fromAddress, blockBuy.privateKeyFrom, blockBuy.toAddress);
    }
}
