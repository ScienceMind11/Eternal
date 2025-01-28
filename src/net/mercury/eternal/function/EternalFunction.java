package net.mercury.eternal.function;

import net.mercury.eternal.env.Environment;
import net.mercury.eternal.syntax.Interpreter;
import net.mercury.eternal.syntax.Stmt;

import java.util.List;

public class EternalFunction implements EternalCallable {

    private final Stmt.Function declaration;

    public EternalFunction(Stmt.Function declaration) {
        this.declaration = declaration;
    }

    @Override
    public int arity() {
        return declaration.params.size();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> args) {
        Environment environment = new Environment(interpreter.globals);
        for(int i = 0; i < declaration.params.size(); i++) {
            environment.define(declaration.params.get(i).lexeme, args.get(i));
        }

        interpreter.executeBlock(declaration.body, environment);
        return null;
    }

    @Override
    public String toString() {
        return "<fn " + declaration.name.lexeme + ">";
    }

}
