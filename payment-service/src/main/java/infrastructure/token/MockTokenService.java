package infrastructure.token;

import infrastructure.token.interfaces.ITokenService;

/**
 * @author Troels (s161791)
 * Mock TokenService for testing.
 */

public class MockTokenService implements ITokenService {

    public String getToken(){
        return "Dette er en token";
    }

    public boolean validateToken(){
        return true;
    }

}
