package com.cellar.advent.day5;

import com.cellar.advent.utils.AdventUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://adventofcode.com/2022/day/5">Advent of Code day 5</a>
 */
public class Main {

	public static void main(String[] args) {
		var lines = AdventUtils.getFileLines("stacks-and-rearrangement-procedures.txt");

		var crates = getInitialStacks(lines.stream().limit(8).toList());
		var cratesPart2 = getInitialStacks(lines.stream().limit(8).toList());
		var instructions = lines.stream().skip(10).toList();

		for (String instruction : instructions) {
			move(crates, new Movement(instruction), true);
		}

		AdventUtils.print("Part 1: ");
		crates.forEach(arr -> System.out.print(arr[0]));

		for (String instruction : instructions) {
			move(cratesPart2, new Movement(instruction), false);
		}

		AdventUtils.print("Part 2: ");
		cratesPart2.forEach(arr -> System.out.print(arr[0]));
	}

	private static List<char[]> getInitialStacks(List<String> formattedCrates) {
		List<char[]> crates = new ArrayList<>();
		char[] firstStack = new char[56];
		char[] secondStack = new char[56];
		char[] thirdStack = new char[56];
		char[] fourthStack = new char[56];
		char[] fifthStack = new char[56];
		char[] sixthStack = new char[56];
		char[] seventhStack = new char[56];
		char[] eighthStack = new char[56];
		char[] ninthStack = new char[56];

		for (int i = 0; i < formattedCrates.size(); i++) {
			var chars = formattedCrates.get(i).toCharArray();
			int length = chars.length;

			if (length > 1 && Character.isLetter(chars[1])) firstStack[i] = chars[1];
			if (length > 5 && Character.isLetter(chars[5])) secondStack[i] = chars[5];
			if (length > 9 && Character.isLetter(chars[9])) thirdStack[i] = chars[9];
			if (length > 13 && Character.isLetter(chars[13])) fourthStack[i] = chars[13];
			if (length > 17 && Character.isLetter(chars[17])) fifthStack[i] = chars[17];
			if (length > 21 && Character.isLetter(chars[21])) sixthStack[i] = chars[21];
			if (length > 25 && Character.isLetter(chars[25])) seventhStack[i] = chars[25];
			if (length > 29 && Character.isLetter(chars[29])) eighthStack[i] = chars[29];
			if (length > 33 && Character.isLetter(chars[33])) ninthStack[i] = chars[33];
		}

		CollectionUtils.addAll(crates, firstStack, secondStack, thirdStack, fourthStack, fifthStack, sixthStack, seventhStack, eighthStack, ninthStack);
		crates.replaceAll(array -> ArrayUtils.removeAllOccurrences(array, '\u0000'));

		return crates;
	}

	private record Movement(String instruction) {

		private int getMovedBoxes() {
			return Integer.parseInt(StringUtils.substringBetween(instruction, "move ", " from "));
		}

		private int getInitialPosition() {
			return Integer.parseInt(StringUtils.substringBetween(instruction, " from ", " to "));
		}

		private int getTargetPosition() {
			return Integer.parseInt(StringUtils.substringAfter(instruction, " to "));
		}
	}

	private static void move(List<char[]> crates, Movement movement, boolean moveSingleCrate) {
		var cratesToMove = new char[movement.getMovedBoxes()];
		var originalStack = crates.get(movement.getInitialPosition() - 1);

		System.arraycopy(originalStack, 0, cratesToMove, 0, movement.getMovedBoxes());

		if (moveSingleCrate) ArrayUtils.reverse(cratesToMove);

		var newInitialStack = ArrayUtils.removeElements(originalStack, cratesToMove);
		crates.set(movement.getInitialPosition() - 1, newInitialStack);

		var newDestinationStack = ArrayUtils.insert(0, crates.get(movement.getTargetPosition() - 1), cratesToMove);
		crates.set(movement.getTargetPosition() - 1, newDestinationStack);
	}

}
