package services;

import dto.Transaction;
import dto.TransactionDTO;
import dto.UserAccountDTO;
import exceptions.account.AccountNotFoundException;
import exceptions.transaction.TransactionNotFoundException;
import infrastructure.bank.BankService;
import services.interfaces.ITransactionService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;

/**
 * @primary-author Daniel (s151641)
 * @co-author Troels (s161791)
 */
@ApplicationScoped
public class TransactionService implements ITransactionService {

    BankService bs;
    MapperService mapper;

    @Inject
    public TransactionService(Bank bank, MapperService mapper) {
        this.bs = bank.getBankServicePort();
        this.mapper = mapper;
    }

    @Override
    public List<TransactionDTO> getTransactions(String id) throws AccountNotFoundException {
        // todo remove this when account-service rabbitmq works
        AccountServiceStub accountServiceStub = new AccountServiceStub();
        UserAccountDTO account = accountServiceStub.getAccount(id);
        if (account == null) {
            throw new AccountNotFoundException("Account (" + id + ") is not found!");
        }


        try {
            return mapper.mapList(bs.getAccount(account.getBankAccount().getId()).getTransactions(), TransactionDTO.class);
        } catch (Exception e) {
            throw new AccountNotFoundException("Account (" + id + ") is not found!");
        }
    }

    @Override
    public TransactionDTO getLatestTransaction(String id)
            throws AccountNotFoundException, TransactionNotFoundException {
        try {


            // todo remove this when account-service rabbitmq works
            AccountServiceStub accountServiceStub = new AccountServiceStub();
            UserAccountDTO account = accountServiceStub.getAccount(id);
            if (account == null) {
                throw new AccountNotFoundException("Account (" + id + ") is not found!");
            }


            Comparator<infrastructure.bank.Transaction> comparator = (p1, p2) -> p1.getTime().compare(p2.getTime());
            List<infrastructure.bank.Transaction> transactions = bs.getAccount(account.getBankAccount().getId()).getTransactions();


            if (transactions == null || transactions.isEmpty()) {
                throw new TransactionNotFoundException("Account (" + id + ") does not have any transactions");
            }

            infrastructure.bank.Transaction latest = transactions.stream().max(comparator).get();

            return mapper.map(latest, TransactionDTO.class);
        } catch (Exception e) {
            throw new AccountNotFoundException("Account (" + id + ") is not found!");
        }
    }

}