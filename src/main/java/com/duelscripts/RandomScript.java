package com.duelscripts;

/**
 * A combat script that makes random decisions.
 * This script migrates the current Fighter behavior to the new script system.
 */
public class RandomScript implements CombatScript {
    
    @Override
    public Action getNextAction(FighterContext context) {
        // Use the existing random logic from Fighter.getAction()
        // This maintains backward compatibility
        return context.getSelf().generateRandomAction();
    }
    
    @Override
    public String getName() {
        return "Random";
    }
    
    @Override
    public String getDescription() {
        return "Makes random combat decisions with no particular strategy. " +
               "Chooses strike targets and parry positions randomly.";
    }
}
