package net.stonegomes.interpreter;

import java.util.HashMap;
import java.util.Map;

// 1 - Int
// left hand side
// right hand side
//
// 1 + 1 - Add
// let x = 10; x + 20
// Add(Expr, Add(Expr, Expr))
//
// let f = fn () => "a";
// let _ = print (f());
// print (let x = 10; x)
//
// Let( "x",
//      Int(10),
//      Add(Var("x"), Int(20)) )
//
public sealed interface Expression {

    record Int(int value) implements Expression {

        @Override
        public Object eval(Map<String, Object> environment) {
            return value;
        }

    }

    record Str(String value) implements Expression {

        @Override
        public Object eval(Map<String, Object> environment) {
            return value;
        }
    }

    // bi - binário, ou seja recebe 2
    // lhs - left hand side, o lado esquerdo da operação
    // rhs - right hand side, o lado direito da operação
    record Binary(Expression lhs, BinaryOp op, Expression rhs) implements Expression {

        @Override
        public Object eval(Map<String, Object> environment) {
            var newLhs = lhs.eval(environment);
            var newRhs = rhs.eval(environment);

            switch (op) {
                case Add -> {
                    if (!op.validateTwoOperands(newLhs, newRhs)) {
                        return null;
                    }

                    return (int) newLhs + (int) newRhs;
                }
                case Sub -> {
                    if (!op.validateTwoOperands(newLhs, newRhs)) {
                        return null;
                    }

                    return (int) newLhs - (int) newRhs;
                }
                case Mul -> {
                    if (!op.validateTwoOperands(newLhs, newRhs)) {
                        return null;
                    }

                    return (int) newLhs * (int) newRhs;
                }
                case Div -> {
                    if (!op.validateTwoOperands(newLhs, newRhs)) {
                        return null;
                    }

                    return (int) newLhs / (int) newRhs;
                }
                default -> throw new RuntimeException("Unsupported type");
            }
        }

    }

    // let x = 10; x
    record Let(String name, Expression value, Expression in) implements Expression {

        @Override
        public Object eval(Map<String, Object> environment) {
            HashMap<String, Object> clonedEnvironment = new HashMap<>(environment);;
            clonedEnvironment.put(name, value.eval(environment));

            return in.eval(clonedEnvironment);
        }

    }

    record Print(Expression expression) implements Expression {

        @Override
        public Object eval(Map<String, Object> environment) {
            Object object = expression.eval(environment);
            System.out.println(object);

            return object;
        }

    }

    default Object eval(Map<String, Object> environment) {
        throw new RuntimeException("Not implemented expression");
    }

}

// ADT - Algebraic Data Types
//
//sealed interface LinkedList<T> {
//    record Cons<T>(T head, LinkedList<T> rest) implements LinkedList<T> {}
//    record Nil<T>() implements LinkedList<T> {}
//
//    default void cu() {
//        // Cons(10, Cons(20, Nil))
//        new Cons<Integer>(10, new Cons<>(20, new Nil<>()));
//    }
//}