package com.github.blockchain.coin.bitcoin;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.SegwitAddress;
import org.bitcoinj.script.Script;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BitcoinConfiguration {

    public static Logger LOG = LoggerFactory.getLogger(BitcoinConfiguration.class.getSimpleName());

    final static NetworkParameters networkParameters = NetworkParameters.fromID(NetworkParameters.ID_TESTNET);
    final static Script.ScriptType scriptType = Script.ScriptType.P2WPKH;

    public static Address toAddress(String address) {
        return SegwitAddress.fromBech32(networkParameters, address);
    }
}
