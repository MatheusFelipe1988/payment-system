package com.aplication.paymentsystem.controller;

import com.aplication.paymentsystem.service.PixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment")
public class PixController {
    @Autowired
    private PixService pixService;
}
