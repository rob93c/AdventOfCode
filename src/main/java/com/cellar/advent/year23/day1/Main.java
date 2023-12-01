package com.cellar.advent.year23.day1;

import com.cellar.advent.utils.AdventUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://adventofcode.com/2023/day/1">Advent of Code day 1</a>
 */
public class Main {

	public static void main(String[] args) {
		var lines = AdventUtils.getFileLines("calibrations.txt");

		part1(lines);
		part2(lines);
	}

	private static void part1(List<String> lines) {
		var calibrations = new ArrayList<Integer>();

		for (var line : lines) {
			var digits = line.chars().filter(Character::isDigit).mapToObj(c -> String.valueOf((char) c)).toList();
			calibrations.add(Integer.valueOf(digits.getFirst() + digits.getLast()));
		}

		AdventUtils.print("Total calibrations: " + calibrations.stream().mapToInt(Integer::intValue).sum());
	}

	private static void part2(List<String> lines) {
		var calibrations = new ArrayList<Integer>();

		for (var line : lines) {
			var digits = new ArrayList<String>();
			var tempValue = new StringBuilder();
			for (char c : line.toCharArray()) {
				if (Character.isDigit(c)) {
					if (!tempValue.isEmpty() && NumberUtils.isDigits(Number.getValueByLabel(tempValue.toString()))) {
						digits.add(Number.getValueByLabel(tempValue.toString()));
						tempValue.delete(0, tempValue.length());
					}

					digits.add(String.valueOf(c));
				} else {
					tempValue.append(c);

					if (NumberUtils.isDigits(Number.getValueByLabel(tempValue.toString()))) {
						digits.add(Number.getValueByLabel(tempValue.toString()));
						tempValue.delete(0, tempValue.length() - 1);
					}
				}
			}

			calibrations.add(Integer.valueOf(digits.getFirst() + digits.getLast()));
		}

		AdventUtils.print("Total calibrations: " + calibrations.stream().mapToInt(Integer::intValue).sum());
	}

	private enum Number {
		ONE("one", "1"),
		TWO("two", "2"),
		THREE("three", "3"),
		FOUR("four", "4"),
		FIVE("five", "5"),
		SIX("six", "6"),
		SEVEN("seven", "7"),
		EIGHT("eight", "8"),
		NINE("nine", "9");

		private final String label;
		private final String value;

		Number(String label, String value) {
			this.label = label;
			this.value = value;
		}

		private static String getValueByLabel(String label) {
			for (var number : Number.values()) {
				if (label.contains(number.label)) {
					return number.value;
				}
			}

			return label;
		}
	}
}
