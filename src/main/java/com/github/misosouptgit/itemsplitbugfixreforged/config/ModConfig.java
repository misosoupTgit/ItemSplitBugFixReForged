package com.github.misosouptgit.itemsplitbugfixreforged.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Shared common config across Fabric / Forge / NeoForge.
 * Stored as {@code config/itemsplitbugfixreforged-common.toml}.
 */
public final class ModConfig {
	private static final Logger LOGGER = LogManager.getLogger("ItemSplitBugFixReForged/Config");

	private static final List<String> DEFAULT_BLACKLIST = Arrays.asList(
			"alexscaves:sack_of_sating",
			"hexerei:*",
			"spelunkery:*",
			"minecraft:spyglass",
			"technicalcores:*"
	);

	private static List<String> blacklist = new ArrayList<>(DEFAULT_BLACKLIST);

	private ModConfig() {}

	public static List<String> getBlacklist() {
		return Collections.unmodifiableList(blacklist);
	}

	public static void load() {
		Path path = Paths.get("config", "itemsplitbugfixreforged-common.toml");
		try {
			if (!Files.exists(path)) {
				Files.createDirectories(path.getParent());
				writeDefaults(path);
				blacklist = new ArrayList<>(DEFAULT_BLACKLIST);
				return;
			}
			blacklist = parseBlacklist(path);
		} catch (Throwable t) {
			LOGGER.warn("Failed to load config; using defaults.", t);
			blacklist = new ArrayList<>(DEFAULT_BLACKLIST);
		}
	}

	private static void writeDefaults(Path path) throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			writer.write("# ItemSplitBugFix: ReForged\n");
			writer.write("# Items in this list will not be modified. Supports wildcard '*'.\n");
			writer.write("# Examples:\n");
			writer.write("#   - 'minecraft:diamond'\n");
			writer.write("#   - '*:diamond*'\n");
			writer.write("#   - 'minecraft:*'\n");
			writer.write("\n");
			writer.write("[blacklist_settings]\n");
			writer.write("blacklist = [\n");
			for (int i = 0; i < DEFAULT_BLACKLIST.size(); i++) {
				writer.write("    \"" + DEFAULT_BLACKLIST.get(i) + "\"");
				if (i < DEFAULT_BLACKLIST.size() - 1) writer.write(",");
				writer.write("\n");
			}
			writer.write("]\n");
		}
	}

	private static List<String> parseBlacklist(Path path) throws IOException {
		List<String> result = new ArrayList<>();
		boolean inArray = false;
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty() || line.startsWith("#") || line.startsWith("[")) {
					continue;
				}
				if (line.startsWith("blacklist")) {
					int bracket = line.indexOf('[');
					if (bracket >= 0) {
						inArray = true;
						String rest = line.substring(bracket + 1).trim();
						if (!rest.isEmpty() && !rest.equals("]")) {
							collectEntries(rest, result);
						}
						if (line.contains("]")) {
							inArray = false;
						}
					}
					continue;
				}
				if (inArray) {
					if (line.contains("]")) {
						String before = line.substring(0, line.indexOf(']')).trim();
						if (!before.isEmpty()) collectEntries(before, result);
						inArray = false;
					} else {
						collectEntries(line, result);
					}
				}
			}
		}
		return result.isEmpty() ? new ArrayList<>(DEFAULT_BLACKLIST) : result;
	}

	private static void collectEntries(String raw, List<String> out) {
		for (String part : raw.split(",")) {
			String v = part.trim();
			if (v.endsWith(",")) v = v.substring(0, v.length() - 1).trim();
			if (v.startsWith("\"") && v.endsWith("\"") && v.length() >= 2) {
				v = v.substring(1, v.length() - 1);
			}
			if (!v.isEmpty() && !v.equals("]")) {
				out.add(v);
			}
		}
	}
}
