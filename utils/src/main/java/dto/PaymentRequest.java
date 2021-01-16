package dto;
import java.io.Serializable;

public class PaymentRequest implements Serializable {

    private String customerId;
    private String merchantId;
    private int amount;
    private String token;

    public PaymentRequest() {
    }

    public PaymentRequest(String customerId, String merchantId, int amount, String token) {
        this.customerId = customerId;
        this.merchantId = merchantId;
        this.amount = amount;
        this.token = token;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
