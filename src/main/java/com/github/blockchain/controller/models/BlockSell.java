package com.github.blockchain.controller.models;

public class BlockSell {

    public String fromAddress;
    public String privateKey;

    public BlockSell(String fromAddress, String privateKey) {
        this.fromAddress = fromAddress;
        this.privateKey = privateKey;
    }
}
