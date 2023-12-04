package com.cellar.advent.year23.day4;

import com.cellar.advent.utils.AdventUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Collections.nCopies;
import static java.util.function.Predicate.not;

/**
 * @see <a href="https://adventofcode.com/2023/day/4">Advent of Code day 4</a>
 */
public class Main {

    private static final Pattern ROW_PATTERN = Pattern.compile("Card\\s+(.*):\\s+(.*)\\s+\\|\\s+(.*)");

    public static void main(String[] args) {
        var lines = AdventUtils.getFileLines("scratchcards.txt");

        part1(lines);
        part2(lines);
    }

    private static void part1(List<String> lines) {
        double points = 0;

        for (var line : lines) {
            var matcher = ROW_PATTERN.matcher(line);

            if (matcher.matches()) {
                var winningNumbers = toList(matcher.group(2));
                var myNumbers = toList(matcher.group(3));

                if (winningNumbers.stream().noneMatch(myNumbers::contains)) {
                    continue;
                }

                int myWinningNumbers = 0;
                for (Integer myNumber : myNumbers) {
                    if (winningNumbers.contains(myNumber)) {
                        myWinningNumbers++;
                    }
                }

                points += Math.pow(2, myWinningNumbers - 1);
            }
        }

        AdventUtils.print("Total scratchcards points: " + points);
    }

    private static void part2(List<String> lines) {
        var scratchcards = new ArrayList<Scratchcard>();

        for (var line : lines) {
            var matcher = ROW_PATTERN.matcher(line);

            if (matcher.matches()) {
                int scratchcardId = Integer.parseInt(matcher.group(1));
                var winningNumbers = toList(matcher.group(2));
                var myNumbers = toList(matcher.group(3));

                var scratchcard = new Scratchcard(scratchcardId, winningNumbers, myNumbers);
                scratchcards.add(scratchcard);
            }
        }

        var winningScratchcards = new ArrayList<Scratchcard>();

        for (var scratchcard : scratchcards) {
            winningScratchcards.add(scratchcard);
            var matches = scratchcard.countMatches();
            int copies = countCopies(winningScratchcards, scratchcard.id());

            for (int i = 0; i < matches; i++) {
                winningScratchcards.addAll(nCopies(copies, scratchcards.get(scratchcard.id + i)));
            }
        }

        AdventUtils.print("Total scratchcards: " + winningScratchcards.size());
    }

    private static List<Integer> toList(String numbers) {
        return Arrays.stream(numbers.split(" "))
                .filter(not(String::isBlank))
                .map(Integer::valueOf)
                .toList();
    }

    private static int countCopies(List<Scratchcard> scratchcards, int id) {
        return scratchcards.stream()
                .filter(s -> s.id() == id)
                .toList()
                .size();
    }

    private record Scratchcard(int id, List<Integer> winningNumbers, List<Integer> myNumbers) {
        private long countMatches() {
            return myNumbers.stream()
                    .filter(winningNumbers::contains)
                    .count();
        }
    }
}
