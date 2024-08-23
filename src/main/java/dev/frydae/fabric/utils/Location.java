package dev.frydae.fabric.utils;

import dev.frydae.fabric.events.FabricEvents;
import lombok.Getter;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Getter
public final class Location {
    private final World world;
    private final Double x;
    private final Double y;
    private final Double z;
    private final Float yaw;
    private final Float pitch;

    public Location(@NotNull World world, @NotNull BlockPos pos) {
        this(world, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
    }

    public Location(@NotNull World world, @NotNull Vec3d pos) {
        this(world, pos.getX(), pos.getY(), pos.getZ());
    }

    public Location(@NotNull World world, @NotNull Vec3i pos) {
        this(world, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
    }

    public Location(@NotNull World world, @NotNull Integer x, @NotNull Integer y, @NotNull Integer z) {
        this(world, (double) x, (double) y, (double) z, 0F, 0F);
    }

    public Location(@NotNull World world, @NotNull Double x, @NotNull Double y, @NotNull Double z) {
        this(world, x, y, z, 0F, 0F);
    }

    public Location(@NotNull World world, @NotNull Integer x, @NotNull Integer y, @NotNull Integer z, @NotNull Float yaw, @NotNull Float pitch) {
        this(world, (double) x, (double) y, (double) z, yaw, pitch);
    }

    public Location(@NotNull World world, @NotNull Double x, @NotNull Double y, @NotNull Double z, @NotNull Float yaw, @NotNull Float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @NotNull
    public Vec3d vec3d() {
        return new Vec3d(x, y, z);
    }

    @NotNull
    public Vec3i vec3i() {
        return new Vec3i(x.intValue(), y.intValue(), z.intValue());
    }

    @NotNull
    public BlockPos blockPos() {
        return new BlockPos(x.intValue(), y.intValue(), z.intValue());
    }

    @NotNull
    public Integer getBlockX() {
        return x.intValue();
    }

    @NotNull
    public Integer getBlockY() {
        return y.intValue();
    }

    @NotNull
    public Integer getBlockZ() {
        return z.intValue();
    }

    public Location withX(double x) {
        return new Location(world, x, y, z);
    }

    public Location withY(double y) {
        return new Location(world, x, y, z);
    }

    public Location withZ(double z) {
        return new Location(world, x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;

        return Objects.equals(getBlockX(), location.getBlockX()) &&
                Objects.equals(getBlockY(), location.getBlockY()) &&
                Objects.equals(getBlockZ(), location.getBlockZ()) &&
                Objects.equals(world.getRegistryKey().getValue(), location.world.getRegistryKey().getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(world, x, y, z);
    }

    @NotNull
    public Location add(double x, double y, double z) {
        return new Location(world, this.x + x, this.y + y, this.z + z);
    }

    @NotNull
    public Location subtract(double x, double y, double z) {
        return new Location(world, this.x - x, this.y - y, this.z - z);
    }

    public boolean isWithin(@NotNull Location pos1, @NotNull Location pos2) {
        int minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
        int minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
        int minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());

        int maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        int maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
        int maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());

        return getBlockX() >= minX && getBlockX() <= maxX &&
                getBlockY() >= minY && getBlockY() <= maxY &&
                getBlockZ() >= minZ && getBlockZ() <= maxZ;
    }

    @NotNull
    public static Location fromPlayer(@NotNull ServerPlayerEntity player) {
        return new Location(player.getServerWorld(), player.getX(), player.getY(), player.getZ(), player.getYaw(), player.getPitch());
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s", world.getRegistryKey().getValue().getPath(), x, y, z, yaw, pitch);
    }

    public NbtCompound toCompound() {
        NbtCompound compound = new NbtCompound();

        compound.putString("world", world.getRegistryKey().getValue().getPath());
        compound.putDouble("x", x);
        compound.putDouble("y", y);
        compound.putDouble("z", z);
        compound.putFloat("yaw", yaw);
        compound.putFloat("pitch", pitch);

        return compound;
    }

    public static Location fromString(String string) {
        String[] split = string.split(",");

        World world = findWorld(split[0]);

        if (world != null) {
            return new Location(world, Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
        }

        return null;
    }

    public static Location fromCompound(NbtCompound compound) {
        return new Location(
                Objects.requireNonNull(findWorld(compound.getString("world"))),
                compound.getDouble("x"),
                compound.getDouble("y"),
                compound.getDouble("z"),
                compound.getFloat("yaw"),
                compound.getFloat("pitch")
        );
    }

    @Nullable
    private static World findWorld(@NotNull String name) {
        MinecraftServer server = FabricEvents.getServer();

        for (RegistryKey<World> worldRegistryKey : server.getWorldRegistryKeys()) {
            if (worldRegistryKey.getValue().getPath().equals(name)) {
                return server.getWorld(worldRegistryKey);
            }
        }

        return null;
    }
}
