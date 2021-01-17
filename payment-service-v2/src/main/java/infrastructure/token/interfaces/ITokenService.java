package infrastructure.token.interfaces;

/**
 * @author Troels (s161791)
 */
public interface ITokenService {
    String getToken();
    boolean validateToken();
}
