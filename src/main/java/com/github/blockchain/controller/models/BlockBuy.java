package com.github.blockchain.controller.models;

public class BlockBuy {

    public String toAddress;
    public String privateKeyFrom;

    public BlockBuy(String toAddress, String privateKeyFrom) {
        this.toAddress = toAddress;
        this.privateKeyFrom = privateKeyFrom;
    }
}
