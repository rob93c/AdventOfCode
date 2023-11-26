package com.cellar.advent.year22.day2;

import com.cellar.advent.utils.AdventUtils;

import java.util.Arrays;

/**
 * @see <a href="https://adventofcode.com/2022/day/2">Advent of Code day 2</a>
 */
public class Main {

	public static void main(String[] args) {
		var lines = AdventUtils.getFileLines("strategy-guide.txt");

		AdventUtils.print("Part 1 - Total score: " + lines.stream().mapToInt(Choice::computeChoiceValuePart1).sum());
		AdventUtils.print("Part 2 - Total score: " + lines.stream().mapToInt(Choice::computeChoiceValuePart2).sum());
	}

	private enum Choice {
		ROCK('A', 'X'),
		PAPER('B', 'Y'),
		SCISSOR('C', 'Z');

		private final char opponentPlay;
		private final char myPlay;

		Choice(char opponentPlay, char myPlay) {
			this.opponentPlay = opponentPlay;
			this.myPlay = myPlay;
		}

		static int computeChoiceValuePart1(final String play) {
			var opponent = findByOpponentPlay(play.charAt(0));
			var me = findByMyPlay(play.charAt(play.length() - 1));

			return new Score(opponent, me).getTotal();
		}

		private static Choice findByOpponentPlay(char opponentPlay) {
			return Arrays.stream(Choice.values()).filter(choice -> choice.getOpponentPlay() == opponentPlay).findFirst().orElse(null);
		}

		private static Choice findByMyPlay(char myPlay) {
			return Arrays.stream(Choice.values()).filter(choice -> choice.getMyPlay() == myPlay).findFirst().orElse(null);
		}

		static int computeChoiceValuePart2(String play) {
			var opponent = findByOpponentPlay(play.charAt(0));
			var me = findByExpectedOutcome(opponent, play.charAt(play.length() - 1));

			return new Score(opponent, me).getTotal();
		}

		private static Choice findByExpectedOutcome(Choice opponent, char expectedOutcome) {
			Choice myChoice;

			switch (expectedOutcome) {
				case 'X' -> myChoice = findLosingMove(opponent);
				case 'Y' -> myChoice = opponent;
				case 'Z' -> myChoice = findWinningMove(opponent);
				default -> myChoice = null;
			}

			return myChoice;
		}

		private static Choice findLosingMove(Choice opponent) {
			if (opponent == ROCK) {
				return SCISSOR;
			} else if (opponent == SCISSOR) {
				return PAPER;
			} else {
				return ROCK;
			}
		}

		private static Choice findWinningMove(Choice opponent) {
			if (opponent == ROCK) {
				return PAPER;
			} else if (opponent == SCISSOR) {
				return ROCK;
			} else {
				return SCISSOR;
			}
		}

		private char getOpponentPlay() {
			return opponentPlay;
		}

		private char getMyPlay() {
			return myPlay;
		}
	}

	private record Score(Choice opponentChoice, Choice myChoice) {

		public int getTotal() {
			int total = 0;

			if (iDraw()) total += 3;
			if (iWin()) total += 6;

			switch (myChoice) {
				case ROCK -> total += 1;
				case PAPER -> total += 2;
				case SCISSOR -> total += 3;
			}

			return total;
		}

		private boolean iDraw() {
			return opponentChoice == myChoice;
		}

		private boolean iWin() {
			return (opponentChoice == Choice.SCISSOR && myChoice == Choice.ROCK)
					|| (opponentChoice == Choice.ROCK && myChoice == Choice.PAPER)
					|| (opponentChoice == Choice.PAPER && myChoice == Choice.SCISSOR);
		}
	}
}
