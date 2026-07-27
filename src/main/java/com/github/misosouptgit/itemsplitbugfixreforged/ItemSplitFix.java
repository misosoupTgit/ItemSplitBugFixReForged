package com.github.misosouptgit.itemsplitbugfixreforged;

import com.github.misosouptgit.itemsplitbugfixreforged.config.ModConfig;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

//? if >=1.20.5 {
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
//?}

//? if >=1.19.3 {
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
//?} else {
/*import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
*///?}

public final class ItemSplitFix {
	private static final Logger LOGGER = LogManager.getLogger("ItemSplitBugFixReForged/Fix");

	private static final List<Pattern> blacklistPattern = new ArrayList<>();
	private static final Set<Item> blacklistCache = new HashSet<>();
	private static final Set<Item> checkedItemsCache = new HashSet<>();
	private static boolean prepared;

	private ItemSplitFix() {}

	private static String itemId(Item item) {
		//? if >=1.19.3 {
		ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
		//?} else {
		/*ResourceLocation id = Registry.ITEM.getKey(item);
		*///?}
		return id == null ? null : id.toString();
	}

	private static boolean isItemOnBlacklist(ItemStack stack) {
		if (stack == null || stack.isEmpty() || blacklistPattern.isEmpty()) {
			return true;
		}
		Item item = stack.getItem();
		if (blacklistCache.contains(item)) {
			return true;
		}
		if (checkedItemsCache.contains(item)) {
			return false;
		}
		String id = itemId(item);
		if (id == null) {
			return true;
		}
		prepareBlacklist();
		for (Pattern p : blacklistPattern) {
			if (p.matcher(id).matches()) {
				blacklistCache.add(item);
				return true;
			}
		}
		checkedItemsCache.add(item);
		return false;
	}

	public static boolean isSplitItemStack(ItemStack stack) {
		prepareBlacklist();
		if (isItemOnBlacklist(stack)) {
			return false;
		}
		//? if >=1.20.5 {
		CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
		return customData != null && customData.isEmpty();
		//?} else {
		/*return stack.hasTag() && stack.getTag() != null && stack.getTag().isEmpty();
		*///?}
	}

	public static void prepareBlacklist() {
		if (prepared) return;
		List<String> list = ModConfig.getBlacklist();
		blacklistPattern.clear();
		for (String s : list) {
			blacklistPattern.add(Pattern.compile(s.replace("*", ".*")));
		}
		blacklistCache.clear();
		checkedItemsCache.clear();
		prepared = true;
	}

	public static void reloadBlacklist() {
		prepared = false;
		prepareBlacklist();
		LOGGER.debug("Blacklist reloaded ({} patterns)", blacklistPattern.size());
	}

	public static void fixBug(ItemStack stack) {
		if (stack == null || stack.isEmpty() || isItemOnBlacklist(stack) || !isSplitItemStack(stack)) {
			return;
		}
		//? if >=1.20.5 {
		stack.remove(DataComponents.CUSTOM_DATA);
		//?} else {
		/*stack.setTag(null);
		*///?}
	}
}
