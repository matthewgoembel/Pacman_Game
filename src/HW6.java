import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * Author: Matthew Goembel, mgoembel2022@my.fit.edu
 * Course: CSE 2010
 * Section: 34, Fall 2023
 * Description: HW6 reads in a file containing an initial
 * pacman game board with obstacles and ghosts. The program
 * then displays the game and upon player move, pacman will
 * and the ghosts will move to the next closest position to
 * pacman. The game runs similar real pacman, but with
 * only 1 move at a time.
 */

public class HW6 {
   public static void main (final String[] args) {
      try {
         // Read input from a file specified in the command line arguments
         final String filename = args[0];
         final Scanner fileScanner = new Scanner(new File(filename));
         // Store grid dimensions
         final int rows = fileScanner.nextInt();
         final int cols = fileScanner.nextInt();
         fileScanner.nextLine(); // Consume the rest of the first line

         final char[][] initialGrid = new char[rows][cols];
         // Initialize each the grid game board
         for (int i = 0; i < rows; i++) {
            final String line = fileScanner.nextLine();
            initialGrid[i] = line.toCharArray();
         }

         // Start the game
         final PacmanGame game = new PacmanGame(initialGrid);
         final Scanner scanner = new Scanner(System.in);
         // While alive
         while (true) {
            // Print
            game.displayGrid();
            game.displayInfo();
            // Prompt user to move pacman
            System.out.print("Enter your move [(u)p, (d)own, (l)eft, (r)ight]: ");
            final char move = scanner.next().charAt(0);
            // Update game accordingly
            game.movePacman(move);
            game.moveGhosts();
            // Check is pacman has died yet or won
            if (game.isGameOver()) {
               System.out.println("Game Over!");
               break;
            }
         }
      } catch (FileNotFoundException e) {
         System.out.println("File not found: " + e.getMessage());
      }
   }
}
