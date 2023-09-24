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
            default -> true;
        };
    }

    private boolean standardTwoIntegers(Object lhs, Object rhs) {
        if (!(lhs instanceof Integer) && !(rhs instanceof Integer)) {
            throw new RuntimeException("Expression must use two integer arguments");
        }

        return true;
    }

}
