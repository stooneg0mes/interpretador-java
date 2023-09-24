package net.stonegomes.interpreter;

import java.util.HashMap;

import static net.stonegomes.interpreter.Expression.*;

public class JavaInterpreter {

    public static void main(String[] args) {
        var expr = new Binary(new Int(10), BinaryOp.Sub, new Binary(new Int(5), BinaryOp.Add, new Int(30)));
        System.out.println(expr.eval(new HashMap<>()));
    }

}