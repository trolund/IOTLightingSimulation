package dto;

//import org.eclipse.persistence.jaxb.MarshallerProperties;

import io.cucumber.gherkin.Token;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;

/**
 * @author Troels (s161791)
 * TransactionDTO.
 */
@XmlRootElement(name = "TransactionDTO")
public class TransactionDTO implements Serializable {

    private BigDecimal amount;
    private BigDecimal balance;
    private String creditor;
    private String debtor;
    private String description;
    private XMLGregorianCalendar time;
    private String tokenId;

    public TransactionDTO() {

    }

    public TransactionDTO(BigDecimal amount, String creditor, String debtor, String tokenId) {
        this.amount = amount;
        this.creditor = creditor;
        this.debtor = debtor;
        this.tokenId = tokenId;
    }

    public TransactionDTO(BigDecimal amount, BigDecimal balance, String creditor, String debtor, String description, XMLGregorianCalendar time, String tokenId) {
        this.amount = amount;
        this.balance = balance;
        this.creditor = creditor;
        this.debtor = debtor;
        this.description = description;
        this.time = time;
        this.tokenId = tokenId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public XMLGregorianCalendar getTime() {
        return time;
    }

    public void setTime(XMLGregorianCalendar time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "amount=" + amount +
                ", balance=" + balance +
                ", creditor='" + creditor + '\'' +
                ", debtor='" + debtor + '\'' +
                ", description='" + description + '\'' +
                ", time=" + time +
                '}';
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}