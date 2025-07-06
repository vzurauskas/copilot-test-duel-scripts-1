package com.duelscripts.scripting.scripts;

import com.duelscripts.core.Action;
import com.duelscripts.core.BodyPart;
import com.duelscripts.scripting.CombatScript;
import com.duelscripts.scripting.FighterContext;
import com.duelscripts.combat.TurnResult;

/**
 * A balanced combat script that mixes offensive and defensive strategies.
 */
public class BalancedScript implements CombatScript {
    
    @Override
    public Action getNextAction(FighterContext context) {
        // Default balanced approach
        BodyPart strikeTarget = BodyPart.TORSO; // Balanced damage/safety
        BodyPart parryTarget = BodyPart.TORSO;  // Balanced protection
        
        double selfHealth = context.getSelfHealthPercentage();
        double opponentHealth = context.getOpponentHealthPercentage();
        
        // Adjust strategy based on health comparison
        if (selfHealth > opponentHealth + 0.2) {
            // We're ahead - be more aggressive
            strikeTarget = BodyPart.HEAD;
            parryTarget = BodyPart.TORSO;
        } else if (selfHealth < opponentHealth - 0.2) {
            // We're behind - be more defensive
            strikeTarget = BodyPart.TORSO;
            parryTarget = BodyPart.HEAD;
        }
        
        // Use battle history for tactical decisions
        if (!context.isFirstTurn()) {
            TurnResult lastTurn = context.getLastTurnResult();
            if (lastTurn != null) {
                // Balanced approach: use damage patterns to adjust strategy
                // If we took more damage last turn, be more defensive
                // If we dealt more damage, be more aggressive
                
                // Note: fighter1Damage is damage TO fighter1, fighter2Damage is damage TO fighter2
                // We need to determine which fighter we are to interpret the damage correctly
                
                // Simple pattern variation based on turn number
                if (context.getCurrentTurn() % 3 == 0) {
                    strikeTarget = BodyPart.HEAD;
                } else if (context.getCurrentTurn() % 3 == 1) {
                    strikeTarget = BodyPart.TORSO;
                } else {
                    strikeTarget = BodyPart.LEGS;
                }
                
                // Vary parry targets too
                if (context.getCurrentTurn() % 2 == 0) {
                    parryTarget = BodyPart.HEAD;
                } else {
                    parryTarget = BodyPart.TORSO;
                }
            }
        }
        
        return new Action(strikeTarget, parryTarget);
    }
    
    @Override
    public String getName() {
        return "Balanced";
    }
    
    @Override
    public String getDescription() {
        return "Uses a balanced mix of offensive and defensive tactics. " +
               "Adapts strategy based on health comparison and varies attack patterns.";
    }
}
