package services.interfaces;

import dto.UserDTO;
import exceptions.DuplicateException;
import exceptions.EmptyCprException;
import exceptions.EmptyNameException;
import exceptions.MissingIdException;


public interface IAccountService {
    void createUser(String firstName, String lastName, String cpr) throws DuplicateException, EmptyCprException, EmptyNameException;
    void disableAccount(String id) throws MissingIdException;
}
