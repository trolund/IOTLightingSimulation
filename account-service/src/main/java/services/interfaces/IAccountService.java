package services.interfaces;

import dto.UserAccountDTO;
import dto.UserRegistrationDTO;
import exceptions.account.AccountExistsException;
import exceptions.account.AccountNotFoundException;
import exceptions.account.AccountRegistrationException;
import exceptions.account.BankAccountException;

import java.util.List;

public interface IAccountService {
    String register(UserRegistrationDTO userRegistrationDTO) throws AccountExistsException, AccountRegistrationException;
    UserAccountDTO get(String id) throws AccountNotFoundException;
    UserAccountDTO getByCpr(String cpr) throws AccountNotFoundException;
    List<UserAccountDTO> getAll() throws BankAccountException;
    void retireAccount(String id) throws BankAccountException;
    void retireAccountByCpr(String cpr) throws BankAccountException;
}