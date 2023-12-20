package dev.frydae.fabric.events.block;

import dev.frydae.beguild.utils.Location;
import dev.frydae.fabric.events.Cancellable;
import dev.frydae.fabric.events.Event;
import dev.frydae.fabric.events.block.extinguish.BlockExtinguishEvent;
import dev.frydae.fabric.events.block.extinguish.CampfireBlockExtinguishEvent;
import dev.frydae.fabric.events.block.extinguish.CandleBlockExtinguishEvent;
import dev.frydae.fabric.events.block.extinguish.CandleCakeBlockExtinguishEvent;
import dev.frydae.fabric.events.player.bucket.PlayerBucketEmptyEvent;
import dev.frydae.fabric.events.player.bucket.PlayerWaterlogBlockEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Unique;

public final class BlockEvents {
    private static boolean callEvent(Event event) {
        event.callEvent();

        if (event instanceof Cancellable) {
            return !((Cancellable) event).isCancelled();
        }

        return true;
    }

    @Unique
    public static boolean callBucketEmptyEvent(ServerPlayerEntity player, Block block, ItemStack bucket, Hand hand) {
        return callEvent(new PlayerBucketEmptyEvent(player, block, bucket.getItem(), bucket, hand));
    }

    @Unique
    public static boolean callWaterlogEvent(ServerPlayerEntity player, Location location) {
        return callEvent(new PlayerWaterlogBlockEvent(player, location));
    }

    @Unique
    public static boolean callCampfireExtinguishEvent(ServerPlayerEntity player, Location location) {
        return callEvent(new CampfireBlockExtinguishEvent(player, location));
    }

    @Unique
    public static boolean callCandleCakeBlockExtinguishEvent(ServerPlayerEntity player, Location location) {
        return callEvent(new CandleCakeBlockExtinguishEvent(player, location));
    }

    @Unique
    public static boolean callBreakBlockEvent(ServerPlayerEntity player, ServerWorld world, BlockPos pos, BlockState blockState) {
        return callEvent(new PlayerBreakBlockEvent(player, new Location(world, pos), blockState));
    }

    @Unique
    public static boolean callPlaceBlockEvent(ServerPlayerEntity player, World world, BlockPos blockPos, BlockState blockState, ItemStack stack) {
        return callEvent(new PlayerPlaceBlockEvent(player, new Location(world, blockPos), blockState, stack));
    }

    @Unique
    public static boolean callExtinguishEvent(ServerPlayerEntity player, Location location) {
        return callEvent(new BlockExtinguishEvent(player, location));
    }

    public static boolean callCandleBlockExtinguishEvent(ServerPlayerEntity player, Location location) {
        return callEvent(new CandleBlockExtinguishEvent(player, location));
    }
}
