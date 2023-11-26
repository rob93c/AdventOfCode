package com.cellar.advent.year22.day8;

import static java.util.Comparator.naturalOrder;

import com.cellar.advent.utils.AdventUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://adventofcode.com/2022/day/8">Advent of Code day 8</a>
 */
public class Main {

	public static void main(String[] args) {
		var treesGrid = AdventUtils.getFileLines("tree-map.txt");

		var trees = transformGrid(treesGrid);

		AdventUtils.print("Part 1 - Visible trees: " + getVisibleTrees(trees));

		AdventUtils.print("Part 2 - Max scenic score: " + getMaxScenicScore(trees));
	}

	private static int[][] transformGrid(List<String> grid) {
		int[][] trees = new int[grid.size()][grid.size()];

		for (int i = 0; i < grid.size(); i++) {
			for (int j = 0; j < grid.size(); j++) {
				trees[i][j] = Character.getNumericValue(grid.get(i).toCharArray()[j]);
			}
		}

		return trees;
	}

	private static int getVisibleTrees(int[][] grid) {
		int visibleTrees = 0;

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (isVisible(i, j, grid)) visibleTrees += 1;
			}
		}

		return visibleTrees;
	}

	private static boolean isVisible(int x, int y, int[][] grid) {
		return (x == 0 || y == 0 || x == grid.length - 1 || y == grid.length - 1)
				|| isVisibleFromLeft(x, y, grid)
				|| isVisibleFromRight(x, y, grid)
				|| isVisibleFromTop(x, y, grid)
				|| isVisibleFromBottom(x, y, grid);
	}

	private static boolean isVisibleFromLeft(int x, int y, int[][] grid) {
		for (int i = 0; i < x; i++) {
			if (grid[i][y] >= grid[x][y]) return false;
		}

		return true;
	}

	private static boolean isVisibleFromRight(int x, int y, int[][] grid) {
		for (int i = x + 1; i < grid.length; i++) {
			if (grid[i][y] >= grid[x][y]) return false;
		}

		return true;
	}

	private static boolean isVisibleFromTop(int x, int y, int[][] grid) {
		for (int i = 0; i < y; i++) {
			if (grid[x][i] >= grid[x][y]) return false;
		}

		return true;
	}

	private static boolean isVisibleFromBottom(int x, int y, int[][] grid) {
		for (int i = y + 1; i < grid.length; i++) {
			if (grid[x][i] >= grid[x][y]) return false;
		}

		return true;
	}

	/**
	 * Part 2
	 */
	private static int getMaxScenicScore(int[][] grid) {
		var scenicScores = new ArrayList<Integer>();

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				scenicScores.add(getScenicScore(i, j, grid));
			}
		}

		return scenicScores.stream().max(naturalOrder()).orElse(0);
	}

	private static int getScenicScore(int x, int y, int[][] grid) {
		return getLeftScenicScore(x, y, grid)
				* getRightScenicScore(x, y, grid)
				* getTopScenicScore(x, y, grid)
				* getBottomScenicScore(x, y, grid);
	}

	private static int getLeftScenicScore(int x, int y, int[][] grid) {
		int score = 0;

		for (int i = x - 1; i >= 0; i--) {
			if (grid[y][i] < grid[y][x]) {
				score++;
			} else if (grid[y][i] == grid[y][x]) {
				score++;
				break;
			} else {
				break;
			}
		}

		return score;
	}

	private static int getRightScenicScore(int x, int y, int[][] grid) {
		int score = 0;

		for (int i = x + 1; i < grid.length; i++) {
			if (grid[y][i] < grid[y][x]) {
				score++;
			} else if (grid[y][i] == grid[y][x]) {
				score++;
				break;
			} else {
				break;
			}
		}

		return score;
	}

	private static int getTopScenicScore(int x, int y, int[][] grid) {
		int score = 0;

		for (int i = y - 1; i >= 0; i--) {
			if (grid[i][x] < grid[y][x]) {
				score++;
			} else if (grid[i][x] == grid[y][x]) {
				score++;
				break;
			} else {
				break;
			}
		}

		return score;
	}

	private static int getBottomScenicScore(int x, int y, int[][] grid) {
		int score = 0;

		for (int i = y + 1; i < grid.length; i++) {
			if (grid[i][x] < grid[y][x]) {
				score++;
			} else if (grid[i][x] == grid[y][x]) {
				score++;
				break;
			} else {
				break;
			}
		}

		return score;
	}
}
