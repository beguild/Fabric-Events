package dev.frydae.fabric.events;

import com.mojang.logging.LogUtils;
import dev.frydae.beguild.loader.BeGuildMod;
import lombok.Getter;
import org.slf4j.Logger;

public final class FabricEvents extends BeGuildMod {
    @Getter private static final Logger logger = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        EventManager.getInstance();
    }
}
