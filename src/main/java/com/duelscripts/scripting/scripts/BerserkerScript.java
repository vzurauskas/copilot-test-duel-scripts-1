package com.duelscripts.scripting.scripts;

import com.duelscripts.core.Action;
import com.duelscripts.core.BodyPart;
import com.duelscripts.scripting.CombatScript;
import com.duelscripts.scripting.FighterContext;

/**
 * A berserker combat script that becomes more aggressive as health decreases.
 */
public class BerserkerScript implements CombatScript {
    
    @Override
    public Action getNextAction(FighterContext context) {
        double healthPercentage = context.getSelfHealthPercentage();
        
        // Default moderate approach
        BodyPart strikeTarget = BodyPart.TORSO;
        BodyPart parryTarget = BodyPart.TORSO;
        
        // Berserker rage: more aggressive as health decreases
        if (healthPercentage > 0.75) {
            // High health - fight normally
            strikeTarget = BodyPart.TORSO;
            parryTarget = BodyPart.HEAD;
        } else if (healthPercentage > 0.5) {
            // Medium health - slightly more aggressive
            strikeTarget = BodyPart.HEAD;
            parryTarget = BodyPart.TORSO;
        } else if (healthPercentage > 0.25) {
            // Low health - very aggressive
            strikeTarget = BodyPart.HEAD;
            parryTarget = BodyPart.LEGS; // Less defensive parrying
        } else {
            // Critical health - berserk mode - always go for maximum damage
            strikeTarget = BodyPart.HEAD;
            // Alternate between minimal defense and all-out attack
            if (context.getCurrentTurn() % 2 == 0) {
                parryTarget = BodyPart.LEGS; // Minimal defense
            } else {
                parryTarget = BodyPart.HEAD; // Occasional protection
            }
        }
        
        return new Action(strikeTarget, parryTarget);
    }
    
    @Override
    public String getName() {
        return "Berserker";
    }
    
    @Override
    public String getDescription() {
        return "Becomes increasingly aggressive and reckless as health decreases. " +
               "Fights normally when healthy, but enters berserk mode when critically wounded.";
    }
}
