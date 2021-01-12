package infrastructure.repositories.interfaces;

import domain.CustomerTokens;
import exceptions.CustomerNotFoundException;
import exceptions.TokenNotFoundException;

import java.util.List;

// Specific to "Example" entity.
// These methods should be specific to the given entity. For example,
// getByCpr, whatever specific methods.
public interface ICustomerTokensRepository extends IRepository<CustomerTokens> {
    CustomerTokens get(String id) throws CustomerNotFoundException;

    CustomerTokens getCustomerWithTokenId(String tokenId) throws TokenNotFoundException;

    void delete(String id) throws CustomerNotFoundException;

    List<CustomerTokens> getAll();

    void update(CustomerTokens obj) throws CustomerNotFoundException;
}