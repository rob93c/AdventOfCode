package com.cellar.advent.day3;

import com.cellar.advent.utils.AdventUtils;

import java.util.HashSet;
import java.util.List;

/**
 * @see <a href="https://adventofcode.com/2022/day/3">Advent of Code day 3</a>
 */
public class Main {

	public static void main(String[] args) {
		var lines = AdventUtils.getFileLines("rucksacks.txt");

		int totalPriorities = 0;

		for (String line : lines) {
			int half = line.length() / 2;
			totalPriorities += new Rucksack(line.substring(0, half), line.substring(half)).getSharedItemValue();
		}

		System.out.println("\nPart 1 - Total priorities: " + totalPriorities);

		int part2Priorities = 0;
		for (int i = 0; i < lines.size(); i += 3) {
			var groups = List.of(lines.get(i), lines.get(i + 1), lines.get(i + 2));
			part2Priorities += new RucksackPart2(groups).getSharedItemValue();
		}

		System.out.println("\nPart 2 - Total priorities: " + part2Priorities);
	}

	private record Rucksack(String left, String right) {

		int getSharedItemValue() {
			var noRepetitionRucksack = new HashSet<Character>();

			for (char c : left.toCharArray()) {
				noRepetitionRucksack.add(c);
			}

			for (char c : right.toCharArray()) {
				if (noRepetitionRucksack.contains(c)) {
					return getCharValue(c);
				}
			}

			return 0;
		}

		private static int getCharValue(char c) {
			return Character.isLowerCase(c) ? c - 96 : c - 64 + 26;
		}
	}

	private record RucksackPart2(List<String> groups) {

		int getSharedItemValue() {
			var noRepetitionRucksack = new HashSet<Character>();
			var repeatedChars = new HashSet<Character>();

			for (char c : groups.get(0).toCharArray()) {
				noRepetitionRucksack.add(c);
			}
			for (char c : groups.get(1).toCharArray()) {
				if (noRepetitionRucksack.contains(c)) repeatedChars.add(c);
			}

			for (char c : groups.get(2).toCharArray()) {
				if (repeatedChars.contains(c)) {
					return Rucksack.getCharValue(c);
				}
			}

			return 0;
		}
	}

}
