package com.github.blockchain.coin.bitcoin;

import static com.github.blockchain.coin.bitcoin.BitcoinConfiguration.scriptType;
import static com.github.blockchain.coin.bitcoin.BitcoinWallet.createBlockchain;
import static com.github.blockchain.coin.bitcoin.BitcoinWallet.createSPVBlockChain;
import static com.github.blockchain.coin.bitcoin.BitcoinWallet.createWallet;
import static com.github.blockchain.coin.bitcoin.BitcoinWallet.walletFromSeed;
import static com.github.blockchain.coin.bitcoin.BitcoinWallet.walletSeed;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.store.SPVBlockStore;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.junit.jupiter.api.Test;

import com.google.common.base.Joiner;

class BitcoinWalletTest {

    @Test
    public void createWalletTest() throws IOException {
        Wallet wallet = createWallet();
        wallet.saveToFile(new File("src/test/resources/wallets").getAbsoluteFile());
        assertEquals(wallet.getKeyChainSeed(), walletSeed(wallet));
    }

    @Test
    public void retrieveCreatedWalletTest() throws UnreadableWalletException {
        Wallet wallet = createWallet();

        DeterministicSeed seed = walletSeed(wallet);
        String seedCode = Joiner.on(" ").join(Objects.requireNonNull(seed.getMnemonicCode()));
        long creationTime = seed.getCreationTimeSeconds();

        DeterministicSeed recoveredSeed = new DeterministicSeed(seedCode, null, "", creationTime);
        Wallet restoredWallet = walletFromSeed(recoveredSeed);

        assertEquals(wallet.getKeyChainSeed(), restoredWallet.getKeyChainSeed());
    }

    @Test
    public void addressTests() {
        Wallet wallet = createWallet();
        Address receiveAddress = wallet.currentReceiveAddress();
        ECKey receivedECKey = wallet.currentReceiveKey();
        Address receiveNewAddress = wallet.freshReceiveAddress();

        assertEquals(receiveAddress.getParameters(), wallet.getParams());
        assertEquals(receiveAddress, Address.fromKey(wallet.getParams(), receivedECKey, scriptType));
        assertNotEquals(receiveNewAddress, receiveAddress);
    }

    @Test
    public void blockChainTest() {
        Wallet wallet = createWallet();
        BlockChain blockChainSPV = createSPVBlockChain(wallet);
        BlockChain blockChain = createBlockchain(wallet);

        assertTrue(blockChainSPV.getBlockStore() instanceof SPVBlockStore);
        assertTrue(blockChain.getBlockStore() instanceof MemoryBlockStore);
        assertNotEquals(blockChain.getBestChainHeight(), blockChainSPV.getBestChainHeight());
        // assertEquals(0, blockChain.getBestChainHeight());
        // assertEquals(1977035, blockChainSPV.getBestChainHeight());
    }
}
