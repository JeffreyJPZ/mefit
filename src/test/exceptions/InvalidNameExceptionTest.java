package exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

// Test class for InvalidNameException
public class InvalidNameExceptionTest {

    @Test
    public void testInvalidNameException() {
        InvalidNameException e = new InvalidNameException();
        assertNull(null, e.getMessage());
    }
}
