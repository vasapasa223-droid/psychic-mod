package net.psychic.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

/** Телекінез: притягує ціль до тебе. З Shift - відкидає від себе. */
public class TelekinesisItem extends PsiPowerItem {
    public TelekinesisItem(Settings settings) {
        super(settings, 20, 20.0);
    }

    @Override
    protected boolean activate(ServerWorld world, PlayerEntity user) {
        LivingEntity target = findTarget(user);
        if (target == null) return false;
        Vec3d dir = user.getPos().subtract(target.getPos()).normalize();
        double power = user.isSneaking() ? -1.5 : 1.2;
        target.setVelocity(dir.x * power, 0.35, dir.z * power);
        target.velocityModified = true;
        beam(world, user, target.getEyePos(), ParticleTypes.WITCH);
        return true;
    }
}
