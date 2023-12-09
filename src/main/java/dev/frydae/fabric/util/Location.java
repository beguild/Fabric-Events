package dev.frydae.fabric.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record Location(@NotNull World world, @NotNull Double x, @NotNull Double y, @NotNull Double z) {
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
        this(world, (double) x, (double) y, (double) z);
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

    public boolean isWithin(@NotNull Location lowLoc, @NotNull Location highLoc) {
        return  lowLoc.getBlockX() <= getBlockX() && getBlockX() <= highLoc.getBlockX() &&
                lowLoc.getBlockY() <= getBlockY() && getBlockY() <= highLoc.getBlockY() &&
                lowLoc.getBlockZ() <= getBlockZ() && getBlockZ() <= highLoc.getBlockZ();
    }

    @Override
    public String toString() {
        return String.format("Location{world=%s, x=%s, y=%s, z=%s}", world.getRegistryKey().getValue(), x, y, z);
    }
}
