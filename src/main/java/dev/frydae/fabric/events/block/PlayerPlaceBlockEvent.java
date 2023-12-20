package dev.frydae.fabric.events.block;

import dev.frydae.beguild.utils.Location;
import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

@Getter
public class PlayerPlaceBlockEvent extends PlayerBlockEvent {
    private final ItemStack stack;

    public PlayerPlaceBlockEvent(ServerPlayerEntity player, Location location, BlockState blockState, ItemStack stack) {
        super(player, location, blockState);

        this.stack = stack;
    }
}
