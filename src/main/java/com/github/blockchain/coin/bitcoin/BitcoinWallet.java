package com.github.blockchain.coin.bitcoin;

import static com.github.blockchain.coin.bitcoin.BitcoinConfiguration.LOG;
import static com.github.blockchain.coin.bitcoin.BitcoinConfiguration.networkParameters;
import static com.github.blockchain.coin.bitcoin.BitcoinConfiguration.scriptType;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.tomcat.util.buf.StringUtils;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.store.SPVBlockStore;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;
import org.springframework.stereotype.Component;

@Component
public class BitcoinWallet {

    public static Wallet createWallet() {
        Wallet wallet = Wallet.createDeterministic(networkParameters, scriptType);
        wallet.autosaveToFile(new File("src/main/resources/file").getAbsoluteFile(), 1000L, TimeUnit.SECONDS, null);
        return wallet;
    }

    public static BlockChain createBlockchain(Wallet wallet) {
        try {
            MemoryBlockStore blockStore = new MemoryBlockStore(networkParameters);
            return new BlockChain(networkParameters, wallet, blockStore);
        } catch (Exception e) {
            LOG.warn("Could not create blockchain", e);
            return null;
        }
    }

    public static BlockChain createSPVBlockChain(Wallet wallet) {
        File chainFile = new File("src/main/resources/blockchain.spvchain").getAbsoluteFile();

        try {
            SPVBlockStore blockStore = new SPVBlockStore(networkParameters, chainFile);
            return new BlockChain(networkParameters, wallet, blockStore);
        } catch (Exception e) {
            LOG.warn("Could not create blockchain", e);
            return null;
        }
    }

    public static PeerGroup createPeerGroup(BlockChain blockChain, Wallet wallet) {
        PeerGroup peerGroup = new PeerGroup(networkParameters, blockChain);
        peerGroup.addWallet(wallet);
        peerGroup.addPeerDiscovery(new DnsDiscovery(networkParameters));
        return peerGroup;
    }

    public static DeterministicSeed walletSeed(Wallet wallet) {
        DeterministicSeed seed = wallet.getKeyChainSeed();
        String seedCode = StringUtils.join(seed.getMnemonicCode(), ' ');
        long creationTime = seed.getCreationTimeSeconds();

        LOG.info("Seed words are: " + seedCode);
        LOG.info("Seed birthday is: " + creationTime);

        return seed;
    }

    public static Wallet walletFromSeed(DeterministicSeed seed) {
        return Wallet.fromSeed(networkParameters, seed, scriptType);
    }
}
