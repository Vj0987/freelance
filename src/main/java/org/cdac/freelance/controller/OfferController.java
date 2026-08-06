package org.cdac.freelance.controller;

import org.cdac.freelance.dto.offers.CreateOfferDTO;
import org.cdac.freelance.dto.offers.OfferResponseDTO;
import org.cdac.freelance.security.CustomUserPrincipal;
import org.cdac.freelance.services.offers.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    OffersService offersService;

    @PostMapping
    public ResponseEntity<Boolean> createOffer(@RequestBody CreateOfferDTO offerDTO, @AuthenticationPrincipal CustomUserPrincipal user){
        return ResponseEntity.status(HttpStatus.CREATED).body(offersService.creatOffer(offerDTO, user.getUserId()));
    }

    @GetMapping("/byClientId")
    public ResponseEntity<List<OfferResponseDTO>> getByClientId(@AuthenticationPrincipal CustomUserPrincipal user){
        return ResponseEntity.ok(offersService.getByClientId(user.getUserId()));
    }

    @GetMapping("/byProviderId")
    public ResponseEntity<List<OfferResponseDTO>> getByProviderId(@AuthenticationPrincipal CustomUserPrincipal user){
        return ResponseEntity.ok(offersService.getByProviderId(user.getUserId()));
    }

    @PutMapping("/accept_offer/{offerId}")
    public ResponseEntity<Boolean> acceptOffer(@PathVariable int offerId){
        return ResponseEntity.ok(offersService.acceptOffer(offerId));
    }

    @PutMapping("/reject_offer/{offerId}")
    public ResponseEntity<Boolean> rejectOffer(@PathVariable int offerId){
        return ResponseEntity.ok(offersService.rejectOffer(offerId));
    }
}
