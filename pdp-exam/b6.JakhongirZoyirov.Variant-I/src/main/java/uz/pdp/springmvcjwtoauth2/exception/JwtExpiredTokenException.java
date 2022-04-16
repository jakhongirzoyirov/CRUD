package uz.pdp.springmvcjwtoauth2.exception;

public class JwtExpiredTokenException extends RuntimeException{

    public JwtExpiredTokenException(String message) {
        super(message);
    }
}
