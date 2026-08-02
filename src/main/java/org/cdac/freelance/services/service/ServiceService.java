package org.cdac.freelance.services.service;

import org.cdac.freelance.dto.service.ServiceResponseDTO;

import java.util.List;

public interface ServiceService {

    List<ServiceResponseDTO> getAllServices();
}
