package com.github.misosouptgit.itemsplitbugfixreforged.mixin;

import com.github.misosouptgit.itemsplitbugfixreforged.ItemSplitFix;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if >=1.20.5 {
import net.minecraft.core.component.DataComponents;
//?}

@Mixin(Slot.class)
public abstract class SlotMixin {
	@Shadow
	@Final
	public Container container;

	//? if >=1.17.1 {
	@Shadow
	public abstract int getContainerSlot();
	//?} else {
	/*@Shadow
	@Final
	private int slot;
	*///?}

	@Inject(method = "getItem", at = @At("HEAD"), cancellable = true)
	private void itemsplitbugfix$getItem(CallbackInfoReturnable<ItemStack> cir) {
		if (this.container == null) return;
		//? if >=1.17.1 {
		ItemStack stack = this.container.getItem(this.getContainerSlot());
		//?} else {
		/*ItemStack stack = this.container.getItem(this.slot);
		*///?}
		if (!ItemSplitFix.isSplitItemStack(stack)) return;
		//? if >=1.20.5 {
		stack.remove(DataComponents.CUSTOM_DATA);
		//?} else {
		/*stack.setTag(null);
		*///?}
		cir.setReturnValue(stack);
	}

	@Inject(method = "set", at = @At("HEAD"))
	private void itemsplitbugfix$set(ItemStack stack, CallbackInfo ci) {
		ItemSplitFix.fixBug(stack);
	}

	@Inject(method = "onQuickCraft", at = @At("HEAD"))
	private void itemsplitbugfix$onQuickCraft(ItemStack stack1, ItemStack stack2, CallbackInfo ci) {
		ItemSplitFix.fixBug(stack1);
		ItemSplitFix.fixBug(stack2);
	}

	@Inject(method = "onTake", at = @At("HEAD"))
	private void itemsplitbugfix$onTake(Player player, ItemStack stack, CallbackInfo ci) {
		ItemSplitFix.fixBug(stack);
	}

	@Inject(method = "mayPlace", at = @At("HEAD"))
	private void itemsplitbugfix$mayPlace(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		ItemSplitFix.fixBug(stack);
	}

	//? if >=1.19.2 {
	@Inject(method = "setByPlayer", at = @At("HEAD"))
	private void itemsplitbugfix$setByPlayer(ItemStack stack, CallbackInfo ci) {
		ItemSplitFix.fixBug(stack);
	}
	//?}
}
