package org.cdac.freelance.client;

import org.cdac.freelance.dto.EscrowClient.CreateEscrowRecordDTO;
import org.cdac.freelance.dto.EscrowClient.EscrowRecordResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "escrow",
             url = "http://localhost:8090")
public interface EscrowClient {

    @PostMapping("/escrow_record")
    EscrowRecordResponseDTO createEscrowRecord(@RequestBody CreateEscrowRecordDTO escrowRecordDTO);
}
