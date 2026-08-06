package org.cdac.freelance.services.offers;

import org.cdac.freelance.dto.offers.CreateOfferDTO;
import org.cdac.freelance.dto.offers.OfferResponseDTO;

import java.util.List;

public interface OffersService {
    boolean creatOffer(CreateOfferDTO offerDTO, int clientId);
    List<OfferResponseDTO> getByClientId(int clientId);
    List<OfferResponseDTO> getByProviderId(int providerId);
    boolean acceptOffer(int offerId);
    boolean rejectOffer(int offerId);
}
