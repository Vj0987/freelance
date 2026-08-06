package org.cdac.freelance.client;

import org.cdac.freelance.dto.EscrowClient.CreateEscrowRecordDTO;
import org.cdac.freelance.dto.EscrowClient.EscrowRecordResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "escrow", url = "http://localhost:8090")
public interface EscrowClient {

    @PostMapping("/escrow_record")
    EscrowRecordResponseDTO createEscrowRecord(
            @RequestBody CreateEscrowRecordDTO escrowRecordDTO);

    @GetMapping("/escrow_record/check_status/{escrowId}")
    boolean checkEscrowStatusAsTerminated(
            @PathVariable("escrowId") int escrowId);

    @PutMapping("/escrow_record/status-terminated/{escrowId}")
    boolean terminateEscrowRecord(@PathVariable int escrowId);
}
