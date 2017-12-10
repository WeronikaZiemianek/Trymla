package checkers.server.fields;

import checkers.server.Checker;
import checkers.server.Field;
import org.junit.Test;

import static org.junit.Assert.*;

public class FieldTest {

    @Test
    public void createField() {
        Field field = new Field(0);
        assertEquals(Checker.RED, field.getType());
        assertEquals(Checker.GREEN, field.getOccupiedBy());
    }

    @Test
    public void createFieldWithChecker() {
        Field field = new Field(Checker.RED);
        assertEquals(Checker.GREEN, field.getOccupiedBy());
    }

}