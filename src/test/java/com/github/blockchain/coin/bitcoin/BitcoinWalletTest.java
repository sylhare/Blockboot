package com.github.blockchain.coin.bitcoin;

import static com.github.blockchain.coin.bitcoin.BitcoinConfiguration.scriptType;
import static com.github.blockchain.coin.bitcoin.BitcoinWallet.createWallet;
import static com.github.blockchain.coin.bitcoin.BitcoinWallet.walletFromSeed;
import static com.github.blockchain.coin.bitcoin.BitcoinWallet.walletSeed;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Objects;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.junit.jupiter.api.Test;

import com.google.common.base.Joiner;

class BitcoinWalletTest {

    @Test
    public void createWalletTest() {
        Wallet wallet = createWallet();
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
}
