package net.mercury.eternal.function;

import net.mercury.eternal.syntax.Interpreter;

import java.util.List;

public interface EternalCallable {

    int arity();

    Object call(Interpreter interpreter, List<Object> args);

}
