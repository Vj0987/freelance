package org.cdac.freelance.services.offers;

import org.cdac.freelance.dto.offers.CreateOfferDTO;
import org.cdac.freelance.dto.offers.OfferResponseDTO;
import org.cdac.freelance.entity.Offers;
import org.cdac.freelance.entity.Users;
import org.cdac.freelance.enums.OfferStatus;
import org.cdac.freelance.exceptions.UserNotFoundException;
import org.cdac.freelance.repository.OffersRepository;
import org.cdac.freelance.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffersServiceImpl implements OffersService {

    @Autowired
    OffersRepository offersRepository;

    @Autowired
    UsersRepository usersRepository;


    @Override
    public boolean creatOffer(CreateOfferDTO offerDTO, int clientId) {

        if (offersRepository.existsByClientIdAndProviderIdAndServiceId
                (clientId,
                        offerDTO.getProviderId(),
                        offerDTO.getServiceId()))

            throw new RuntimeException("Offer already Exist");

        Offers offer = new Offers();

        offer.setClientId(clientId);
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
                .findByClientIdAndStatusNot(clientId, OfferStatus.CONTRACTED)
                .stream()
                .map(offers -> {

                            Users client = usersRepository
                                    .findById(offers.getClientId())
                                    .orElseThrow(() -> new UserNotFoundException("Client Not Found"));

                            Users provider = usersRepository
                                    .findById(offers.getProviderId())
                                    .orElseThrow(() -> new UserNotFoundException("Provider Not found"));

                            OfferResponseDTO responseDTO = getOfferResponseDTO(offers, client, provider);
                            return responseDTO;
                        }

                ).toList();
    }


    @Override
    public List<OfferResponseDTO> getByProviderId(int providerId) {
        Users provider = usersRepository
                .findById(providerId)
                .orElseThrow(() -> new UserNotFoundException("Provider Not found"));

        return offersRepository
                .findByProviderIdAndStatusNot(providerId, OfferStatus.CONTRACTED)
                .stream()
                .map(offers -> {
                            Users client = usersRepository
                                    .findById(offers.getClientId())
                                    .orElseThrow(() -> new UserNotFoundException("Client Not Found"));

                            OfferResponseDTO responseDTO = getOfferResponseDTO(offers, client, provider);
                            return responseDTO;
                        }

                ).toList();
    }

    @Override
    public boolean acceptOffer(int offerId) {
        Offers offer = offersRepository
                .findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer Not found"));

        offer.setStatus(OfferStatus.ACCEPTED);

        offersRepository.save(offer);
        return true;
    }

    @Override
    public boolean rejectOffer(int offerId) {
        Offers offer = offersRepository
                .findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer Not found"));

        offer.setStatus(OfferStatus.REJECTED);

        offersRepository.save(offer);
        return true;
    }

    private static OfferResponseDTO getOfferResponseDTO(Offers offers, Users client, Users provider) {
        OfferResponseDTO responseDTO = new OfferResponseDTO();

        responseDTO.setOfferId(offers.getOfferId());
        responseDTO.setClientId(offers.getClientId());
        responseDTO.setClientName(client.getFullName());
        responseDTO.setProviderId(provider.getUserId());
        responseDTO.setProviderName(provider.getFullName());
        responseDTO.setServiceId(offers.getServiceId());
        responseDTO.setBudget(offers.getBudget());
        responseDTO.setDescription(offers.getDescription());
        responseDTO.setTitle(offers.getTitle());
        responseDTO.setStatus(offers.getStatus());
        return responseDTO;
    }
}
