package com.duelscripts;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScriptFactoryTest {
    
    @Test
    public void testCreateRandomScript() {
        CombatScript script = ScriptFactory.createScript(ScriptFactory.RANDOM);
        assertNotNull(script);
        assertTrue(script instanceof RandomScript);
        assertEquals("Random", script.getName());
    }
    
    @Test
    public void testCreateAggressiveScript() {
        CombatScript script = ScriptFactory.createScript(ScriptFactory.AGGRESSIVE);
        assertNotNull(script);
        assertTrue(script instanceof AggressiveScript);
        assertEquals("Aggressive", script.getName());
    }
    
    @Test
    public void testCreateDefensiveScript() {
        CombatScript script = ScriptFactory.createScript(ScriptFactory.DEFENSIVE);
        assertNotNull(script);
        assertTrue(script instanceof DefensiveScript);
        assertEquals("Defensive", script.getName());
    }
    
    @Test
    public void testCreateBalancedScript() {
        CombatScript script = ScriptFactory.createScript(ScriptFactory.BALANCED);
        assertNotNull(script);
        assertTrue(script instanceof BalancedScript);
        assertEquals("Balanced", script.getName());
    }
    
    @Test
    public void testCreateAllAvailableScripts() {
        for (String scriptType : ScriptFactory.getAvailableScripts()) {
            CombatScript script = ScriptFactory.createScript(scriptType);
            assertNotNull(script, "Failed to create script: " + scriptType);
            assertNotNull(script.getName());
            assertNotNull(script.getDescription());
        }
    }
    
    @Test
    public void testCreateScriptCaseInsensitive() {
        CombatScript script1 = ScriptFactory.createScript("AGGRESSIVE");
        CombatScript script2 = ScriptFactory.createScript("aggressive");
        CombatScript script3 = ScriptFactory.createScript("Aggressive");
        
        assertEquals(script1.getClass(), script2.getClass());
        assertEquals(script2.getClass(), script3.getClass());
    }
    
    @Test
    public void testCreateInvalidScript() {
        assertThrows(IllegalArgumentException.class, () -> {
            ScriptFactory.createScript("invalid_script");
        });
    }
    
    @Test
    public void testGetDefaultScript() {
        CombatScript defaultScript = ScriptFactory.getDefaultScript();
        assertNotNull(defaultScript);
        assertTrue(defaultScript instanceof RandomScript);
    }
    
    @Test
    public void testIsScriptAvailable() {
        assertTrue(ScriptFactory.isScriptAvailable(ScriptFactory.RANDOM));
        assertTrue(ScriptFactory.isScriptAvailable(ScriptFactory.AGGRESSIVE));
        assertTrue(ScriptFactory.isScriptAvailable("DEFENSIVE")); // Test case insensitive
        assertFalse(ScriptFactory.isScriptAvailable("nonexistent"));
    }
    
    @Test
    public void testGetAvailableScripts() {
        var availableScripts = ScriptFactory.getAvailableScripts();
        assertNotNull(availableScripts);
        assertFalse(availableScripts.isEmpty());
        assertTrue(availableScripts.contains(ScriptFactory.RANDOM));
        assertTrue(availableScripts.contains(ScriptFactory.AGGRESSIVE));
        assertTrue(availableScripts.contains(ScriptFactory.DEFENSIVE));
        assertTrue(availableScripts.contains(ScriptFactory.BALANCED));
    }
}
