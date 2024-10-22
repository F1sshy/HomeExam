package main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PointSaladTest {

    @Test
    void testIllegalNumberOfPlayers() {
        String[] argsTooFew = {"1", "0"};
        String[] argsTooMany = {"7", "0"};
        String[] argsValid = {"2", "0"};

        // Test too few players
        Exception exceptionTooFew = assertThrows(IllegalArgumentException.class, () -> {
            new PointSalad(argsTooFew);
        });
        assertEquals("Invalid number of players.", exceptionTooFew.getMessage());

        // Test too many players
        Exception exceptionTooMany = assertThrows(IllegalArgumentException.class, () -> {
            new PointSalad(argsTooMany);
        });
        assertEquals("Invalid number of players.", exceptionTooMany.getMessage());

        // Test valid number of players
//        assertDoesNotThrow(() -> {
//            new PointSalad(argsValid);
//        });
    }
}