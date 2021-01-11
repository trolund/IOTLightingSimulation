package Services.PaymentServiceTest.dto;

import java.io.Serializable;

public class PaymentData implements Serializable {
    public int amount;
    public String creditor;
    public String debtor;
    public String description;
}
