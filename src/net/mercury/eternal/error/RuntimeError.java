package net.mercury.eternal.error;

import net.mercury.eternal.token.Token;

public class RuntimeError extends RuntimeException {

    public final Token token;

    public RuntimeError(Token token, String message) {
        super(message);
        this.token = token;
    }

}
