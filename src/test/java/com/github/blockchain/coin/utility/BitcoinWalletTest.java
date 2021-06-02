package com.github.blockchain.coin.utility;

import static com.github.blockchain.coin.utility.BitcoinConfiguration.networkParameters;
import static com.github.blockchain.coin.utility.BitcoinWallet.createWallet;
import static com.github.blockchain.coin.utility.BitcoinWallet.walletFromSeed;
import static com.github.blockchain.coin.utility.BitcoinWallet.walletSeed;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

import org.bitcoinj.script.Script;
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

}
