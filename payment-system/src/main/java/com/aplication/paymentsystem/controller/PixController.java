package com.aplication.paymentsystem.controller;

import com.aplication.paymentsystem.domain.DTO.PixChargeDTO;
import com.aplication.paymentsystem.service.PixService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment")
public class PixController {
    @Autowired
    private PixService pixService;

    @GetMapping
    public ResponseEntity createPixEVP(){

        JSONObject response = this.pixService.pixCreateEVP();
        return ResponseEntity.ok(response);

    }

    @PostMapping
    public ResponseEntity pixCreateCharge(@RequestBody PixChargeDTO pixChargeDTO){

        JSONObject response = this.pixService.pixChargeCreate(pixChargeDTO);

        return ResponseEntity.ok(response);
    }
}
