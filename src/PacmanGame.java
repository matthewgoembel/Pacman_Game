import java.util.*;

/*
 * Author: Matthew Goembel, mgoembel2022@my.fit.edu
 * Course: CSE 2010
 * Section: 34, Fall 2023
 * Description: The program utilizes a 2D grid to simulate
 * the pacman game. The program uses the rules and game logic as the
 * real pacman. The program provide a list of methods that move each
 * component of the game, and show how the ghosts calculate their path
 * to get pacman.
 */

public class PacmanGame {
   // Initialize game attributes
   private static final char PACMAN = 'P';
   private static final char DOT = '.';
   private static final char OBSTACLE = '#';
   private static final char EMPTY = ' ';
   private final char[][] grid;
   private int playerPoints;
   private int possiblePoints;
   private int pacmanRow;
   private int pacmanCol;
   private final ArrayList<Ghost> ghosts = new ArrayList<>();

   /**
    * PacmanGame constructor constructs initial game grid
    */
   public PacmanGame(char[][] initialGrid) {
      this.grid = initialGrid;
      this.playerPoints = 0;
      findPacmanPosition();
      for (int i = 0; i < grid.length; i++) {
         for (int j = 0; j < grid[i].length; j++) {
            if (Character.isLetter(grid[i][j]) && grid[i][j] != 'P') {
               ghosts.add(new Ghost(grid[i][j], i, j));
            } else if (grid[i][j] == '.') {
               possiblePoints++;
            }
         }
      }
      System.out.println(possiblePoints);
   }

   /**
    * Searches through the grid to find the position [x][y] of pacman
    */
   private void findPacmanPosition() {
      for (int i = 0; i < grid.length; i++) {
         for (int j = 0; j < grid[i].length; j++) {
            if (grid[i][j] == PACMAN) {
               pacmanRow = i;
               pacmanCol = j;
               return;
            }
         }
      }
   }

   /**
    * Prints out the current game grid matrix
    */
   public void displayGrid() {
      for (char[] row : grid) {
         for (char cell : row) {
            System.out.print(cell);
         }
         System.out.println();
      }
   }

   /**
    * Prints out player points
    */
   public void displayInfo() {
      System.out.println("Points: " + playerPoints);
   }

   /**
    * Use player direction input to get next position
    * Moves pacman to the next position
    * Increments points when pacman collects a point(.)
    */
   public void movePacman(char direction) {
      int newRow = pacmanRow;
      int newCol = pacmanCol;
      // Use direction set next position
      switch (direction) {
         case 'u' -> newRow--;
         case 'd' -> newRow++;
         case 'l' -> newCol--;
         case 'r' -> newCol++;
      }
      // If the move is valid, move
      if (isValidMove(newRow, newCol)) {
         char cell = grid[newRow][newCol];
         if (cell == DOT) {
            playerPoints++; // Update player point count
         }
         // Update grid
         grid[pacmanRow][pacmanCol] = EMPTY;
         grid[newRow][newCol] = PACMAN;
         // Update pacman position
         pacmanRow = newRow;
         pacmanCol = newCol;
      }
   }

   /**
    * Returns true if the next move, using [row][col] of next
    * move, is valid (In bounds and no obstacle)
    */
   private boolean isValidMove(int row, int col) {
      return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length && grid[row][col] != OBSTACLE;
   }

