package org.cdac.freelance.controller;

import org.cdac.freelance.dto.offers.CreateOfferDTO;
import org.cdac.freelance.dto.offers.OfferResponseDTO;
import org.cdac.freelance.services.offers.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    OffersService offersService;

    @PostMapping
    public ResponseEntity<Boolean> createOffer(@RequestBody CreateOfferDTO offerDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(offersService.creatOffer(offerDTO));
    }

    @GetMapping("/byClientId/{clientId}")
    public ResponseEntity<List<OfferResponseDTO>> getByClientId(@PathVariable int clientId){
        return ResponseEntity.ok(offersService.getByClientId(clientId));
    }

    @GetMapping("/byProviderId/{providerId}")
    public ResponseEntity<List<OfferResponseDTO>> getByProviderId(@PathVariable int providerId){
        return ResponseEntity.ok(offersService.getByProviderId(providerId));
    }
}
