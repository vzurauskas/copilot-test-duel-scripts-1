package com.duelscripts;

/**
 * An aggressive combat script that always strikes and targets high-damage body parts.
 */
public class AggressiveScript implements CombatScript {
    
    @Override
    public Action getNextAction(FighterContext context) {
        // Always strike, preferring HEAD (highest damage)
        BodyPart strikeTarget = BodyPart.HEAD;
        
        // Simple parry strategy - default to protecting head
        BodyPart parryTarget = BodyPart.HEAD;
        
        // If we have battle history, use simple reactive logic
        if (!context.isFirstTurn()) {
            // Vary our strikes occasionally to be less predictable
            if (context.getCurrentTurn() % 3 == 0) {
                strikeTarget = BodyPart.TORSO; // Occasional variety
            }
            
            // Alternate parry targets to be less predictable
            if (context.getCurrentTurn() % 2 == 0) {
                parryTarget = BodyPart.TORSO;
            }
        }
        
        return new Action(strikeTarget, parryTarget);
    }
    
    @Override
    public String getName() {
        return "Aggressive";
    }
    
    @Override
    public String getDescription() {
        return "Always strikes aggressively, preferring high-damage targets like the head. " +
               "Uses basic reactive parrying based on opponent's previous actions.";
    }
}
