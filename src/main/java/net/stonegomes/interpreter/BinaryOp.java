package net.stonegomes.interpreter;

public enum BinaryOp {
    Add, // +
    Sub, // -
    Mul, // *
    Div, // /
    Rem, // %
    Eq,  // ==
    Neq, // !=
    Lt,  // <
    Gt,  // >
    Lte, // <=
    Gte, // >=
    And, // &&
    Or;   // ||

    public boolean validateTwoOperands(Object lhs, Object rhs) {
        return switch (this) {
            case Add, Sub, Mul, Div -> standardTwoIntegers(lhs, rhs);
            case Eq -> standardTwoIntegers(lhs, rhs) || standardTwoStrings(lhs, rhs) || standardTwoBooleans(lhs, rhs);
            default -> true;
        };
    }

    private boolean standardTwoIntegers(Object lhs, Object rhs) {
        return lhs instanceof Integer || rhs instanceof Integer;
    }

    private boolean standardTwoStrings(Object lhs, Object rhs) {
        return lhs instanceof String && rhs instanceof String;
    }

    private boolean standardTwoBooleans(Object lhs, Object rhs) {
        return lhs instanceof Boolean && rhs instanceof Boolean;
    }

}
