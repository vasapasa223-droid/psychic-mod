package net.psychic.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/** Базовий клас для всіх psi-сил: приціл, перезарядка, звук і промінь частинок. */
public abstract class PsiPowerItem extends Item {
    protected final int cooldownTicks;
    protected final double range;

    protected PsiPowerItem(Settings settings, int cooldownTicks, double range) {
        super(settings);
        this.cooldownTicks = cooldownTicks;
        this.range = range;
    }

    /** Повертає true, якщо силу було застосовано (тоді вмикається перезарядка). */
    protected abstract boolean activate(ServerWorld world, PlayerEntity user);

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient() && world instanceof ServerWorld serverWorld) {
            if (activate(serverWorld, user)) {
                user.getItemCooldownManager().set(this, cooldownTicks);
                world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME,
                        SoundCategory.PLAYERS, 1.0f, 0.8f);
            }
        }
        return TypedActionResult.success(stack, world.isClient());
    }

    /** Жива ціль у напрямку погляду (або null). */
    protected LivingEntity findTarget(PlayerEntity user) {
        Vec3d start = user.getEyePos();
        Vec3d dir = user.getRotationVec(1.0f);
        Vec3d end = start.add(dir.multiply(range));
        Box box = user.getBoundingBox().stretch(dir.multiply(range)).expand(1.0);
        EntityHitResult hit = ProjectileUtil.raycast(user, start, end, box,
                e -> !e.isSpectator() && e.canHit() && e instanceof LivingEntity, range * range);
        if (hit != null) {
            Entity e = hit.getEntity();
            if (e instanceof LivingEntity living) return living;
        }
        return null;
    }

    /** Малює промінь частинок від гравця до точки. */
    protected void beam(ServerWorld world, PlayerEntity user, Vec3d to, ParticleEffect particle) {
        Vec3d from = user.getEyePos().add(user.getRotationVec(1.0f).multiply(0.6)).subtract(0, 0.2, 0);
        Vec3d diff = to.subtract(from);
        int steps = (int) (diff.length() * 3);
        for (int i = 0; i <= steps; i++) {
            Vec3d p = from.add(diff.multiply(i / (double) Math.max(1, steps)));
            world.spawnParticles(particle, p.x, p.y, p.z, 1, 0, 0, 0, 0);
        }
    }

    protected Vec3d beamEnd(PlayerEntity user) {
        return user.getEyePos().add(user.getRotationVec(1.0f).multiply(range));
    }
}
