package org.cdac.freelance.services.offers;

import org.cdac.freelance.dto.offers.CreateOfferDTO;
import org.cdac.freelance.dto.offers.OfferResponseDTO;
import org.cdac.freelance.entity.Offers;
import org.cdac.freelance.enums.OfferStatus;
import org.cdac.freelance.repository.OffersRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffersServiceImpl implements OffersService{

    @Autowired
    OffersRepository offersRepository;


    @Override
    public boolean creatOffer(CreateOfferDTO offerDTO) {

        if(offersRepository.existsByClientIdAndProviderIdAndServiceId
                (offerDTO.getClientId(),
                 offerDTO.getProviderId(),
                 offerDTO.getServiceId()))

            throw new RuntimeException("Offer already Exist");

        Offers offer = new Offers();

        offer.setClientId(offerDTO.getClientId());
        offer.setProviderId(offerDTO.getProviderId());
        offer.setServiceId(offerDTO.getServiceId());
        offer.setBudget(offerDTO.getBudget());
        offer.setTitle(offerDTO.getTitle());
        offer.setDescription(offerDTO.getDescription());
        offer.setStatus(OfferStatus.OFFERED);

        offersRepository.save(offer);
        return true;
    }

    @Override
    public List<OfferResponseDTO> getByClientId(int clientId) {
        return offersRepository
                .findByClientId(clientId)
                .stream()
                .map(offers -> {
                    OfferResponseDTO responseDTO = new OfferResponseDTO();
                    BeanUtils.copyProperties(offers, responseDTO);
                    return responseDTO;
                }

        ).toList();
    }

    @Override
    public List<OfferResponseDTO> getByProviderId(int providerId) {
        return offersRepository
                .findByProviderId(providerId)
                .stream()
                .map(offers -> {
                            OfferResponseDTO responseDTO = new OfferResponseDTO();
                            BeanUtils.copyProperties(offers, responseDTO);
                            return responseDTO;
                        }

                ).toList();
    }
}
