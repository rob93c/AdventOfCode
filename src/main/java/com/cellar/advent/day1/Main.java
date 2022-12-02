package com.cellar.advent.day1;

import static java.util.Comparator.reverseOrder;

import com.cellar.advent.utils.AdventUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://adventofcode.com/2022/day/1">Advent of Code day 1</a>
 */
public class Main {

	public static void main(String[] args) {
		var lines = AdventUtils.getFileLines("calories.txt");

		List<Long> calories = new ArrayList<>();
		long newCalories = 0L;

		for (String line : lines) {
			if (line.isBlank()) {
				calories.add(newCalories);
				newCalories = 0L;
			} else {
				newCalories += Long.parseLong(line);
			}
		}

		System.out.println("Max calories: " + calories.stream().max(Long::compare).orElse(0L));
		System.out.println("Top 3 total: " + calories.stream().sorted(reverseOrder()).limit(3).mapToLong(Long::valueOf).sum());
	}
}
