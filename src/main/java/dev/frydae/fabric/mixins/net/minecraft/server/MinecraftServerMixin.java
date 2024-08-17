package dev.frydae.fabric.mixins.net.minecraft.server;

import dev.frydae.fabric.events.FabricEvents;
import dev.frydae.fabric.events.server.ServerLifecycleEvent;
import dev.frydae.fabric.events.server.ServerStartedEvent;
import dev.frydae.fabric.events.server.ServerStartingEvent;
import dev.frydae.fabric.events.server.ServerStoppedEvent;
import dev.frydae.fabric.events.server.ServerStoppingEvent;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;setupServer()Z"), method = "runServer")
    private void beforeSetupServer(CallbackInfo info) {
        FabricEvents.server = (MinecraftServer) (Object) this;
        fabricevents$callEvent(new ServerStartingEvent((MinecraftServer) (Object) this), true);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;createMetadata()Lnet/minecraft/server/ServerMetadata;", ordinal = 0), method = "runServer")
    private void afterSetupServer(CallbackInfo info) {
        fabricevents$callEvent(new ServerStartedEvent((MinecraftServer) (Object) this), true);
    }

    @Inject(at = @At("HEAD"), method = "shutdown")
    private void beforeShutdownServer(CallbackInfo info) {
        fabricevents$callEvent(new ServerStoppingEvent((MinecraftServer) (Object) this), false);
    }

    @Inject(at = @At("TAIL"), method = "shutdown")
    private void afterShutdownServer(CallbackInfo info) {
        fabricevents$callEvent(new ServerStoppedEvent((MinecraftServer) (Object) this), false);
    }

    @Unique
    private void fabricevents$callEvent(ServerLifecycleEvent event, boolean shouldCancel) {
        event.callEvent();

        if (event.isCancelled() && shouldCancel) {
            ((MinecraftServer) (Object) this).shutdown();
        }
    }
}
