package com.github.blockchain.coin.utility;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.script.Script;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BitcoinConfiguration {

    final static NetworkParameters networkParameters = NetworkParameters.fromID(NetworkParameters.ID_TESTNET);
    final static Script.ScriptType scriptType = Script.ScriptType.P2WPKH;

}
