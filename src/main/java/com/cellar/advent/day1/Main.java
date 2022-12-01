package com.cellar.advent.day1;

import static java.util.Comparator.reverseOrder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException, URISyntaxException {
		var file = Main.class.getClassLoader().getResource("calories.txt");
		var lines = Files.readAllLines(Path.of(file.toURI()));

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
