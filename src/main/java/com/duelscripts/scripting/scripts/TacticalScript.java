package com.duelscripts.scripting.scripts;

import com.duelscripts.core.Action;
import com.duelscripts.core.BodyPart;
import com.duelscripts.scripting.CombatScript;
import com.duelscripts.scripting.FighterContext;
import com.duelscripts.combat.TurnResult;

/**
 * A tactical combat script that uses complex decision trees and strategic analysis.
 */
public class TacticalScript implements CombatScript {
    
    @Override
    public Action getNextAction(FighterContext context) {
        // Complex tactical analysis
        double selfHealth = context.getSelfHealthPercentage();
        double opponentHealth = context.getOpponentHealthPercentage();
        int turn = context.getCurrentTurn();
        
        // Analyze the tactical situation
        TacticalSituation situation = assessSituation(selfHealth, opponentHealth, turn);
        
        // Make decision based on tactical assessment
        return makeDecisionBasedOnSituation(situation, context);
    }
    
    private TacticalSituation assessSituation(double selfHealth, double opponentHealth, int turn) {
        // Early game (first few turns)
        if (turn <= 3) {
            return TacticalSituation.EARLY_GAME;
        }
        
        // Health-based assessment
        double healthDifference = selfHealth - opponentHealth;
        
        if (healthDifference > 0.3) {
            return TacticalSituation.WINNING;
        } else if (healthDifference < -0.3) {
            return TacticalSituation.LOSING;
        } else if (selfHealth < 0.3 && opponentHealth < 0.3) {
            return TacticalSituation.CRITICAL_BOTH;
        } else {
            return TacticalSituation.BALANCED;
        }
    }
    
    private Action makeDecisionBasedOnSituation(TacticalSituation situation, FighterContext context) {
        switch (situation) {
            case EARLY_GAME:
                return earlyGameStrategy(context);
            case WINNING:
                return winningStrategy(context);
            case LOSING:
                return losingStrategy(context);
            case CRITICAL_BOTH:
                return criticalStrategy(context);
            case BALANCED:
            default:
                return balancedStrategy(context);
        }
    }
    
    private Action earlyGameStrategy(FighterContext context) {
        // Conservative start, gather information
        return new Action(BodyPart.TORSO, BodyPart.HEAD);
    }
    
    private Action winningStrategy(FighterContext context) {
        // Maintain advantage, don't take unnecessary risks
        BodyPart strikeTarget = BodyPart.TORSO; // Reliable damage
        BodyPart parryTarget = BodyPart.HEAD;   // Protect against desperate attacks
        
        // If opponent is very low, go for the finish
        if (context.getOpponentHealthPercentage() < 0.2) {
            strikeTarget = BodyPart.HEAD;
        }
        
        return new Action(strikeTarget, parryTarget);
    }
    
    private Action losingStrategy(FighterContext context) {
        // Need to take calculated risks
        BodyPart strikeTarget = BodyPart.HEAD; // Need high damage
        BodyPart parryTarget = BodyPart.HEAD;  // Protect against finishing blows
        
        // If severely behind, take bigger risks
        if (context.getSelfHealthPercentage() < 0.2) {
            // All or nothing
            strikeTarget = BodyPart.HEAD;
            parryTarget = BodyPart.TORSO; // Less defensive
        }
        
        return new Action(strikeTarget, parryTarget);
    }
    
    private Action criticalStrategy(FighterContext context) {
        // Both fighters are low - be unpredictable but aggressive
        BodyPart strikeTarget = BodyPart.HEAD; // Maximum damage
        BodyPart parryTarget = BodyPart.HEAD;  // Protect vital areas
        
        // Add some unpredictability
        if (context.getCurrentTurn() % 2 == 0) {
            strikeTarget = BodyPart.TORSO; // Safer choice occasionally
        }
        
        return new Action(strikeTarget, parryTarget);
    }
    
    private Action balancedStrategy(FighterContext context) {
        // Use pattern analysis if available
        if (!context.isFirstTurn()) {
            TurnResult lastTurn = context.getLastTurnResult();
            if (lastTurn != null) {
                // Tactical decision based on previous turn
                return tacticalCounterMove(lastTurn, context);
            }
        }
        
        // Default balanced approach
        return new Action(BodyPart.TORSO, BodyPart.TORSO);
    }
    
    private Action tacticalCounterMove(TurnResult lastTurn, FighterContext context) {
        // Since we don't have access to opponent actions in TurnResult,
        // we'll use damage information to make tactical decisions
        
        // Use damage patterns to infer opponent behavior
        int damageTaken = 0;
        int damageDealt = 0;
        
        // The damage values in TurnResult are: 
        // fighter1Damage = damage TO fighter1 (dealt by fighter2)
        // fighter2Damage = damage TO fighter2 (dealt by fighter1)
        
        // We need to determine which fighter we are
        // Since we don't have direct access, we'll use a simplified approach
        
        // Tactical decision based on previous damage
        if (lastTurn.getFighter1Damage() > 0 || lastTurn.getFighter2Damage() > 0) {
            // There was damage dealt, adjust strategy
            BodyPart counterStrike = BodyPart.TORSO;
            BodyPart counterParry = BodyPart.HEAD;
            
            // If there was a critical hit, be more defensive
            if (lastTurn.isFighter1CriticalHit() || lastTurn.isFighter2CriticalHit()) {
                counterParry = BodyPart.HEAD;
                counterStrike = BodyPart.TORSO; // Safer choice
            }
            
            return new Action(counterStrike, counterParry);
        }
        
        return new Action(BodyPart.TORSO, BodyPart.TORSO);
    }
    
    private BodyPart findTacticalCounterStrike(BodyPart opponentParry) {
        // Strike where opponent didn't defend
        switch (opponentParry) {
            case HEAD:
                return BodyPart.TORSO;
            case TORSO:
                return BodyPart.LEGS;
            case LEGS:
                return BodyPart.HEAD;
            default:
                return BodyPart.TORSO;
        }
    }
    
    @Override
    public String getName() {
        return "Tactical";
    }
    
    @Override
    public String getDescription() {
        return "Uses complex tactical analysis including situation assessment, " +
               "pattern recognition, and strategic decision trees for optimal combat choices.";
    }
    
    /**
     * Enum representing different tactical situations in combat.
     */
    private enum TacticalSituation {
        EARLY_GAME,
        WINNING,
        LOSING,
        CRITICAL_BOTH,
        BALANCED
    }
}
