package exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

// Test class for InvalidInputException
public class InvalidInputExceptionTest {

    @Test
    public void testInvalidInputException() {
        InvalidInputException e = new InvalidInputException();
        assertNull(null, e.getMessage());
    }
}
