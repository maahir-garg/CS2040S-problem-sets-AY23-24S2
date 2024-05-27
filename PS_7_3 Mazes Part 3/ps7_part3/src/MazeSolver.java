import java.util.*;
import java.util.function.Function;

public class MazeSolver implements IMazeSolver {

	private class Node implements Comparable<Node> {

		int row;
		int col;
		int priority;

		Node(int row, int col, int priority) {
			this.row = row;
			this.col = col;
			this.priority = priority;
		}

		int getRow() {
			return this.row;
		}

		int getCol() {
			return this.col;
		}

		int getPriority() {
			return this.priority;
		}

		void setPriority(int priority) {
			this.priority = priority;
		}

		@Override
		public String toString() {
			return this.row + " " + this.col + " " + this.priority;
		}

		@Override
		public int compareTo(Node o) {
			if (this.getPriority() < o.getPriority()) return -1;
			if (this.getPriority() == o.getPriority()) return 0;
			return 1;
		}
	}

	private static final int TRUE_WALL = Integer.MAX_VALUE;
	private static final int EMPTY_SPACE = 0;
	private static final List<Function<Room, Integer>> WALL_FUNCTIONS = Arrays.asList(
			Room::getNorthWall,
			Room::getEastWall,
			Room::getWestWall,
			Room::getSouthWall
	);
	private static final int[][] DELTAS = new int[][] {
			{ -1, 0 }, // North
			{ 0, 1 }, // East
			{ 0, -1 }, // West
			{ 1, 0 } // South
	};

	private Maze maze;
	private boolean[][] visited;

//	private boolean[][][] visitedWithPower;
//	private int[][][][] parentWithPower;
//	private int[][][] parent;

	private PriorityQueue<Node> queue;
	int[][] scared;


	public MazeSolver() {
		// TODO: Initialize variables.
		maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		queue = new PriorityQueue<>();
		this.scared = new int[maze.getRows()][maze.getColumns()];
		this.visited = new boolean[maze.getRows()][maze.getColumns()];
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find minimum fear level.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}
		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
			}
		}
		queue = new PriorityQueue<>();
		queue.add(new Node(startRow, startCol, 0));

		while (!queue.isEmpty()) {
			Node node = queue.poll();
			visited[node.getRow()][node.getCol()] = true;
			if (node.getRow() == endRow && node.getCol() == endCol) {
				return node.getPriority();
			}
			for (int i = 0; i < 4; i++) {
				int wall = WALL_FUNCTIONS.get(i).apply(maze.getRoom(node.getRow(), node.getCol()));
				if (wall == TRUE_WALL || visited[node.getRow() + DELTAS[i][0]][node.getCol() + DELTAS[i][1]]) {
					continue;
				} else if (wall == EMPTY_SPACE) {
					Node temp = new Node(node.getRow() + DELTAS[i][0], node.getCol() + DELTAS[i][1], node.getPriority() + 1);
                    queue.add(temp);
				} else {
					Node temp = new Node(node.getRow() + DELTAS[i][0], node.getCol() + DELTAS[i][1], node.getPriority() + wall);
                    queue.add(temp);
				}
			}
		}
		return null;
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find minimum fear level given new rules.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}
		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
			}
		}
		queue = new PriorityQueue<>();
		queue.add(new Node(startRow, startCol, 0));

		while (!queue.isEmpty()) {
			Node node = queue.poll();
			visited[node.getRow()][node.getCol()] = true;
			if (node.getRow() == endRow && node.getCol() == endCol) {
				return node.getPriority();
			}
			for (int i = 0; i < 4; i++) {
				int wall = WALL_FUNCTIONS.get(i).apply(maze.getRoom(node.getRow(), node.getCol()));
				if (wall == TRUE_WALL || visited[node.getRow() + DELTAS[i][0]][node.getCol() + DELTAS[i][1]]) {
					continue;
				} else if (wall == EMPTY_SPACE) {
					Node temp = new Node(node.getRow() + DELTAS[i][0], node.getCol() + DELTAS[i][1], node.getPriority() + 1);
					queue.add(temp);
				} else {
					Node temp = new Node(node.getRow() + DELTAS[i][0], node.getCol() + DELTAS[i][1], Math.max(node.getPriority(), wall));
					queue.add(temp);
				}
			}
		}
		return null;
	}


	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol, int sRow, int sCol) throws Exception {
		// TODO: Find minimum fear level given new rules and special room.
		// there are only 2 cases
		// start room -> end room
		// or special room -> end room
		// bonusSearch2Helper is same as bonus Search but sets initial scariness as -1 instead of 0;

		if (bonusSearch(startRow, startCol, endRow, endCol) == null) return bonusSearch2Helper(sRow, sCol, endRow, endCol);
		if (bonusSearch2Helper(sRow, sCol, endRow, endCol) == null) return bonusSearch(startRow, startCol, endRow, endCol);
		return Math.min(bonusSearch(startRow, startCol, endRow, endCol), bonusSearch2Helper(sRow, sCol, endRow, endCol));
	}

	public Integer bonusSearch2Helper(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find minimum fear level given new rules.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}
		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
			}
		}
		queue = new PriorityQueue<>();
		queue.add(new Node(startRow, startCol, -1));

		while (!queue.isEmpty()) {
			Node node = queue.poll();
			visited[node.getRow()][node.getCol()] = true;
			if (node.getRow() == endRow && node.getCol() == endCol) {
				return node.getPriority();
			}
			for (int i = 0; i < 4; i++) {
				int wall = WALL_FUNCTIONS.get(i).apply(maze.getRoom(node.getRow(), node.getCol()));
				if (wall == TRUE_WALL || visited[node.getRow() + DELTAS[i][0]][node.getCol() + DELTAS[i][1]]) {
					continue;
				} else if (wall == EMPTY_SPACE) {
					Node temp = new Node(node.getRow() + DELTAS[i][0], node.getCol() + DELTAS[i][1], node.getPriority() + 1);
					queue.add(temp);
				} else {
					Node temp = new Node(node.getRow() + DELTAS[i][0], node.getCol() + DELTAS[i][1], Math.max(node.getPriority(), wall));
					queue.add(temp);
				}
			}
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("haunted-maze-sample.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 1, 4));
//			System.out.println(solver.bonusSearch(0, 1, 0, 3));
//			System.out.println(solver.bonusSearch(0, 0, 0, 3, 0, 1));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
