package org.cdac.freelance.controller;

import org.cdac.freelance.dto.contract.ContractResponseDTO;
import org.cdac.freelance.dto.contract.CreateContractRequestDTO;
import org.cdac.freelance.services.contract.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contract")
public class ContractController {
    @Autowired
    ContractService contractService;

    @PostMapping
    public ResponseEntity<Boolean> createContract(@RequestBody CreateContractRequestDTO contractRequestDTO){
        return ResponseEntity.ok(contractService.createContract(contractRequestDTO));
    }

    @GetMapping("/provider")
    public ResponseEntity<List<ContractResponseDTO>> getContractByProviderId(@PathVariable int providerId){
        return  ResponseEntity.ok(contractService.getAllContractByProviderId(providerId));
    }

    public ResponseEntity<List<ContractResponseDTO>> getContractByClientId(@PathVariable int clientId){
        return ResponseEntity.ok(contractService.getAllContractByClientId(clientId));
    }
}
