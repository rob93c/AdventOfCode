package com.cellar.advent.day3;

import com.cellar.advent.utils.AdventUtils;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

/**
 * @see <a href="https://adventofcode.com/2022/day/3">Advent of Code day 3</a>
 */
public class Main {

	public static void main(String[] args) {
		var lines = AdventUtils.getFileLines("rucksacks.txt");

		int part1Priorities = 0;

		for (String line : lines) {
			part1Priorities += new Rucksack(line).getSharedItemValue();
		}

		System.out.println("\nPart 1 - Total priorities: " + part1Priorities);

		int part2Priorities = 0;
		for (int i = 0; i < lines.size(); i += 3) {
			var groups = List.of(lines.get(i), lines.get(i + 1), lines.get(i + 2));
			part2Priorities += new RucksackPart2(groups).getSharedItemValue();
		}

		System.out.println("\nPart 2 - Total priorities: " + part2Priorities);
	}

	private record Rucksack(String line) {

		int getSharedItemValue() {
			int half = line.length() / 2;
			final String left = line.substring(0, half);
			final String right = line.substring(half);

			var noRepetitionRucksack = new HashSet<Character>();

			getCharStream(left).forEach(noRepetitionRucksack::add);

			return getCharStream(right)
					.filter(noRepetitionRucksack::contains)
					.findFirst()
					.map(Rucksack::getCharValue)
					.orElse(0);
		}

		private static int getCharValue(char c) {
			return Character.isLowerCase(c) ? c - 96 : c - 64 + 26;
		}
	}

	private static Stream<Character> getCharStream(String line) {
		return line.chars().mapToObj(c -> (char) c);
	}

	private record RucksackPart2(List<String> groups) {

		int getSharedItemValue() {
			var noRepetitionRucksack = new HashSet<Character>();
			var repeatedChars = new HashSet<Character>();

			getCharStream(groups.get(0)).forEach(noRepetitionRucksack::add);

			getCharStream(groups.get(1)).filter(noRepetitionRucksack::contains).forEach(repeatedChars::add);

			return getCharStream(groups.get(2))
					.filter(repeatedChars::contains)
					.findFirst()
					.map(Rucksack::getCharValue)
					.orElse(0);
		}
	}

}
