package com.duelscripts;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Main application.
 */
public class MainTest {
    
    @Test
    public void testMainClassExists() {
        // Simple test to verify Main class can be instantiated
        Main main = new Main();
        assertNotNull(main);
    }
}
