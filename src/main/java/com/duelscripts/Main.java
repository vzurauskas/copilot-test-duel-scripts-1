package com.duelscripts;

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
        
        // Create fighters
        Fighter knight = new Fighter("Sir Galahad", 50, 8, sword);
        Fighter barbarian = new Fighter("Conan", 55, 6, axe);
        
        // Create and run a complete battle
        Game game = new Game(knight, barbarian);
        BattleResult result = game.runFullCombat();
        
        // Display additional battle information
        System.out.println("\nBATTLE ANALYSIS:");
        System.out.println("=".repeat(60));
        System.out.println("Total turns: " + result.getTotalTurns());
        System.out.println("Winner: " + (result.getWinner() != null ? result.getWinner().getName() : "None"));
        System.out.println("Turn limit reached: " + result.reachedTurnLimit());
        System.out.println("Turn history count: " + result.getTurnHistory().size());
        
        // Display final fighter states
        System.out.println("\nFINAL FIGHTER STATES:");
        System.out.println("-".repeat(40));
        System.out.println(knight.getName() + " - HP: " + knight.getHitPoints() + "/" + knight.getMaxHitPoints());
        System.out.println(barbarian.getName() + " - HP: " + barbarian.getHitPoints() + "/" + barbarian.getMaxHitPoints());
    }
}
