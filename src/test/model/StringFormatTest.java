package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test class for StringFormat
public class StringFormatTest {

    @Test
    public void testValues() {
        assertEquals("\t", StringFormat.SEPARATOR.getFormat());
        assertEquals("\n", StringFormat.LINE_BREAK.getFormat());
        assertEquals("... with ", StringFormat.CUTOFF.getFormat());
        assertEquals("[", StringFormat.LEFT_BRACKET.getFormat());
        assertEquals("]", StringFormat.RIGHT_BRACKET.getFormat());
    }
}
