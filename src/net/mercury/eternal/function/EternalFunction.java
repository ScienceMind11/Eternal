package net.mercury.eternal.function;

import net.mercury.eternal.env.Environment;
import net.mercury.eternal.error.Return;
import net.mercury.eternal.syntax.Interpreter;
import net.mercury.eternal.syntax.Stmt;

import java.util.List;

public class EternalFunction implements EternalCallable {

    private final Stmt.Function declaration;
    private final Environment closure;

    public EternalFunction(Stmt.Function declaration, Environment closure) {
        this.closure = closure;
        this.declaration = declaration;
    }

    @Override
    public int arity() {
        return declaration.params.size();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> args) {
        Environment environment = new Environment(closure);
        for(int i = 0; i < declaration.params.size(); i++) {
            environment.define(declaration.params.get(i).lexeme, args.get(i));
        }

        try {
            interpreter.executeBlock(declaration.body, environment);
        } catch(Return returnValue) {
            return returnValue.value;
        }

        return null;
    }

    @Override
    public String toString() {
        return "<fn " + declaration.name.lexeme + ">";
    }

}
