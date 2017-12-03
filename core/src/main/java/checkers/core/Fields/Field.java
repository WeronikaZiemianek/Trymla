package checkers.core.Fields;

import checkers.core.Checker;

public class Field {
    private Checker occupiedBy;

    Field() {
        init();
    }

    private void init() {
        occupiedBy = Checker.EMPTY;
    }

    Checker getOccupiedBy() {
        return occupiedBy;
    }

    void setOccupiedBy(Checker occupiedBy) {
        this.occupiedBy = occupiedBy;
    }

    Checker getTypeOfField() {
        return Checker.EMPTY;
    }
}
