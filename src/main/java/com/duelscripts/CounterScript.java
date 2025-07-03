package com.duelscripts;

/**
 * A counter-attacking combat script that reacts to opponent's previous actions.
 */
public class CounterScript implements CombatScript {
    
    @Override
    public Action getNextAction(FighterContext context) {
        // Default actions for first turn
        BodyPart strikeTarget = BodyPart.TORSO;
        BodyPart parryTarget = BodyPart.HEAD;
        
        if (!context.isFirstTurn()) {
            // Since we don't have access to opponent's actual actions,
            // we'll use a simplified counter strategy based on turn patterns
            
            // Simple pattern-based counter strategy
            int turn = context.getCurrentTurn();
            
            // Assume opponent follows some pattern and counter it
            if (turn % 3 == 0) {
                strikeTarget = BodyPart.HEAD;
                parryTarget = BodyPart.TORSO;
            } else if (turn % 3 == 1) {
                strikeTarget = BodyPart.TORSO;
                parryTarget = BodyPart.LEGS;
            } else {
                strikeTarget = BodyPart.LEGS;
                parryTarget = BodyPart.HEAD;
            }
        }
        
        // Adjust based on health situation
        double selfHealth = context.getSelfHealthPercentage();
        if (selfHealth < 0.25) {
            // Desperate - go for maximum damage
            strikeTarget = BodyPart.HEAD;
        }
        
        return new Action(strikeTarget, parryTarget);
    }
    
    /**
     * Finds a body part to target that's different from the opponent's last parry.
     */
    private BodyPart findCounterTarget(BodyPart opponentLastParry) {
        // Simple counter logic: if opponent parried HEAD, strike TORSO, etc.
        switch (opponentLastParry) {
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
        return "Counter";
    }
    
    @Override
    public String getDescription() {
        return "Reacts to opponent's previous actions by countering their strategy. " +
               "Parries where opponent struck and strikes where opponent didn't defend.";
    }
}
