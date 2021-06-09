package com.github.blockchain.domain;

import static com.github.blockchain.coin.bitcoin.BitcoinConfiguration.toAddress;

import org.bitcoinj.core.Address;

import com.github.blockchain.controller.models.BlockBuy;
import com.github.blockchain.controller.models.BlockSell;

public class BlockTransaction {
    public Address fromAddress;
    public String fromKey;
    public Address toAddress;

    public BlockTransaction(String fromAddress, String fromKey, String toAddress) {
        this.fromAddress = toAddress(fromAddress);
        this.fromKey = fromKey;
        this.toAddress = toAddress(toAddress);
    }

    public static BlockTransaction convert(String toAddress, BlockSell blockSell) {
        return new BlockTransaction(blockSell.fromAddress, blockSell.privateKey, toAddress);
    }

    public static BlockTransaction convert(String fromAddress, BlockBuy blockBuy) {
        return new BlockTransaction(fromAddress, blockBuy.privateKeyFrom, blockBuy.toAddress);
    }
}
