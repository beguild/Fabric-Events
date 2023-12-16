package dev.frydae.fabric.mixins;

import dev.frydae.fabric.events.container.JukeboxPlaceEvent;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MusicDiscItem.class)
public class MusicDiscItemMixin {
    @Inject(
            method = "useOnBlock",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/JukeboxBlockEntity;setStack(Lnet/minecraft/item/ItemStack;)V"),
            cancellable = true
    )
    public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity playerEntity = context.getPlayer();
        BlockEntity blockEntity = world.getBlockEntity(blockPos);
        ItemStack itemStack = context.getStack();

        if (playerEntity instanceof ServerPlayerEntity serverPlayer && blockEntity instanceof JukeboxBlockEntity jukeboxBlockEntity) {
            JukeboxPlaceEvent event = new JukeboxPlaceEvent(serverPlayer, jukeboxBlockEntity, itemStack);

            event.callEvent();

            if (event.isCancelled()) {
                cir.setReturnValue(ActionResult.CONSUME);
            }
        }
    }
}
