package services;

import dto.AccountInformation;
import dto.BankAccountDTO;
import dto.UserAccountDTO;
import dto.UserRegistrationDTO;
import exceptions.account.AccountExistsException;
import exceptions.account.AccountNotFoundException;
import exceptions.account.AccountRegistrationException;
import exceptions.account.BankAccountException;
import infrastructure.bank.*;
import infrastructure.repositories.AccountRepository;
import infrastructure.repositories.interfaces.IAccountRepository;
import services.interfaces.IAccountService;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AccountService implements IAccountService {

    private final BankService bs = new BankServiceService().getBankServicePort();

    private final IAccountRepository repo = AccountRepository.getInstance();

    @Override
    public String register(UserRegistrationDTO userRegistrationDTO)
            throws AccountExistsException, AccountRegistrationException {

        if (isRegistered(userRegistrationDTO)) {
            throw new AccountExistsException("Account with cpr (" + userRegistrationDTO.getCprNumber() + ") already exists!");
        }

        try {
            return registerBankAccount(userRegistrationDTO);
        } catch (BankAccountException e) {
            throw new AccountRegistrationException(e.getMessage());
        }
    }

    @Override
    public UserAccountDTO get(String id) throws AccountNotFoundException {
        AccountInformation accountInformation = repo.getById(id);

        if (accountInformation == null) {
            throw new AccountNotFoundException("Account with id (" + id + ") is not found!");
        }

        try {
            return getUserAccountFromInfo(accountInformation);
        } catch (BankAccountException e) {
            throw new AccountNotFoundException("Account with id (" + id + ") is not found!");
        }
    }

    @Override
    public UserAccountDTO getByCpr(String cpr) throws AccountNotFoundException {
        AccountInformation accountInformation = repo.getByCpr(cpr);

        if (accountInformation == null) {
            throw new AccountNotFoundException("Account with cpr (" + cpr + ") is not found!");
        }

        try {
            return getUserAccountFromInfo(accountInformation);
        } catch (BankAccountException e) {
            throw new AccountNotFoundException("Account with cpr (" + cpr + ") is not found!");
        }
    }

    @Override
    public List<UserAccountDTO> getAll() throws BankAccountException {
        List<UserAccountDTO> userAccountDTOs = new ArrayList<>();
        for (AccountInformation accountInfo : repo.getAll()) {
            userAccountDTOs.add(getUserAccountFromInfo(accountInfo));
        }
        return userAccountDTOs;
    }

    @Override
    public void retireAccountByCpr(String cpr) throws BankAccountException {
        AccountInformation accountInformation = repo.getByCpr(cpr);

        // if it returns null,
        // it means the user doesn't exist
        if (accountInformation == null) {
            return;
        }

        retireAccountFromInfo(accountInformation);
    }

    @Override
    public void retireAccount(String id) throws BankAccountException {
        AccountInformation accountInformation = repo.getById(id);

        // if it returns null,
        // it means the user doesn't exist
        if (accountInformation == null) {
            return;
        }

        retireAccountFromInfo(accountInformation);
    }

    private boolean isRegistered(UserRegistrationDTO userRegistrationDTO) {
        AccountInformation accountInformation = repo.getByCpr(userRegistrationDTO.getCprNumber());
        return accountInformation != null;
    }

    private String registerBankAccount(UserRegistrationDTO userRegistrationDTO)
            throws BankAccountException {
        BigDecimal initialBalance = userRegistrationDTO.getBankAccount().getBalance();
        User user = new User();
        user.setCprNumber(userRegistrationDTO.getCprNumber());
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());

        // try to create a bank account for the user
        String bankId;
        try {
            bankId = bs.createAccountWithBalance(user, initialBalance);
        } catch (BankServiceException_Exception e) {
            throw new BankAccountException("Failed to create bank account" +
                    " for account with cpr (" + userRegistrationDTO.getCprNumber() + ")");
        }

        // if the creation has gone well,
        // create an internal uuid for the user
        // and add them to the repository
        String internalId = String.valueOf(UUID.randomUUID());

        AccountInformation newAccount = new AccountInformation();
        newAccount.setId(internalId);
        newAccount.setBankId(bankId);
        newAccount.setCpr(userRegistrationDTO.getCprNumber());

        repo.add(newAccount);

        // and return the internal id
        return internalId;
    }

    private Account getBankAccount(String bankId) throws BankAccountException {
        try {
            return bs.getAccount(bankId);
        } catch (BankServiceException_Exception e) {
            throw new BankAccountException(e.getMessage());
        }
    }

    private UserAccountDTO getUserAccountFromInfo(AccountInformation accountInformation)
            throws BankAccountException {
        Account bankAccount = getBankAccount(accountInformation.getBankId());

        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setId(accountInformation.getId());
        userAccountDTO.setFirstName(bankAccount.getUser().getFirstName());
        userAccountDTO.setLastName(bankAccount.getUser().getLastName());
        userAccountDTO.setCpr(bankAccount.getUser().getCprNumber());

        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setId(accountInformation.getBankId());
        bankAccountDTO.setBalance(bankAccount.getBalance());

        userAccountDTO.setBankAccount(bankAccountDTO);

        return userAccountDTO;
    }

    private void retireAccountFromInfo(AccountInformation accountInformation)
            throws BankAccountException {
        try {
            bs.retireAccount(accountInformation.getBankId());
            repo.remove(accountInformation.getId());
        } catch (BankServiceException_Exception e) {
            throw new BankAccountException(e.getMessage());
        }
    }

}