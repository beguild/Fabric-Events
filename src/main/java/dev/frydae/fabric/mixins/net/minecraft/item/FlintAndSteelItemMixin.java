package dev.frydae.fabric.mixins.net.minecraft.item;

import com.llamalad7.mixinextras.sugar.Local;
import dev.frydae.beguild.utils.Location;
import dev.frydae.fabric.events.block.BlockUtils;
import dev.frydae.fabric.events.block.ignite.BlockIgniteEvent;
import dev.frydae.fabric.events.block.ignite.CampfireBlockIgniteEvent;
import dev.frydae.fabric.events.block.ignite.CandleBlockIgniteEvent;
import dev.frydae.fabric.events.block.ignite.CandleCakeBlockIgniteEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(FlintAndSteelItem.class)
public class FlintAndSteelItemMixin {
    @Inject(
            method = "useOnBlock",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V"),
            cancellable = true
    )
    public void useOnBlockSpecial(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, @Local BlockPos blockPos, @Local BlockState blockState) {
        BlockIgniteEvent event = null;
        World world = context.getWorld();

        if (context.getPlayer() instanceof ServerPlayerEntity player) {
            if (BlockUtils.isCandle(blockState)) {
                event = new CandleBlockIgniteEvent(player, new Location(world, blockPos));
            } else if (blockState.isOf(Blocks.CAMPFIRE) || blockState.isOf(Blocks.SOUL_CAMPFIRE)) {
                event = new CampfireBlockIgniteEvent(player, new Location(world, blockPos));
            } else if (BlockUtils.isCandleCake(blockState)) {
                event = new CandleCakeBlockIgniteEvent(player, new Location(world, blockPos));
            }

            if (event != null) {
                event.callEvent();

                if (event.isCancelled()) {
                    cir.setReturnValue(ActionResult.FAIL);
                }
            }
        }
    }

    @Inject(
            method = "useOnBlock",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V", ordinal = 1),
            cancellable = true
    )
    public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, @Local(ordinal = 0) BlockPos blockPos, @Local(ordinal = 0) BlockState blockState) {
        if (context.getWorld().isClient()) {
            return;
        }

        BlockIgniteEvent event = new BlockIgniteEvent((ServerPlayerEntity) context.getPlayer(), new Location(context.getWorld(), blockPos));

        event.callEvent();

        if (event.isCancelled()) {
            cir.setReturnValue(ActionResult.FAIL);
        }
    }
}
