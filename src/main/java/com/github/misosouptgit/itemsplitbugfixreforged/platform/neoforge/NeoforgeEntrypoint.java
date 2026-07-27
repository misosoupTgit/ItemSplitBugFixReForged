package com.github.misosouptgit.itemsplitbugfixreforged.platform.neoforge;

//? neoforge {
import com.github.misosouptgit.itemsplitbugfixreforged.ItemSplitBugFixReForged;
import net.neoforged.fml.common.Mod;

@Mod(ItemSplitBugFixReForged.MOD_ID)
public class NeoforgeEntrypoint {
	public NeoforgeEntrypoint() {
		ItemSplitBugFixReForged.init();
	}
}
//?}
