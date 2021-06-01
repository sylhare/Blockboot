package com.github.blockchain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.blockchain.controller.models.BlockBuy;
import com.github.blockchain.controller.models.BlockSell;
import com.github.blockchain.controller.models.BlockStatus;
import com.github.blockchain.domain.BlockchainService;

@RestController
public class BlockchainController {

    @Autowired
    private BlockchainService blockService;

    @PostMapping(value = "/sellTo/{publicAddress}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sellTo(@PathVariable String publicAddress, @RequestBody BlockSell blockSell) {
        new Thread(() -> blockService.sell(publicAddress, blockSell)).start();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/buyFrom/{publicAddress}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> buyFrom(@PathVariable String publicAddress, @RequestBody BlockBuy blockBuy) {
        new Thread(() -> blockService.buy(publicAddress, blockBuy)).start();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/balance/{publicAddress}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BlockStatus> getBalance(@PathVariable String publicAddress) {
        return new ResponseEntity<>(blockService.get(publicAddress), HttpStatus.OK);
    }
}
