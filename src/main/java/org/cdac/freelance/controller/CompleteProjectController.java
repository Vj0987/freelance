package org.cdac.freelance.controller;

import org.cdac.freelance.dto.completed_project.CompletedProjectResponseDTO;
import org.cdac.freelance.dto.completed_project.CreateCompleteProjectDTO;
import org.cdac.freelance.dto.contract.CreateContractRequestDTO;
import org.cdac.freelance.security.CustomUserPrincipal;
import org.cdac.freelance.services.completed_project.CompletedProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class CompleteProjectController {

    @Autowired
    CompletedProjectService completedProjectService;

    @PostMapping
    public ResponseEntity<Boolean> createCompletedProject(@RequestBody CreateCompleteProjectDTO projectDTO){
        return ResponseEntity.ok(completedProjectService.createCompletedProject(projectDTO.getContractId()));
    }

    @GetMapping
    public ResponseEntity<List<CompletedProjectResponseDTO>> getByProviderId(@AuthenticationPrincipal CustomUserPrincipal user){
        return ResponseEntity.ok(completedProjectService.getByProviderId(user.getUserId()));
    }
}
