package org.cdac.freelance.services.completed_project;

import org.cdac.freelance.dto.completed_project.CompletedProjectResponseDTO;
import org.cdac.freelance.dto.completed_project.CreateCompleteProjectDTO;

import java.util.List;

public interface CompletedProjectService {

    boolean createCompletedProject(int  contractId);
    List<CompletedProjectResponseDTO> getByProviderId(int providerId);
}
