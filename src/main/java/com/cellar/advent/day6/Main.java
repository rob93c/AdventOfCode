package com.cellar.advent.day6;

import static java.util.HashSet.newHashSet;

import com.cellar.advent.utils.AdventUtils;

import java.security.InvalidParameterException;

/**
 * @see <a href="https://adventofcode.com/2022/day/6">Advent of Code day 6</a>
 */
public class Main {

	public static void main(String[] args) {
		var dataStream = AdventUtils.getFirstLine("data-stream.txt");

		AdventUtils.print("Part 1 - Marker received at " + findMarkerIndexOfSize(dataStream, 4));

		AdventUtils.print("Part 2 - Marker received at " + findMarkerIndexOfSize(dataStream, 14));
	}

	private static int findMarkerIndexOfSize(String dataStream, int expectedSize) {
		String marker;

		for (int i = 0; i < dataStream.length(); i++) {
			marker = dataStream.substring(i, i + expectedSize);
			if (isValidMarker(marker, expectedSize)) {
				return i + expectedSize;
			}
		}

		throw new InvalidParameterException("data stream didn't contain valid markers of size " + expectedSize);
	}

	private static boolean isValidMarker(String marker, int expectedSize) {
		var uniqueChars = newHashSet(expectedSize);
		marker.chars().forEach(uniqueChars::add);

		return uniqueChars.size() == expectedSize;
	}
}
