package com.duelscripts.scripting.scripts;

import com.duelscripts.core.Action;
import com.duelscripts.core.BodyPart;
import com.duelscripts.scripting.CombatScript;
import com.duelscripts.scripting.FighterContext;

/**
 * A defensive combat script that prioritizes parrying and strikes conservatively.
 */
public class DefensiveScript implements CombatScript {
    
    @Override
    public Action getNextAction(FighterContext context) {
        // Default to conservative striking and defensive parrying
        BodyPart strikeTarget = BodyPart.TORSO; // Safer, moderate damage
        BodyPart parryTarget = BodyPart.HEAD;   // Protect against high damage
        
        // Increase defensiveness when health is low
        double healthPercentage = context.getSelfHealthPercentage();
        if (healthPercentage < 0.3) {
            // Very low health - be extra defensive
            parryTarget = BodyPart.HEAD; // Always protect head when low
            strikeTarget = BodyPart.TORSO; // Safe strikes only
        }
        
        // Only strike aggressively if we have significant health advantage
        double opponentHealthPercentage = context.getOpponentHealthPercentage();
        if (healthPercentage > 0.7 && opponentHealthPercentage < 0.5) {
            strikeTarget = BodyPart.HEAD; // Go for the kill when safe
        }
        
        // Use turn-based defensive patterns
        if (!context.isFirstTurn()) {
            // Rotate parry targets to cover all areas
            int turn = context.getCurrentTurn();
            if (turn % 3 == 0) {
                parryTarget = BodyPart.HEAD;
            } else if (turn % 3 == 1) {
                parryTarget = BodyPart.TORSO;
            } else {
                parryTarget = BodyPart.LEGS;
            }
        }
        
        return new Action(strikeTarget, parryTarget);
    }
    
    @Override
    public String getName() {
        return "Defensive";
    }
    
    @Override
    public String getDescription() {
        return "Prioritizes defense and survival, striking conservatively. " +
               "Becomes more defensive when health is low and more aggressive when ahead.";
    }
}
