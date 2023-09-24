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
public sealed interface Term {

    record Int(int value) implements Term {

        public static Int of(int value) {
            return new Int(value);
        }

        @Override
        public Object eval(Map<String, Object> environment) {
            return value;
        }

    }

    record Str(String value) implements Term {

        public static Str of(String value) {
            return new Str(value);
        }

        @Override
        public Object eval(Map<String, Object> environment) {
            return value;
        }
    }

    record Bool(boolean value) implements Term {

        public static Bool of(boolean value) {
            return new Bool(value);
        }

        @Override
        public Object eval(Map<String, Object> environment) {
            return value;
        }

    }

    // bi - binário, ou seja recebe 2
    // lhs - left hand side, o lado esquerdo da operação
    // rhs - right hand side, o lado direito da operação
record Binary(Term lhs, BinaryOp op, Term rhs) implements Term {

        @Override
        public Object eval(Map<String, Object> environment) {
            var lhsValue = lhs.eval(environment);
            var rhsValue = rhs.eval(environment);

            if (!op.validateTwoOperands(lhsValue, rhsValue)) {
                return null;
            }

            switch (op) {
                case Add -> {
                    return (int) lhsValue + (int) rhsValue;
                }
                case Sub -> {
                    return (int) lhsValue - (int) rhsValue;
                }
                case Mul -> {
                    return (int) lhsValue * (int) rhsValue;
                }
                case Div -> {
                    return (int) lhsValue / (int) rhsValue;
                }
                case Eq -> {
//                    if (lhsValue instanceof String lhsString && rhsValue instanceof String rhsString) {
//                        return lhsString.equals(rhsString);
//                    } else if (lhsValue instanceof Integer lhsInt && rhsValue instanceof Integer rhsInt) {
//                        return lhsInt.equals(rhsInt);
//                    }

                    return lhs.equals(rhs);
                }
                default -> throw new RuntimeException("Unsupported type");
            }
        }

    }

    // let x = 10; x
    record Let(String name, Term value, Term in) implements Term {

        @Override
        public Object eval(Map<String, Object> environment) {
            HashMap<String, Object> clonedEnvironment = new HashMap<>(environment);;
            clonedEnvironment.put(name, value.eval(environment));

            return in.eval(clonedEnvironment);
        }

    }

    record Print(Term term) implements Term {

        @Override
        public Object eval(Map<String, Object> environment) {
            Object object = term.eval(environment);
            System.out.println(object);

            return object;
        }

    }

    record If(Term condition, Term then, Term otherwise) implements Term {

        @Override
        public Object eval(Map<String, Object> environment) {
            Object conditionResult = condition.eval(environment);
            if (!(conditionResult instanceof Boolean bool)) {
                throw new RuntimeException("If term not conditioned with bool");
            }

            if (bool) {
                return then.eval(environment);
            } else {
                return otherwise.eval(environment);
            }
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