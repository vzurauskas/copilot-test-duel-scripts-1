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
        System.out.println();
        
        // Create weapons
        Weapon sword = new Weapon("Iron Sword", 10, 0.15);
        Weapon axe = new Weapon("Battle Axe", 12, 0.10);
        
        // Create fighters
        Fighter knight = new Fighter("Sir Galahad", 50, 8, sword);
        Fighter barbarian = new Fighter("Conan", 55, 6, axe);
        
        System.out.println("FIGHTERS:");
        System.out.println(knight);
        System.out.println(barbarian);
        System.out.println();
        
        // Create and execute a game turn
        Game game = new Game(knight, barbarian);
        
        System.out.println("TURN 1:");
        System.out.println("-".repeat(40));
        CombatResolver.TurnResult result = game.executeTurn();
        System.out.println(result.getDescription());
        
        if (game.isGameOver()) {
            Fighter winner = game.getWinner();
            if (winner != null) {
                System.out.println("WINNER: " + winner.getName() + "!");
            } else {
                System.out.println("Both fighters have fallen!");
            }
        } else {
            System.out.println("The battle continues...");
        }
    }
}
