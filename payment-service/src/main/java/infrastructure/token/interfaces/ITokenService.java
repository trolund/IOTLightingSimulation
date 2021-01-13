package infrastructure.token.interfaces;

public interface ITokenService {
    String getToken();
    boolean validateToken();
}
