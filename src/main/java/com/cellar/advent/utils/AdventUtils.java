package com.cellar.advent.utils;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class AdventUtils {

	public static List<String> getFileLines(String resourceName) {
		var file = AdventUtils.class.getClassLoader().getResource(resourceName);

		try {
			return Files.readAllLines(Path.of(requireNonNull(file).toURI()));
		} catch (IOException | URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getFirstLine(String resourceName) {
		return getFileLines(resourceName).get(0);
	}

	public static void print(String text) {
		System.out.println("\n" + text);
	}

	private AdventUtils() {}
}
