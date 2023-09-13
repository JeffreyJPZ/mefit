package model.formatting;

import org.junit.jupiter.api.Test;

import static model.formatting.StringFormat.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Test class for StringFormat
public class StringFormatTest {

    @Test
    public void testValues() {
        assertEquals("\t", SEPARATOR.getFormat());
        assertEquals("\n", LINE_BREAK.getFormat());
        assertEquals("... with ", CUTOFF.getFormat());
        assertEquals("[", LEFT_BRACKET.getFormat());
        assertEquals("]", RIGHT_BRACKET.getFormat());
    }
}
