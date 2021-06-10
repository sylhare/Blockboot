package com.github.blockchain.coin.bitcoin.service;

import static com.github.blockchain.coin.bitcoin.BitcoinConfiguration.LOG;
import static com.github.blockchain.coin.bitcoin.BitcoinConfiguration.toAddress;
import static com.github.blockchain.coin.bitcoin.BitcoinWallet.createBlockchain;
import static com.github.blockchain.coin.bitcoin.BitcoinWallet.createPeerGroup;
import static com.github.blockchain.coin.bitcoin.BitcoinWallet.createWallet;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.github.blockchain.coin.CoinService;
import com.github.blockchain.coin.bitcoin.old.AddressBalance;
import com.github.blockchain.domain.BlockTransaction;
import com.github.blockchain.domain.TransactionStatus;

@Service
@Profile("bitcoin")
public class BitcoinService implements CoinService {

    private final Wallet wallet = createWallet();
    @Value("${blockboot.private.key:unknown}")
    private String privateWalletKey;
    private BlockChain blockChain;
    private PeerGroup peerGroup;

    @PostConstruct
    private void setup() {
        if (!Objects.equals(privateWalletKey, "unknown")) {
            LOG.info("Creating wallet, blockChain and peerGroup");
            wallet.importKey(ECKey.fromPrivate(Base58.decodeToBigInteger(privateWalletKey)));
            LOG.info("Wallet Active key chain {}", wallet.getActiveKeyChain().toString());
            blockChain = createBlockchain(wallet);
            peerGroup = createPeerGroup(blockChain, wallet);
            start();
        }
    }

    private void start() {
        LOG.info("Starting peerGroup and downloading blockchain");
        peerGroup.startAsync();
        peerGroup.downloadBlockChain(); // very big
    }

    public Coin getBalance(Address address) {
        Coin balance = wallet.getBalance(new AddressBalance(address));
        LOG.info("Balance is {}", balance.toFriendlyString());
        return balance;
    }

    public void sendCoins(Address targetAddress, String coinValue) throws InsufficientMoneyException, ExecutionException, InterruptedException {
        Wallet.SendResult result = wallet.sendCoins(peerGroup, targetAddress, Coin.parseCoin(coinValue));
        LOG.info("Transaction {} was completed {}", result.tx, result.broadcastComplete.get());
    }

    @Override
    public TransactionStatus read(String publicAddress) {
        Coin balance = getBalance(toAddress(publicAddress));
        LOG.info("Balance of {} is at {} BTC", publicAddress, balance.toFriendlyString());
        return TransactionStatus.CONFIRMED;
    }

    @Override
    public void write(BlockTransaction blockTransaction, String amount) throws Exception {
        sendCoins(blockTransaction.toAddress, amount);
    }
}
