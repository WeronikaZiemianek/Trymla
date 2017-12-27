package checkers.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Field {
    static Logger logger = LoggerFactory.getLogger(Field.class);
    private Checker occupiedBy;
    private Checker type;

    public Field(int type) {
        Checker[] values = Checker.values();
        this.type = values[type];
        if(type != 6) {
            occupiedBy = values[(type + 3) % 6];
        } else {
            occupiedBy = values[6];
        }
    }


    public Field(Checker type, Checker occupied) {
        this.type = type;
        if(occupied == null) {
            initField(type);
        } else {
            this.type = type;
            this.occupiedBy = occupied;
        }

    }

    private void initField(Checker type) {
        Checker values[] = Checker.values();
        int t = 0;
        this.type = type;
        int size = values.length;
        for(int i=0; i< size; i++) {
            if(values[i].equals(type)) {
                t=i;
            }
        }
        if(t != 6) {
            occupiedBy = values[(t + 3) % 6];
        } else {
            occupiedBy = values[6];
        }
    }

    public Checker getOccupiedBy() {
        return this.occupiedBy;
    }

    public void setOccupiedBy(Checker occupiedBy) {
        this.occupiedBy = occupiedBy;
    }

    public Checker getType() {
        return this.type;
    }
}
