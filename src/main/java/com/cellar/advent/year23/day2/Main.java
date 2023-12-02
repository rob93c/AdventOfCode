package com.cellar.advent.year23.day2;

import com.cellar.advent.utils.AdventUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @see <a href="https://adventofcode.com/2023/day/2">Advent of Code day 2</a>
 */
public class Main {

    private static final Pattern COLOR_PATTERN = Pattern.compile("(\\d+) (red|green|blue)");

    public static void main(String[] args) {
        var lines = AdventUtils.getFileLines("cubes.txt");

        part1(lines);
        part2(lines);
    }

    private static void part1(List<String> lines) {
        int sumIds = 0;

        for (var line : lines) {
            var game = new Game(line);

            if (isPossible(game)) {
                sumIds += game.retrieveId();
            }
        }

        AdventUtils.print("Sum of the possible games: " + sumIds);
    }

    private static void part2(List<String> lines) {
        int totalPower = 0;

        for (var line : lines) {
            var game = new Game(line);

            totalPower += getPower(game.getSets());
        }

        AdventUtils.print("Sum of the power: " + totalPower);
    }

    private static boolean isPossible(Game game) {
        boolean isPossible = true;
        var sets = game.getSets();
        for (var colors : sets) {
            isPossible &= colors.get("red") <= 12 && colors.get("green") <= 13 && colors.get("blue") <= 14;
        }

        return isPossible;
    }

    private record Game(String game) {
        private int retrieveId() {
            return Integer.parseInt(StringUtils.substringBetween(game, "Game ", ":"));
        }

        private List<Map<String, Integer>> getSets() {
            var setArray = StringUtils.substringAfter(game, ": ").split("; ");

            return Arrays.stream(setArray)
                    .map(this::getColors)
                    .filter(Objects::nonNull)
                    .toList();
        }

        private Map<String, Integer> getColors(String set) {
            var colors = HashMap.<String, Integer>newHashMap(3);
            colors.put("red", 0);
            colors.put("green", 0);
            colors.put("blue", 0);

            var matcher = COLOR_PATTERN.matcher(set);

            while (matcher.find()) {
                colors.put(matcher.group(2), Integer.parseInt(matcher.group(1)));
            }

            /*
            Active only for part 1
            if (colors.values().stream().mapToInt(integer -> integer).sum() > 39) {
                return null;
            }
            */

            return colors;
        }
    }

    private static int getPower(List<Map<String, Integer>> sets) {
        int maxRed = 1;
        int maxGreen = 1;
        int maxBlue = 1;

        for (var colors : sets) {
            maxRed = Math.max(maxRed, colors.get("red"));
            maxGreen = Math.max(maxGreen, colors.get("green"));
            maxBlue = Math.max(maxBlue, colors.get("blue"));
        }

        return maxRed * maxGreen * maxBlue;
    }
}
