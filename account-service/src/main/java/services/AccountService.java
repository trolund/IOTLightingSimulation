package services;

import domain.AccountObj;
import dto.AccountObjDTO;
import infrastructure.repositories.interfaces.IAccountRepository;
import org.modelmapper.ModelMapper;
import services.interfaces.IAccountService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

// All of the business logic concerning the
// Account domain should be written here.
@ApplicationScoped
public class AccountService implements IAccountService {

    @Inject
    IAccountRepository repo;

    public AccountObjDTO readAccount() {
        ModelMapper mapper = new ModelMapper();
        AccountObj exampleDto = repo.readAccount();
        return mapper.map(exampleDto, AccountObjDTO.class);
    }
}
