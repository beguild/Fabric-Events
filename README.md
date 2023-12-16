# Fabric Event API

**Description**

The Fabric Event API is a lightweight library for creating and managing events in Minecraft mods using the Fabric modding framework. It is designed to replicate the functionality of the Spigot API's Event system, providing a familiar structure for developers transitioning from Spigot to Fabric.

**Features**

- **Event Handling**: Easily create and handle events in your Fabric mods.
- **Familiar Syntax**: Designed to resemble the Spigot API's Event system for a seamless transition.
- **Lightweight**: Minimalistic and efficient implementation to keep your mod lightweight.

**Usage**

1. **Installation**:

   Add the following to your gradle build file (`build.gradle` or `build.gradle.kts`):

   ```groovy
   repositories {
       maven {
           url = uri("https://repo.frydae.dev/repository/maven-snapshots/")
       }
   }
   ```

   Add the following dependency to your `build.gradle`:

   <details>
   <summary>Groovy</summary>

   ```groovy
   modImplementation "dev.frydae:fabric-events:${project.fabric_version}-SNAPSHOT"
   ```
   </details>

   <details>
   <summary>Kotlin</summary>

   ```groovy
   modImplementation("dev.frydae:fabric-events:${property("fabric_version")!!}-SNAPSHOT")
   ```
   </details>

2. **Creating an Event**:
   ```java
   import dev.frydae.fabric.events.Event;
   import dev.frydae.fabric.events.Cancellable;

   public class MyEvent extends Event /*implements Cancellable*/ {
      // Your event code here
   
      // If you want to be able to cancel your event, uncomment the line above and those below:
      /*
      private boolean cancelled = false;
   
      @Override
      public boolean isCancelled() {
          return cancelled;
      }
   
      @Override
      public void setCancelled(boolean cancelled) {
          this.cancelled = cancelled;
      }
      */
   }
   ```
   
3. **Example Mixin**:

    If you want to make your own events, you'll have to create your own mixin as well.

    Here's an example of a mixin for the `PlayerDropItemEvent`:

    ```java
    package dev.frydae.fabric.mixins;
   
    import dev.frydae.fabric.events.player.PlayerDropItemEvent;
    import net.minecraft.item.ItemStack;
    import net.minecraft.server.network.ServerPlayerEntity;
    import org.spongepowered.asm.mixin.Mixin;
    import org.spongepowered.asm.mixin.injection.At;
    import org.spongepowered.asm.mixin.injection.Inject;
    import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

    @Mixin(ServerPlayerEntity.class)
    public class ServerPlayerEntityMixin {
        @Inject(method = "dropSelectedItem", at = @At("HEAD"), cancellable = true)
        public void onDropItem(boolean entireStack, CallbackInfoReturnable<Boolean> cir) {
            ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
            ItemStack stack = player.getMainHandStack();

            if (!stack.isEmpty()) {
                PlayerDropItemEvent event = new PlayerDropItemEvent(player, stack);

                event.callEvent();

                if (event.isCancelled()) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
    ```

4. **Example Usage**:

   ```java
   import dev.frydae.fabric.events.Listener;
   import dev.frydae.fabric.events.EventHandler;
   
   public class MyListener implements Listener {
       @EventHandler
       public void onMyEvent(MyEvent event) {
           // Your event handling code here
       }
   }
   ```

5. **Registering and Handling Events**:

   ```java
   import dev.frydae.fabric.events.EventManager;

   public class MyMod implements ModInitializer {
       @Override
       public void onInitialize() {
           EventManager.getInstance().registerEvents(new MyListener());
       }
   }
   ```

**Contributing**

Contributions are welcome! Feel free to open issues or submit pull requests.

**License**

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

**Acknowledgments**

- FabricMC Team: for the Fabric modding framework.
- Spigot Team: for the inspiration from the Spigot API.

**Contact**

For questions or support, please contact FabricEvents@frydae.dev.
