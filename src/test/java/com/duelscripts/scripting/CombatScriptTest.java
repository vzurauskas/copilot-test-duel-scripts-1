package com.duelscripts.scripting;

import com.duelscripts.core.Fighter;
import com.duelscripts.core.Weapon;
import com.duelscripts.core.Action;
import com.duelscripts.core.BodyPart;
import com.duelscripts.scripting.scripts.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class CombatScriptTest {
    
    private Fighter createTestFighter(String name, int hp) {
        Weapon weapon = new Weapon("Test Weapon", 10, 0.1);
        return new Fighter(name, hp, 5, weapon);
    }
    
    private FighterContext createTestContext(Fighter self, Fighter opponent, int turn) {
        return new FighterContext(self, opponent, turn, new ArrayList<>());
    }
    
    @Test
    public void testRandomScriptBehavior() {
        RandomScript script = new RandomScript();
        Fighter fighter1 = createTestFighter("Alice", 50);
        Fighter fighter2 = createTestFighter("Bob", 50);
        FighterContext context = createTestContext(fighter1, fighter2, 1);
        
        Action action = script.getNextAction(context);
        assertNotNull(action);
        assertNotNull(action.getStrikeTarget());
        assertNotNull(action.getParryTarget());
        
        assertEquals("Random", script.getName());
        assertNotNull(script.getDescription());
    }
    
    @Test
    public void testAggressiveScriptPreferences() {
        AggressiveScript script = new AggressiveScript();
        Fighter fighter1 = createTestFighter("Alice", 50);
        Fighter fighter2 = createTestFighter("Bob", 50);
        FighterContext context = createTestContext(fighter1, fighter2, 1);
        
        Action action = script.getNextAction(context);
        assertNotNull(action);
        
        // Aggressive script should prefer HEAD strikes (high damage)
        assertEquals(BodyPart.HEAD, action.getStrikeTarget());
        
        assertEquals("Aggressive", script.getName());
        assertTrue(script.getDescription().toLowerCase().contains("aggressive"));
    }
    
    @Test
    public void testDefensiveScriptBehavior() {
        DefensiveScript script = new DefensiveScript();
        Fighter fighter1 = createTestFighter("Alice", 50);
        Fighter fighter2 = createTestFighter("Bob", 50);
        
        // Test normal health behavior
        FighterContext context = createTestContext(fighter1, fighter2, 1);
        Action action = script.getNextAction(context);
        assertNotNull(action);
        assertEquals("Defensive", script.getName());
        
        // Test low health behavior (should be more defensive)
        fighter1.takeDamage(40); // Reduce to 20% health
        FighterContext lowHealthContext = createTestContext(fighter1, fighter2, 2);
        Action lowHealthAction = script.getNextAction(lowHealthContext);
        assertNotNull(lowHealthAction);
        
        // Should be more defensive when health is low
        // The exact behavior depends on implementation, but it should still be valid
        assertTrue(lowHealthAction.getStrikeTarget() != null);
        assertTrue(lowHealthAction.getParryTarget() != null);
    }
    
    @Test
    public void testBalancedScriptAdaptability() {
        BalancedScript script = new BalancedScript();
        Fighter fighter1 = createTestFighter("Alice", 50);
        Fighter fighter2 = createTestFighter("Bob", 50);
        
        // Test first turn
        FighterContext context1 = createTestContext(fighter1, fighter2, 1);
        Action action1 = script.getNextAction(context1);
        assertNotNull(action1);
        
        // Test later turn (should potentially behave differently)
        FighterContext context3 = createTestContext(fighter1, fighter2, 3);
        Action action3 = script.getNextAction(context3);
        assertNotNull(action3);
        
        assertEquals("Balanced", script.getName());
        assertTrue(script.getDescription().toLowerCase().contains("balanced"));
    }
    
    @Test
    public void testBerserkerScriptRageMode() {
        BerserkerScript script = new BerserkerScript();
        Fighter fighter1 = createTestFighter("Alice", 50);
        Fighter fighter2 = createTestFighter("Bob", 50);
        
        // Test high health (should be normal)
        FighterContext highHealthContext = createTestContext(fighter1, fighter2, 1);
        Action highHealthAction = script.getNextAction(highHealthContext);
        assertNotNull(highHealthAction);
        
        // Test low health (should enter berserker mode)
        fighter1.takeDamage(45); // Reduce to 10% health
        FighterContext lowHealthContext = createTestContext(fighter1, fighter2, 2);
        Action lowHealthAction = script.getNextAction(lowHealthContext);
        assertNotNull(lowHealthAction);
        
        // Berserker mode should prefer aggressive strikes when health is low
        assertEquals(BodyPart.HEAD, lowHealthAction.getStrikeTarget());
        
        assertEquals("Berserker", script.getName());
        assertTrue(script.getDescription().toLowerCase().contains("aggressive"));
    }
    
    @Test
    public void testAllScriptsProduceValidActions() {
        Fighter fighter1 = createTestFighter("Alice", 50);
        Fighter fighter2 = createTestFighter("Bob", 50);
        FighterContext context = createTestContext(fighter1, fighter2, 1);
        
        // Test all script types from factory
        for (String scriptType : ScriptFactory.getAvailableScripts()) {
            CombatScript script = ScriptFactory.createScript(scriptType);
            Action action = script.getNextAction(context);
            
            assertNotNull(action, "Script " + scriptType + " returned null action");
            assertNotNull(action.getStrikeTarget(), "Script " + scriptType + " returned null strike target");
            assertNotNull(action.getParryTarget(), "Script " + scriptType + " returned null parry target");
            assertNotNull(script.getName(), "Script " + scriptType + " returned null name");
            assertNotNull(script.getDescription(), "Script " + scriptType + " returned null description");
        }
    }
}
