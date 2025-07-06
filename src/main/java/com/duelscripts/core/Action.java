package com.duelscripts.core;

/**
 * Represents a fighter's action for a single turn.
 * Each turn, a fighter strikes one body part and parries another.
 */
public class Action {
    private final BodyPart strikeTarget;
    private final BodyPart parryTarget;
    
    public Action(BodyPart strikeTarget, BodyPart parryTarget) {
        this.strikeTarget = strikeTarget;
        this.parryTarget = parryTarget;
    }
    
    public BodyPart getStrikeTarget() {
        return strikeTarget;
    }
    
    public BodyPart getParryTarget() {
        return parryTarget;
    }
    
    @Override
    public String toString() {
        return String.format("Strike: %s, Parry: %s", strikeTarget, parryTarget);
    }
}
