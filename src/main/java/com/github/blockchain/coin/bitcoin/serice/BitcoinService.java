package com.github.blockchain.coin.bitcoin.serice;

import static com.github.blockchain.coin.bitcoin.BitcoinConfiguration.LOG;
import static com.github.blockchain.coin.bitcoin.BitcoinWallet.createBlockchain;
import static com.github.blockchain.coin.bitcoin.BitcoinWallet.createPeerGroup;
import static com.github.blockchain.coin.bitcoin.BitcoinWallet.createWallet;

import java.io.File;
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
import org.bitcoinj.core.Transaction;
import org.bitcoinj.store.SPVBlockStore;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.github.blockchain.coin.CoinService;
import com.github.blockchain.domain.BlockTransaction;
import com.github.blockchain.domain.TransactionStatus;

@Service
@Profile("bitcoin")
public class BitcoinService implements CoinService {

    @Value("${blockboot.private.key:unknown}")
    private String privateWalletKey;

    private final Wallet wallet = createWallet();
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
        }
    }

    private void start() {
        LOG.info("Starting peerGroup and downloading blockchain");
        peerGroup.startAsync();
        peerGroup.downloadBlockChain(); // very big
    }

    public Coin getBalance() {
        Coin balance = wallet.getBalance();
        LOG.info("Balance is {}", balance.toFriendlyString());
        return balance;
    }

    public Transaction sendCoins(Address targetAddress, String coinValue) throws InsufficientMoneyException, ExecutionException, InterruptedException {
        Wallet.SendResult result = wallet.sendCoins(peerGroup, targetAddress, Coin.parseCoin(coinValue));
        return result.broadcastComplete.get();
    }

    @Override
    public TransactionStatus read(String publicAddress) {
        return null;
    }

    @Override
    public void write(BlockTransaction blockTransaction, String amount) throws Exception {

    }
}
