import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MazeSolver implements IMazeSolver {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
		{ -1, 0 }, // North
		{ 1, 0 }, // South
		{ 0, 1 }, // East
		{ 0, -1 } // West
	};

	private Maze maze;
	private boolean solved = false;
	private boolean[][] visited;
	private int[][][] parent;
	private int endRow, endCol;

	private ArrayList<Integer> steps;
	public MazeSolver() {
		// TODO: Initialize variables.
		solved = false;
		maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		visited = new boolean[maze.getRows()][maze.getColumns()];
		parent = new int[maze.getRows()][maze.getColumns()][];
		solved = false;
		steps = new ArrayList<>();
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find shortest path.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}
		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}
		// set all visited flag to false
		// before we begin our search
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
				maze.getRoom(i, j).onPath = false;
				parent[i][j] = null;
			}
		}
		solved = false;
		steps = new ArrayList<>();

		Queue<int[]> currQueue = new LinkedList<>();
		currQueue.add(new int[] { startRow, startCol });

		int step = 0;

		while (!currQueue.isEmpty()) {
			steps.add(step, currQueue.size());
			Queue<int[]> nextQueue = new LinkedList<>();
			for (int[] current : currQueue) {
				int row = current[0];
				int col = current[1];

				if (row == endRow && col == endCol) {
					solved = true;
				}

				visited[row][col] = true;
				for (int direction = 0; direction < 4; direction++) {
					if (canGo(row, col, direction) && !visited[row + DELTAS[direction][0]][col + DELTAS[direction][1]]) { // can we go in that direction?
						// yes we can :)
						nextQueue.add(new int[]{row + DELTAS[direction][0], col + DELTAS[direction][1]});
						parent[row + DELTAS[direction][0]][col + DELTAS[direction][1]] = new int[]{row, col};
						visited[row + DELTAS[direction][0]][col + DELTAS[direction][1]] = true;

					}
				}
			}
			currQueue = nextQueue;
			step++;
		}

		if (solved) {
			int count = 0;
			int row = endRow;
			int col = endCol;
			while (true) {
				if (row == startRow && col == startCol) {
					maze.getRoom(row, col).onPath = true;
					return count;
				}
				count++;
				maze.getRoom(row, col).onPath = true;
				int[] temp = parent[row][col];
				row = temp[0];
				col = temp[1];
			}
		}



		return null;
	}



	private boolean canGo(int row, int col, int dir) {
		// not needed since our maze has a surrounding block of wall
		// but Joe the Average Coder is a defensive coder!
		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows()) return false;
		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return false;

		switch (dir) {
			case NORTH:
				return !maze.getRoom(row, col).hasNorthWall();
			case SOUTH:
				return !maze.getRoom(row, col).hasSouthWall();
			case EAST:
				return !maze.getRoom(row, col).hasEastWall();
			case WEST:
				return !maze.getRoom(row, col).hasWestWall();
		}

		return false;
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		// TODO: Find number of reachable rooms.
		if (k < steps.size()) {
			return steps.get(k);
		}
		return 0;
	}

	public static void main(String[] args) {
		// Do remember to remove any references to ImprovedMazePrinter before submitting
		// your code!
		try {
			Maze maze = Maze.readMaze("maze-sample.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(4, 0, 0, 0));
			MazePrinter.printMaze(maze);
			ImprovedMazePrinter.printMaze(maze, 0, 0);


//			for (int i = 0; i < maze.getRows(); i++) {
//				for (int j = 0; j < maze.getColumns(); j++) {
//					System.out.println(i + " " + j + " " + solver.pathSearch(0, 0, i, j));
//					for (int q = 0; q <= 9; ++q) {
//						System.out.println("Steps " + q + " Rooms: " + solver.numReachable(q));
//					}
//				}
//			}

			for (int i = 0; i <= 11; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
