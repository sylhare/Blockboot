package com.github.blockchain.coin;

import static com.github.blockchain.coin.utility.BitcoinWallet.createBlockchain;
import static com.github.blockchain.coin.utility.BitcoinWallet.createWallet;

import org.bitcoinj.core.BlockChain;
import org.bitcoinj.wallet.Wallet;
import org.springframework.stereotype.Service;

@Service
public class BitcoinService {

    private Wallet wallet = createWallet();
    private BlockChain blockChain = createBlockchain(wallet);
}
