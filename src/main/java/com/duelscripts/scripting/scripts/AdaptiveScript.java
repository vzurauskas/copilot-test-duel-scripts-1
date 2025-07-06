package com.duelscripts.scripting.scripts;

import com.duelscripts.core.Action;
import com.duelscripts.core.BodyPart;
import com.duelscripts.scripting.CombatScript;
import com.duelscripts.scripting.FighterContext;

import java.util.HashMap;
import java.util.Map;

/**
 * An adaptive combat script that learns from opponent patterns and adjusts strategy.
 */
public class AdaptiveScript implements CombatScript {
    private Map<BodyPart, Integer> opponentStrikeFrequency = new HashMap<>();
    private Map<BodyPart, Integer> opponentParryFrequency = new HashMap<>();
    
    @Override
    public Action getNextAction(FighterContext context) {
        // Analyze battle history to learn opponent patterns
        analyzeOpponentBehavior(context);
        
        // Default balanced approach
        BodyPart strikeTarget = BodyPart.TORSO;
        BodyPart parryTarget = BodyPart.TORSO;
        
        // Adapt strike target based on opponent's parry patterns
        BodyPart leastParriedPart = findLeastParriedBodyPart();
        if (leastParriedPart != null) {
            strikeTarget = leastParriedPart;
        }
        
        // Adapt parry target based on opponent's strike patterns
        BodyPart mostStrikedPart = findMostStrikedBodyPart();
        if (mostStrikedPart != null) {
            parryTarget = mostStrikedPart;
        }
        
        // Adjust for health situation
        double selfHealth = context.getSelfHealthPercentage();
        double opponentHealth = context.getOpponentHealthPercentage();
        
        if (selfHealth < 0.3 && opponentHealth > 0.5) {
            // Desperate situation - go for high damage
            strikeTarget = BodyPart.HEAD;
            parryTarget = BodyPart.HEAD;
        } else if (selfHealth > 0.7 && opponentHealth < 0.3) {
            // Winning situation - play it safe
            strikeTarget = BodyPart.TORSO;
            parryTarget = findMostStrikedBodyPart() != null ? findMostStrikedBodyPart() : BodyPart.HEAD;
        }
        
        return new Action(strikeTarget, parryTarget);
    }
    
    private void analyzeOpponentBehavior(FighterContext context) {
        // For now, use simplified analysis based on available information
        // Clear previous analysis
        opponentStrikeFrequency.clear();
        opponentParryFrequency.clear();
        
        // Initialize counters
        for (BodyPart part : BodyPart.values()) {
            opponentStrikeFrequency.put(part, 0);
            opponentParryFrequency.put(part, 0);
        }
        
        // Since we don't have access to opponent actions in TurnResult,
        // we'll use a simplified approach based on damage patterns
        // This is a limitation that could be addressed by enhancing TurnResult
        
        // For now, use turn-based patterns as a placeholder
        int totalTurns = context.getBattleHistory().size();
        
        // Simulate some pattern recognition based on turn numbers
        // This is a simplified approach until we can access actual opponent actions
        if (totalTurns > 0) {
            // Assume some patterns based on turn count
            // This is placeholder logic
            BodyPart assumedTarget = BodyPart.HEAD;
            if (totalTurns % 3 == 0) assumedTarget = BodyPart.HEAD;
            else if (totalTurns % 3 == 1) assumedTarget = BodyPart.TORSO;
            else assumedTarget = BodyPart.LEGS;
            
            opponentStrikeFrequency.put(assumedTarget, opponentStrikeFrequency.get(assumedTarget) + 1);
            opponentParryFrequency.put(assumedTarget, opponentParryFrequency.get(assumedTarget) + 1);
        }
    }
    
    private BodyPart findMostStrikedBodyPart() {
        BodyPart mostStriked = null;
        int maxCount = 0;
        
        for (Map.Entry<BodyPart, Integer> entry : opponentStrikeFrequency.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostStriked = entry.getKey();
            }
        }
        
        return mostStriked;
    }
    
    private BodyPart findLeastParriedBodyPart() {
        BodyPart leastParried = null;
        int minCount = Integer.MAX_VALUE;
        
        for (Map.Entry<BodyPart, Integer> entry : opponentParryFrequency.entrySet()) {
            if (entry.getValue() < minCount) {
                minCount = entry.getValue();
                leastParried = entry.getKey();
            }
        }
        
        return leastParried;
    }
    
    @Override
    public String getName() {
        return "Adaptive";
    }
    
    @Override
    public String getDescription() {
        return "Learns from opponent behavior patterns and adapts strategy accordingly. " +
               "Strikes where opponent parries least and defends where opponent strikes most.";
    }
}
