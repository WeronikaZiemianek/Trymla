package checkers.core.Fields;

import checkers.core.Checker;

public class RedField extends Field {

    @Override
    void init() {
        occupiedBy = Checker.RED;
    }

    @Override
    Checker getTypeOfField() {
        return Checker.RED;
    }
}
