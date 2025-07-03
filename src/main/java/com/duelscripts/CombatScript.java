package com.duelscripts;

/**
 * Interface for combat scripts that define fighter behavior strategies.
 * Scripts implement the Strategy pattern to allow flexible combat tactics.
 */
public interface CombatScript {
    /**
     * Determines the next action for a fighter based on the current context.
     * @param context The current battle context containing all relevant information
     * @return The action the fighter should take this turn
     */
    Action getNextAction(FighterContext context);
    
    /**
     * Gets the name of this combat script.
     * @return A short, descriptive name for the script
     */
    String getName();
    
    /**
     * Gets a description of this combat script's behavior.
     * @return A detailed description of the script's tactics and strategy
     */
    String getDescription();
}
