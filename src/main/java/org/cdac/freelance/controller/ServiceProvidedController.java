package org.cdac.freelance.controller;

import org.cdac.freelance.dto.service_provided.CreateRequestDTO;
import org.cdac.freelance.dto.service_provided.ServiceProvidedResponseDTO;
import org.cdac.freelance.services.service_provided.ServiceProvideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service_provided")
public class ServiceProvidedController {

    @Autowired
    ServiceProvideService serviceProvideService;

    @PostMapping
    public ResponseEntity<Boolean> createServiceProvided(CreateRequestDTO requestDTO){
        return ResponseEntity.ok(serviceProvideService.createServiceProvided(requestDTO));
    }

    @GetMapping("/byServiceId/{serviceId}")
    public ResponseEntity<List<ServiceProvidedResponseDTO>> getAllByServiceId(@PathVariable int serviceId){
        return ResponseEntity.ok(serviceProvideService.getAllByServiceId(serviceId));
    }

    @GetMapping("/byProviderId/{providerId}")
    public ResponseEntity<List<ServiceProvidedResponseDTO>> getAllByProviderId(@PathVariable int providerId){
        return ResponseEntity.ok(serviceProvideService.getAllByProviderId(providerId));
    }
}
