package infrastructure.token;

import infrastructure.token.interfaces.ITokenService;

/**
 * @author Troels (s161791)
 * service for communicating with TokenService.
 */
public class TokenService implements ITokenService {

    private String baseUrl;

    public TokenService() {
        this.baseUrl = System.getenv("TOKENSERVICEPATH");
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public String getToken() {
       return null;
    }

    @Override
    public boolean validateToken() {
        return true;
    }

}