   /**
    * Ghosts are moved in alphabetical order
    * Positions of each ghost are set to next position in the shortest path to pacman
    * Ghosts should not collect dots
    * Path of each ghost to pacman is printed
    */
   public void moveGhosts() {
      // Sort alphabetically by comparing each ghost name
      ghosts.sort(Comparator.comparing(Ghost::getName));

      for (Ghost ghost : ghosts) {
         // Get position of ghost
         int ghostRow = ghost.getX();
         int ghostCol = ghost.getY();

         // Store the content of the cell that the ghost is moving from
         char fromContent = grid[ghostRow][ghostCol];

         List<int[]> shortestPath = shortestPath(grid, ghostRow, ghostCol); // BFS Shortest path

         if (!shortestPath.isEmpty()) {
            int[] nextMove = shortestPath.get(0); // Next closest spot
            // Restore the previous spot only if it was a dot
            if (fromContent == DOT) {
               grid[ghostRow][ghostCol] = DOT;
            } else {
               // Update grid
               grid[ghostRow][ghostCol] = EMPTY;
            }
            grid[nextMove[0]][nextMove[1]] = ghost.getName();
            // Update ghost position
            ghost.setX(nextMove[0]);
            ghost.setY(nextMove[1]);
         }
         // Prints length and coordinates of the path
         System.out.printf("%s %s %d ", "Ghost", ghost.getName(), shortestPath.size());
         for (int[] coordinates : shortestPath) {
            System.out.print("(" + coordinates[0] + ", " + coordinates[1] + ") ");
         }
         System.out.println();
      }

      System.out.println();
   }

   /**
    * Finds the shortest path from ghost position to pacman position
    * Uses BFS to calculate the min distance from the ghost to each cell
    * Then traces back the shortest path from pacman to the ghost based on the distances
    *
    */
   public List<int[]> shortestPath(char[][] grid, int ghostRow, int ghostCol) {
      int pacRow = pacmanRow;
      int pacCol = pacmanCol;
      int[][] visited = new int[grid.length][grid[0].length]; // copy grid
      // Initializes grid with max distance
      for (int i = 0; i < grid.length; i++) {
         Arrays.fill(visited[i], Integer.MAX_VALUE);
      }
      // Initialize queue with ghost positions
      visited[ghostRow][ghostCol] = 0;
      Queue<int[]> queue = new LinkedList<>();
      queue.add(new int[]{ghostRow, ghostCol});
      int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right
      // BFS traversal
      while (!queue.isEmpty()) {
         int[] current = queue.poll();
         // Explore neighbor cells
         for (int[] dir : directions) {
            int newRow = current[0] + dir[0];
            int newCol = current[1] + dir[1];
            // If the position is unvisited and valid
            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length
                    && grid[newRow][newCol] != OBSTACLE && visited[newRow][newCol] == Integer.MAX_VALUE) {
               visited[newRow][newCol] = visited[current[0]][current[1]] + 1; // Update Position
               queue.add(new int[]{newRow, newCol});                          // Enqueue position
            }
         }
      }
      // Trace back the shortest path
      List<int[]> shortestPath = new ArrayList<>();
      int[] current = {pacRow, pacCol};
      // Reconstruct until reaching the ghost
      while (visited[current[0]][current[1]] > 0) {
         shortestPath.add(0, new int[]{current[0], current[1]});
         // Visit neighboring cells
         for (int[] dir : directions) {
            int newRow = current[0] + dir[0];
            int newCol = current[1] + dir[1];
            // If unvisited and valid, update position
            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length
                    && visited[newRow][newCol] == visited[current[0]][current[1]] - 1) {
               current[0] = newRow;
               current[1] = newCol;
               break;
            }
         }
      }
      // Return shortest path in a list of coordinates
      return shortestPath;
   }

   /**
    * Returns true if pacman has died
    * Returns false if pacman is still alive
    */
   public boolean isGameOver() {
      for (Ghost ghost : ghosts) {
         int getY = ghost.getY();
         int getX = ghost.getX();
         // If ghost is same space (Eats) as pacman
         if (pacmanCol == getY && pacmanRow == getX) {
            return true;
         }
         if (possiblePoints == playerPoints) {
            return  true;
         }
      }
      return false;
   }
}

class Ghost {
   private int x;
   private int y;
   private final char name;
   // Ghost Object
   public Ghost(char name, int x, int y) {
      this.x = x;
      this.y = y;
      this.name = name;
   }
   // Return ghosts X (col) position
   public int getX() {
      return x;
   }
   // Return ghosts Y (row) position
   public int getY() {
      return y;
   }
   // Sets ghosts X (col) position
   public void setX(int x) {
      this.x = x;
   }
   // Sets ghosts y (row) position
   public void setY(int y) {
      this.y = y;
   }
   // Returns ghost name
   public char getName () {
      return name;
   }
}
