package com.cellar.advent.year22.day10;

import com.cellar.advent.utils.AdventUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @see <a href="https://adventofcode.com/2022/day/10">Advent of Code day 10</a>
 */
public class Main {

	public static void main(String[] args) {
		var instructions = AdventUtils.getFileLines("cpu-instructions.txt");

		int signalStrength = 0;
		int currentValue = 1;
		int cycle = 0;

		for (String instruction : instructions) {
			if ("noop".equals(instruction)) {
				signalStrength += getThresholdValue(++cycle, currentValue);
			} else if (instruction.startsWith("addx")) {
				signalStrength += getThresholdValue(++cycle, currentValue);
				signalStrength += getThresholdValue(++cycle, currentValue);

				currentValue += Integer.parseInt(StringUtils.substringAfter(instruction, "addx "));
			}
		}

		AdventUtils.print("Part 1 - Signal strength is " + signalStrength);
	}

	private static int getThresholdValue(int cycle, int currentValue) {
		draw(cycle, currentValue);

		if (cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220) {
			return cycle * currentValue;
		}

		return 0;
	}

	private static void draw(int cycle, int currentValue) {
		int currentPosition = cycle % 40;

		if (currentPosition == 0) {
			System.out.println();
		} else if (currentPosition < currentValue || currentPosition > currentValue + 2) {
			System.out.print(".");
		} else {
			System.out.print("#");
		}
	}
}
