package exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

// Test class for InvalidPositionException
public class InvalidPositionExceptionTest {

    @Test
    public void testInvalidPositionException() {
        InvalidPositionException e = new InvalidPositionException();
        assertNull(null, e.getMessage());
    }
}
