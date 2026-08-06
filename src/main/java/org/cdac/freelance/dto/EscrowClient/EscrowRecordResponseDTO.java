package org.cdac.freelance.dto.EscrowClient;

import org.cdac.freelance.enums.EscrowStatus;

import java.math.BigDecimal;

public class EscrowRecordResponseDTO {

    private int escrowRecordId;
    private int escrowId;
    private int clientId;
    private int providerId;
    private BigDecimal amount;
    private EscrowStatus status;
    private BigDecimal cancelFee;

    public int getEscrowRecordId() {
        return escrowRecordId;
    }

    public void setEscrowRecordId(int escrowRecordId) {
        this.escrowRecordId = escrowRecordId;
    }

    public int getEscrowId() {
        return escrowId;
    }

    public void setEscrowId(int escrowId) {
        this.escrowId = escrowId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public EscrowStatus getStatus() {
        return status;
    }

    public void setStatus(EscrowStatus status) {
        this.status = status;
    }

    public BigDecimal getCancelFee() {
        return cancelFee;
    }

    public void setCancelFee(BigDecimal cancelFee) {
        this.cancelFee = cancelFee;
    }
}


