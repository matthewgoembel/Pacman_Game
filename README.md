# Pacman_Game
In the game of Pac-man, the player controls the Pac-man in
a maze to earn points by consuming dots. To foil Pac-man’s
apetite, ghosts roam around trying to consume Pac-man.
HW6 explores graph algorithms to simulate a simpler ver-
sion of the Pac-man game. Given a starting position in a 2D
grid world, a player’s goal is to move Pac-man to consume the
most dots (each dot earns one point) without being consumed
by one of the ghosts. Naturally, once consumed, a dot is no
longer available. At each step, the player can move Pac-man
up, down, left, or right to an adjacent empty cell or a cell with
a dot. Similarly, at each step, the ghost can move in those
four directions to an adjacent cell that is empty, with a dot, or
with Pac-man. Ghosts do not consume dots and ignore them.
For simplicity, the ghosts move at the same speed as Pac-man.
The player moves Pac-man first, then each ghost (in alpha-
betical order) will move. Trying to reach Pac-man quickly,
each ghost decides which direction to move based on the short-
est path from its cell to Pac-man’s cell. The distance from one
cell to an adjacent cell is 1. For easier debugging and testing,
during Breadth-First Search for the path, consider the valid
adjacent cells in this order: up, down, left, and right. Each
cell can have Pac-man, a ghost, a dot, or an obstacle, or can
be empty. *Note that since ghosts do not consume dots, a
cell could have both a ghost and a dot, but only the ghost is
shown.* The game stops after no dots remain, or one of the
ghosts reaches Pac-man (cell).

Input: Command-line argument for HW6.java is a filename
of the 2D grid world—the first line has number of rows and
columns of the world, the following lines have the initial world
represented by these characters:
• P represents Pac-man
• . represents a dot that Pac-man likes to consume
• G, H, O, S represent ghosts (up to 4) with a dot in the cell
• g, h, o, s represent ghosts without a dot in the cell
• # represents a stationary obstacle
• a space represents empty

During the game, via the keyboard, the player can input u,
d, l, and r to indicate moving up, down, left, and right to an
adjacent cell. If the input is invalid (incorrect letter or the
adjacent cell is not empty), prompt the player to re-enter.

Output: Output goes to the standard output (screen):
1. the world with row numbers on the left and column num-
bers on the top
2. Please enter your move [u(p), d(own), l(elf), or r(ight)]:
3. the world with row numbers on the left and column num-
bers on the top
4. Points:
5. For each ghost (in alphabetical order), display its move
(u/d/l/r), length of its shortest path to Pac-man, and
cells on the shortest path starting with the ghost cell
before the move and ends with Pac-man’s cell:
Ghost g: move shortestP athLength (row1,col1)
(row2,col2) ...
...
Ghost s: move shortestP athLength (row1,col1)
(row2,col2) ...
Row 0 is the top row and column 0 is on the leftmost column.
