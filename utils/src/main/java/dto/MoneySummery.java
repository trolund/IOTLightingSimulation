package dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class MoneySummery implements Serializable {
    Map<String, BigDecimal> summary;
    List<TransactionDTO> transactions;

    public MoneySummery() {
    }

    public MoneySummery(Map<String, BigDecimal> summary, List<TransactionDTO> transactions) {
        this.summary = summary;
        this.transactions = transactions;
    }

    public Map<String, BigDecimal> getSummary() {
        return summary;
    }

    public void setSummary(Map<String, BigDecimal> summary) {
        this.summary = summary;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }
}
