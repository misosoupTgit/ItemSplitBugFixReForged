package com.github.misosouptgit.itemsplitbugfixreforged;

import com.github.misosouptgit.itemsplitbugfixreforged.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ItemSplitBugFixReForged {
	public static final String MOD_ID = "itemsplitbugfixreforged";
	public static final Logger LOGGER = LogManager.getLogger("ItemSplitBugFixReForged");

	private static boolean initialized;

	private ItemSplitBugFixReForged() {}

	public static void init() {
		if (initialized) return;
		initialized = true;
		ModConfig.load();
		LOGGER.info("ItemSplitBugFix: ReForged initialized");
	}
}
