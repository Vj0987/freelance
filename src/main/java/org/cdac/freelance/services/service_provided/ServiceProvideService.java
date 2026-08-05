package org.cdac.freelance.services.service_provided;

import org.cdac.freelance.dto.service_provided.CreateRequestDTO;
import org.cdac.freelance.dto.service_provided.ServiceProvidedResponseDTO;

import java.util.List;

public interface ServiceProvideService {

    boolean createServiceProvided(CreateRequestDTO requestDTO);
    List<ServiceProvidedResponseDTO> getAllByServiceId(int serviceId);
    List<ServiceProvidedResponseDTO> getAllByProviderId(int providerId);
}
