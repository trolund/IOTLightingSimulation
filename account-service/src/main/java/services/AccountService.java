package services;

import dto.UserAccountDTO;
import exceptions.account.AccountException;
import exceptions.account.RemoteAccountDoesNotExistException;
import exceptions.account.RemoteAccountExistsException;
import infrastructure.bank.BankService;
import infrastructure.bank.BankServiceService;
import infrastructure.bank.User;
import infrastructure.repositories.interfaces.IAccountRepository;
import model.BankAccount;
import model.UserAccount;
import services.interfaces.IAccountService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AccountService implements IAccountService {

    BankService bs = new BankServiceService().getBankServicePort();

    @Inject
    IAccountRepository repo;

    @Inject
    MapperService mapper;

    @Override
    public String add(UserAccountDTO ua) throws Exception {
        try {
            return checkIfAccountExists(ua);
        } catch (RemoteAccountDoesNotExistException e1) {
            return createAccount(ua);
        }
    }

    @Override
    public UserAccountDTO getById(String id) throws AccountException {
        return mapper.map(repo.getById(id), UserAccountDTO.class);
    }

    @Override
    public UserAccountDTO getByCpr(String cpr) throws AccountException {
        return mapper.map(repo.getByCpr(cpr), UserAccountDTO.class);
    }

    @Override
    public List<UserAccountDTO> getAll() {
        mapper.mapList(repo.getAll(), UserAccountDTO.class);
        return mapper.mapList(repo.getAll(), UserAccountDTO.class);
    }

    @Override
    public void retireAccountByCpr(String cpr) throws RemoteAccountDoesNotExistException {
        try {
            UserAccount ua = repo.getByCpr(cpr);
            bs.retireAccount(ua.getBankAccount().getBankId());
            repo.remove(ua.getId());
        } catch (Exception e) {
            throw new RemoteAccountDoesNotExistException();
        }
    }

    @Override
    public void retireAccount(String id) throws RemoteAccountDoesNotExistException {
        try {
            UserAccount ua = repo.getById(id);
            bs.retireAccount(ua.getBankAccount().getBankId());
            repo.remove(ua.getId());
        } catch (Exception e) {
            throw new RemoteAccountDoesNotExistException();
        }
    }

    private String checkIfAccountExists(UserAccountDTO ua)
            throws RemoteAccountDoesNotExistException {
        try {
            bs.getAccountByCprNumber(ua.getCprNumber());
            return repo.getByCpr(ua.getCprNumber()).getId();
        } catch (Exception e) {
            throw new RemoteAccountDoesNotExistException();
        }
    }

    private String createAccount(UserAccountDTO ua)
            throws RemoteAccountExistsException {
        BigDecimal initialBalance = ua.getBankAccount().getBalance();

        User user = new User();
        user.setCprNumber(ua.getCprNumber());
        user.setFirstName(ua.getFirstName());
        user.setLastName(ua.getLastName());

        // try to create a new account
        try {
            UserAccount tempUa = mapper.map(ua, UserAccount.class);
            String bankId = bs.createAccountWithBalance(user, initialBalance);
            tempUa.getBankAccount().setBankId(bankId);
            tempUa.setBankAccount(new BankAccount(bankId, initialBalance));
            String internalId = String.valueOf(UUID.randomUUID());
            tempUa.setId(internalId);
            repo.add(tempUa);
            return internalId;
        } catch (Exception e) {
            throw new RemoteAccountExistsException();
        }
    }

}