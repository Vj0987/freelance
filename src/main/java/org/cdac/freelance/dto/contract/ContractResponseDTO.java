package org.cdac.freelance.dto.contract;

import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContractResponseDTO {

    private int contractId;
    private int providerId;
    private String providerName;
    private String clientName;
    private int clientId;
    private int escrowId;
    private BigDecimal amount;
    private LocalDate completionDate;

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
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

    public int getEscrowId() {
        return escrowId;
    }

    public void setEscrowId(int escrowId) {
        this.escrowId = escrowId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }
}
