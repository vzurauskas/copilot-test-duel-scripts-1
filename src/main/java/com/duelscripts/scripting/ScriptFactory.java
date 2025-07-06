package com.duelscripts.scripting;

import com.duelscripts.scripting.scripts.*;

import java.util.Arrays;
import java.util.List;

/**
 * Factory for creating and managing combat scripts.
 * Provides centralized access to all available script types.
 */
public class ScriptFactory {
    
    /**
     * Available script types that can be created.
     */
    public static final String RANDOM = "random";
    public static final String AGGRESSIVE = "aggressive";
    public static final String DEFENSIVE = "defensive";
    public static final String BALANCED = "balanced";
    public static final String ADAPTIVE = "adaptive";
    public static final String COUNTER = "counter";
    public static final String BERSERKER = "berserker";
    public static final String TACTICAL = "tactical";
    
    /**
     * Creates a combat script of the specified type.
     * @param scriptType The type of script to create (use constants from this class)
     * @return A new instance of the requested script type
     * @throws IllegalArgumentException if the script type is not recognized
     */
    public static CombatScript createScript(String scriptType) {
        switch (scriptType.toLowerCase()) {
            case RANDOM:
                return new RandomScript();
            case AGGRESSIVE:
                return new AggressiveScript();
            case DEFENSIVE:
                return new DefensiveScript();
            case BALANCED:
                return new BalancedScript();
            case ADAPTIVE:
                return new AdaptiveScript();
            case COUNTER:
                return new CounterScript();
            case BERSERKER:
                return new BerserkerScript();
            case TACTICAL:
                return new TacticalScript();
            default:
                throw new IllegalArgumentException("Unknown script type: " + scriptType);
        }
    }
    
    /**
     * Gets a list of all available script types.
     * @return A list of script type names that can be passed to createScript()
     */
    public static List<String> getAvailableScripts() {
        return Arrays.asList(
            RANDOM, AGGRESSIVE, DEFENSIVE, BALANCED,
            ADAPTIVE, COUNTER, BERSERKER, TACTICAL
        );
    }
    
    /**
     * Gets the default script for fighters.
     * @return A default combat script (currently RandomScript)
     */
    public static CombatScript getDefaultScript() {
        return new RandomScript();
    }
    
    /**
     * Checks if a script type is available.
     * @param scriptType The script type to check
     * @return true if the script type is available, false otherwise
     */
    public static boolean isScriptAvailable(String scriptType) {
        return getAvailableScripts().contains(scriptType.toLowerCase());
    }
}
