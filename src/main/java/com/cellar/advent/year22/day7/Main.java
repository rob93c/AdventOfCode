package com.cellar.advent.year22.day7;

import com.cellar.advent.utils.AdventUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @see <a href="https://adventofcode.com/2022/day/7">Advent of Code day 7</a>
 */
public class Main {

	private static final int MAX_SIZE = 100_000;
	private static final int AVAILABLE_STORAGE = 70_000_000;
	private static final int NEEDED_SPACE = 30_000_000;

	public static void main(String[] args) {
		var consoleOutput = AdventUtils.getFileLines("directories.txt");

		List<ConsoleDirectory> directories = new ArrayList<>();

		ConsoleDirectory rootDirectory = new ConsoleDirectory(null, "/");
		ConsoleDirectory currentDirectory = rootDirectory;
		for (String line : consoleOutput) {
			if (line.startsWith("$ cd ")) {
				currentDirectory = changeDirectory(currentDirectory, line);
			} else if (line.startsWith("dir ")) {
				ConsoleDirectory directory = new ConsoleDirectory(currentDirectory, StringUtils.substringAfter(line, " "));

				currentDirectory.addEntry(directory);
				directories.add(directory);
			} else if (!"$ ls".equals(line)) {
				String fileName = StringUtils.substringAfter(line, " ");
				int fileSize = Integer.parseInt(StringUtils.substringBefore(line, " "));

				ConsoleFile file = new ConsoleFile(fileName, fileSize);
				currentDirectory.addEntry(file);
			}
		}

		/*
		Unneeded for part 2

		directories.removeIf(dir -> dir.getSize() > MAX_SIZE);

		AdventUtils.print("Part 1 - Total size: " + totalSize);
		 */

		int sizeToFree = NEEDED_SPACE - AVAILABLE_STORAGE - rootDirectory.getSize();

		directories.removeIf(dir -> dir.getSize() < sizeToFree);
		var folderSizeToDelete = directories.stream().mapToInt(ConsoleDirectory::getSize).min().orElse(0);

		AdventUtils.print("Part 2 - Total size: " + folderSizeToDelete);
	}

	private static ConsoleDirectory changeDirectory(ConsoleDirectory currentDirectory, String command) {
		if ("$ cd /".equals(command)) {
			return currentDirectory;
		} else if (command.endsWith("..")) {
			return currentDirectory.getParent();
		} else {
			return currentDirectory.findDirectoryByName(StringUtils.substringAfterLast(command, " "));
		}
	}

	private static class ConsoleFile {

		private final String name;
		private final int size;

		ConsoleFile(String name, int size) {
			this.name = name;
			this.size = size;
		}

		String getName() {
			return name;
		}

		int getSize() {
			return size;
		}
	}

	private static class ConsoleDirectory extends ConsoleFile {
		private final ConsoleDirectory parent;
		private final Map<String, ConsoleFile> content = new HashMap<>();

		ConsoleDirectory(ConsoleDirectory parent, String name) {
			super(name, 0);
			this.parent = parent;
		}

		ConsoleDirectory getParent() {
			return parent;
		}

		ConsoleDirectory findDirectoryByName(String name) {
			return (ConsoleDirectory) content.get(name);
		}

		<T extends ConsoleFile> void addEntry(T newEntry) {
			content.put(newEntry.getName(), newEntry);
		}

		@Override
		int getSize() {
			int totalSize = 0;
			for (ConsoleFile entry : content.values()) {
				if (entry instanceof ConsoleDirectory directory) {
					totalSize += directory.getSize();
				} else {
					totalSize += entry.getSize();
				}
			}
			return totalSize;
		}
	}

}
