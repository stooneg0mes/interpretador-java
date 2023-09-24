package net.stonegomes.interpreter;

import java.util.HashMap;

import static net.stonegomes.interpreter.Term.*;

public class JavaInterpreter {

    public static void main(String[] args) {
        simpleBinaryTerm();
        complexBinaryTerm();

        simpleIfTerm();
        complexIfTerm();
    }

    private static void simpleBinaryTerm() {
        // Simple binary run

        // print(x = 200 + 500))
        // x = 200 + 500
        // x = 700
        var binaryTerm = new Print(
            new Binary(Int.of(200), BinaryOp.Add, Int.of(500))
        );

        // Result: 700
        binaryTerm.eval(new HashMap<>());
    }

    private static void complexBinaryTerm() {
        // Complex binary run

        // print(x = 200 + ; (y = 500 / 2))
        // x = 200 + y
        // y = 500 / 2
        // y = 250
        // x = 450
        var binaryTerm = new Print(
            new Binary(Int.of(200), BinaryOp.Add,
                new Binary(Int.of(500), BinaryOp.Div, Int.of(2)))
        );

        // Result: 450
        binaryTerm.eval(new HashMap<>());
    }

    private static void simpleIfTerm() {
        // Simple if term run

        // if (true) print("it is true") else print("it is not true")
        var ifTerm = new If(Bool.of(true), // condition
            new Print(Str.of("it is true")), // yes
            new Print(Str.of("it is not true")) // no
        );

        // Result: it is true
        ifTerm.eval(new HashMap<>());
    }

    private static void complexIfTerm() {
        // Complex if term run

        var pixNadaAinda = "pix nada ainda";
        var pixNada = "pix nada";

        // if ("pix nada ainda" == "pix nada") print(x = 10 + 10) else print(x = 20 + 20)
        var condition = new Binary(Str.of(pixNadaAinda), BinaryOp.Eq, Str.of(pixNada));
        var ifTerm = new If(condition,
            new Print(new Binary(Int.of(10), BinaryOp.Add, Int.of(10))),
            new Print(new Binary(Int.of(20), BinaryOp.Add, Int.of(20)))
        );

        // Result: 40
        ifTerm.eval(new HashMap<>());
    }

}