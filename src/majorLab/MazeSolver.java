package majorLab;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class MazeSolver {
	private Stack<Square> stack;
	// private MyStack stack;
	private Maze maze;
	private Square current;
	private List<Square> locations;
	private boolean flag;

	/**
	 * preferred constructor
	 * 
	 * @param maze object from the maze class
	 */
	public MazeSolver(Maze maze) {
		// stack = new MyStack();
		stack = new Stack<Square>();
		current = maze.getStart();
		stack.push(current);

		this.maze = maze;

		locations = new ArrayList<Square>();
		flag = false;
	}

	/**
	 * abstract method that gets overridden in the Maze Solver Stack class
	 */
	public abstract void makeEmpty();

	/**
	 * abstract method that gets overridden in the Maze Solver Stack class
	 * 
	 * @return boolean true or false if empty or not
	 */
	public abstract boolean isEmpty();

	/**
	 * abstract method that gets overridden in the Maze Solver Stack class
	 */
	public abstract void add(Square s);

	/**
	 * abstract method that gets overridden in the Maze Solver Stack class
	 * 
	 * @return Square returns the next method
	 */
	public abstract Square next();

	/**
	 * outputs if the maze got solved or not
	 * 
	 * @return boolean true or false if the maze is solved
	 */
	public boolean isSolved() {
		return flag;
	}

	/**
	 * makes one iteration to solve the maze
	 */
	public void step() {
		if (stack.isEmpty()) {
			flag = false;
			return;
		}

		while (stack.peek() == null) {
			stack.pop();
		}
		Square temp = null;
		if (!stack.isEmpty()) {
			temp = stack.pop();
			current = temp;
		}

		maze.assign(temp.getRow(), temp.getCol(), '.');

		locations = maze.getNeighbors(temp);

		for (int i = 0; i < locations.size(); i++) {
			if (maze.getLayout()[locations.get(i).getRow()][locations.get(i).getCol()].getType() == 3) {
				flag = true;
				isSolved();
				return;
			}

			if (locations.get(i).getType() == 1 || locations.get(i).getStatus() == '.'
					|| locations.get(i).getStatus() == 'o' || locations.get(i).getType() == 2
					|| locations.get(i).getType() == 3) {

				locations.remove(i);
				i--;
			}
		}

		for (int i = 0; i < locations.size(); i++) {
			if (maze.getLayout()[locations.get(i).getRow()][locations.get(i).getCol()].getStatus() != '.') {
				maze.assign(locations.get(i).getRow(), locations.get(i).getCol(), 'o');
				stack.push(locations.get(i));
			}
		}
	}

	/**
	 * outputs if the maze is solved or not
	 * 
	 * @return String the message displayed in the console
	 */
	public String getPath() {
		if (isSolved()) {
			return "This maze is solved.";
		} else if (stack.isEmpty()) {
			return "This maze is unsolvable.";
		}
		return "This maze is not yet solved.";
	}

	/**
	 * calls the step method until it is solved
	 */
	public void solve() {
		while (!isSolved()) {
			step();
		}
	}
}
