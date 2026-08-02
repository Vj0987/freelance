package org.cdac.freelance.controller;

import org.cdac.freelance.dto.service.ServiceResponseDTO;
import org.cdac.freelance.services.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    ServiceService serviceService;

    @GetMapping
    public ResponseEntity<List<ServiceResponseDTO>> getAllServices(){
        return ResponseEntity.ok(serviceService.getAllServices());
    }
}
