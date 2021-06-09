package com.github.blockchain.coin.utility;

import static com.github.blockchain.coin.utility.BitcoinConfiguration.LOG;
import static com.github.blockchain.coin.utility.BitcoinConfiguration.networkParameters;
import static com.github.blockchain.coin.utility.BitcoinConfiguration.scriptType;

import java.util.Objects;

import org.bitcoinj.core.BlockChain;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;

@Component
public class BitcoinWallet {

    public static Wallet createWallet() {
        return Wallet.createDeterministic(networkParameters, scriptType);
    }

    public static BlockChain createBlockchain(Wallet wallet) {
        try {
            final MemoryBlockStore blockStore = new MemoryBlockStore(Objects.requireNonNull(networkParameters));
            return new BlockChain(networkParameters, wallet, blockStore);
        } catch (Exception e) {
            LOG.warn("Could not create blockchain", e);
            return null;
        }
    }

    public static DeterministicSeed walletSeed(Wallet wallet) {
        DeterministicSeed seed = wallet.getKeyChainSeed();
        String seedCode = Joiner.on(" ").join(Objects.requireNonNull(seed.getMnemonicCode()));
        long creationTime = seed.getCreationTimeSeconds();

        LOG.info("Seed words are: " + seedCode);
        LOG.info("Seed birthday is: " + creationTime);

        return seed;
    }

    public static Wallet walletFromSeed(DeterministicSeed seed) {
        return Wallet.fromSeed(networkParameters, seed, scriptType);
    }
}
