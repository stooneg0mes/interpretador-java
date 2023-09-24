package net.stonegomes.interpreter.term;

import java.util.HashMap;
import java.util.Map;

// lhs - left hand side of the operation
// rhs - right hand side of the operation

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
            HashMap<String, Object> clonedEnvironment = new HashMap<>(environment);
            ;
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