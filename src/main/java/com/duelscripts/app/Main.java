package com.duelscripts.app;

import com.duelscripts.core.Fighter;
import com.duelscripts.core.Weapon;
import com.duelscripts.combat.Game;
import com.duelscripts.combat.BattleResult;
import com.duelscripts.combat.BattleStats;
import com.duelscripts.scripting.ScriptFactory;
import com.duelscripts.scripting.CombatScript;
import com.duelscripts.scripting.scripts.*;

/**
 * Main class for the Duel Scripts game.
 * Entry point for the text-based fighting game.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Duel Scripts!");
        System.out.println("A turn-based fighting game where fighters execute scripts.");
        System.out.println("=".repeat(60));
        
        // Create weapons
        Weapon sword = new Weapon("Iron Sword", 10, 0.15);
        Weapon axe = new Weapon("Battle Axe", 12, 0.10);
        
        // Demonstrate different combat scripts
        demonstrateScriptBattle("Aggressive vs Defensive", 
                               new AggressiveScript(), new DefensiveScript(), 
                               sword, axe);
        
        demonstrateScriptBattle("Balanced vs Tactical", 
                               new BalancedScript(), new TacticalScript(), 
                               sword, axe);
        
        demonstrateScriptBattle("Adaptive vs Berserker", 
                               new AdaptiveScript(), new BerserkerScript(), 
                               sword, axe);
    }
    
    private static void demonstrateScriptBattle(String battleName, 
                                               CombatScript script1, CombatScript script2,
                                               Weapon weapon1, Weapon weapon2) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DEMONSTRATION: " + battleName);
        System.out.println("=".repeat(60));
        
        // Create fighters with specific scripts
        Fighter fighter1 = new Fighter("Warrior A", 45, 7, weapon1, script1);
        Fighter fighter2 = new Fighter("Warrior B", 45, 7, weapon2, script2);
        
        System.out.println("Fighter 1: " + fighter1);
        System.out.println("Script: " + script1.getDescription());
        System.out.println();
        System.out.println("Fighter 2: " + fighter2);
        System.out.println("Script: " + script2.getDescription());
        System.out.println();
        
        // Run the battle
        Game game = new Game(fighter1, fighter2, 10, false); // Max 10 turns for demo
        BattleResult result = game.runFullCombat();
        
        // Show script effectiveness
        System.out.println("\nSCRIPT ANALYSIS:");
        System.out.println("-".repeat(40));
        if (result.getWinner() != null) {
            System.out.println("Winning Script: " + result.getWinner().getCombatScript().getName());
        } else {
            System.out.println("Result: Draw/Timeout");
        }
        System.out.println("Battle Duration: " + result.getTotalTurns() + " turns");
        
        BattleStats stats = result.getStatistics();
        System.out.println("Damage Ratio: " + stats.getTotalDamageByFighter1() + 
                          " vs " + stats.getTotalDamageByFighter2());
    }
}
