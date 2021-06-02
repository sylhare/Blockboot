package com.github.blockchain.coin.utility;

import static com.github.blockchain.coin.utility.BitcoinConfiguration.networkParameters;
import static com.github.blockchain.coin.utility.BitcoinConfiguration.scriptType;

import java.util.Objects;

import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;

@Component
public class BitcoinWallet {

    public static Wallet createWallet() {
        return Wallet.createDeterministic(networkParameters, scriptType);
    }

    public static DeterministicSeed walletSeed(Wallet wallet) {
        DeterministicSeed seed = wallet.getKeyChainSeed();
        String seedCode = Joiner.on(" ").join(Objects.requireNonNull(seed.getMnemonicCode()));
        long creationTime = seed.getCreationTimeSeconds();

        System.out.println("Seed words are: " + seedCode);
        System.out.println("Seed birthday is: " + creationTime);

        return seed;
    }

    public static Wallet walletFromSeed(DeterministicSeed seed) {
        return Wallet.fromSeed(networkParameters, seed, scriptType);
    }
}
