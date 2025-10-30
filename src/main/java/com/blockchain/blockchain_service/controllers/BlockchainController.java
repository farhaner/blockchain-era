package com.blockchain.blockchain_service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class BlockchainController {

    @PostMapping(value = "/add")
    public String addData() {
        return null;
    }

    @GetMapping(value = "/get")
    public String getData() {
        return null;
    }

    @GetMapping(value = "/getAll")
    public List<String> getAlldata() {
        return null;
    }

    @PostMapping(value = "/update")
    public String updateData() {
        return null;
    }
}
