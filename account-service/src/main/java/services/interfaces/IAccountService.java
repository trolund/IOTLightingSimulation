package services.interfaces;

import dto.UserAccountDTO;
import dto.UserRegistrationDTO;
import exceptions.account.AccountException;
import exceptions.account.RemoteAccountDoesNotExistException;
import model.UserAccount;

import java.util.List;

public interface IAccountService {
    String add(UserRegistrationDTO account) throws Exception;
    UserAccountDTO getById(String id) throws AccountException;
    UserAccountDTO getByCpr(String cpr) throws AccountException;
    List<UserAccountDTO> getAll();
    void retireAccountByCpr(String cpr) throws RemoteAccountDoesNotExistException;
    void retireAccount(String id) throws RemoteAccountDoesNotExistException;
}