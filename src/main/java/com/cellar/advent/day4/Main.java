package com.cellar.advent.day4;

import com.cellar.advent.utils.AdventUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @see <a href="https://adventofcode.com/2022/day/4">Advent of Code day 4</a>
 */
public class Main {

	public static void main(String[] args) {
		var lines = AdventUtils.getFileLines("assignment-pairs.txt");

		AdventUtils.print("Part 1 - Total overlapping shifts: " + lines.stream().filter(Main::fullyOverlaps).count());

		AdventUtils.print("Part 2 - Total overlapping shifts: " + lines.stream().filter(Main::partiallyOverlaps).count());
	}

	private static boolean fullyOverlaps(String line) {
		List<Integer> shifts = getNumbers(line);

		return (shifts.get(0) >= shifts.get(2) && shifts.get(1) <= shifts.get(3))
				|| (shifts.get(2) >= shifts.get(0) && shifts.get(3) <= shifts.get(1));
	}

	private static List<Integer> getNumbers(String line) {
		int firstDash = line.indexOf("-");
		int comma = line.indexOf(",");
		int secondDash = line.indexOf("-", comma);

		List<Integer> shifts = new ArrayList<>();

		Stream.of(line.substring(0, firstDash), line.substring(firstDash + 1, comma), line.substring(comma + 1, secondDash), line.substring(secondDash + 1))
				.mapToInt(Integer::parseInt)
				.forEach(shifts::add);

		return shifts;
	}

	private static boolean partiallyOverlaps(String line) {
		List<Integer> shifts = getNumbers(line);

		return (shifts.get(3) >= shifts.get(0) && shifts.get(3) <= shifts.get(1))
				|| (shifts.get(2) >= shifts.get(0) && shifts.get(2) <= shifts.get(1))
				|| (shifts.get(1) >= shifts.get(2) && shifts.get(1) <= shifts.get(3))
				|| (shifts.get(0) >= shifts.get(2) && shifts.get(0) <= shifts.get(3));
	}
}
