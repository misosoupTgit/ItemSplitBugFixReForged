package com.github.misosouptgit.itemsplitbugfixreforged.mixin;

import com.github.misosouptgit.itemsplitbugfixreforged.ItemSplitFix;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	@Inject(method = "split", at = @At("HEAD"))
	private void itemsplitbugfix$split(CallbackInfoReturnable<ItemStack> cir) {
		ItemSplitFix.fixBug((ItemStack) (Object) this);
	}

	@Inject(method = "copy", at = @At("HEAD"))
	private void itemsplitbugfix$copy(CallbackInfoReturnable<ItemStack> cir) {
		ItemSplitFix.fixBug((ItemStack) (Object) this);
	}

	//? if >=1.20.5 {
	@Inject(method = "isSameItemSameComponents", at = @At("HEAD"))
	private static void itemsplitbugfix$isSameItemSameComponents(ItemStack stack1, ItemStack stack2, CallbackInfoReturnable<Boolean> cir) {
		ItemSplitFix.fixBug(stack1);
		ItemSplitFix.fixBug(stack2);
	}
	//?} else {
	/*@Inject(method = "isSameItemSameTags", at = @At("HEAD"))
	private static void itemsplitbugfix$isSameItemSameTags(ItemStack stack1, ItemStack stack2, CallbackInfoReturnable<Boolean> cir) {
		ItemSplitFix.fixBug(stack1);
		ItemSplitFix.fixBug(stack2);
	}
	*///?}

	@Inject(method = "isSameItem", at = @At("HEAD"))
	private static void itemsplitbugfix$isSameItem(ItemStack stack1, ItemStack stack2, CallbackInfoReturnable<Boolean> cir) {
		ItemSplitFix.fixBug(stack1);
		ItemSplitFix.fixBug(stack2);
	}

	@Inject(method = "getCount", at = @At("HEAD"))
	private void itemsplitbugfix$getCount(CallbackInfoReturnable<Integer> cir) {
		ItemSplitFix.fixBug((ItemStack) (Object) this);
	}

	@Inject(method = "setCount", at = @At("HEAD"))
	private void itemsplitbugfix$setCount(int count, CallbackInfo ci) {
		ItemSplitFix.fixBug((ItemStack) (Object) this);
	}
}
