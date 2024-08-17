package dev.frydae.fabric.events.player;

import dev.frydae.fabric.events.Event;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class PlayerListFilterEvent extends Event {
    private final PlayerListS2CPacket packet;
    private final ServerPlayerEntity playerToSend;
    private final List<ServerPlayerEntity> playersToSendTo;

    public PlayerListFilterEvent(PlayerListS2CPacket packet, ServerPlayerEntity playerToSend, List<ServerPlayerEntity> playersToSendTo) {
        super();

        this.packet = packet;
        this.playerToSend = playerToSend;
        this.playersToSendTo = playersToSendTo;
    }

    public PlayerListS2CPacket getPacket() {
        return this.packet;
    }

    public ServerPlayerEntity getPlayerToSend() {
        return this.playerToSend;
    }

    public List<ServerPlayerEntity> getPlayersToSendTo() {
        return this.playersToSendTo;
    }

    public void removePlayer(ServerPlayerEntity player) {
        this.playersToSendTo.remove(player);
    }
}
