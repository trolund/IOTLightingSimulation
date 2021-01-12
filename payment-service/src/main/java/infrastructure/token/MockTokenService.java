package infrastructure.token;

import infrastructure.token.interfaces.ITokenService;

public class TokenService implements ITokenService {

    public String getToken(){
        return "Dette er en token";
    }

    public boolean validateToken(){
        return true;
    }

}
