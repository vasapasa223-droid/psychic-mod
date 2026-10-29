package net.psychic.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

/** PSI Blast: ударна хвиля розуму, що б'є ціль на відстані. */
public class PsiBlastItem extends PsiPowerItem {
    public PsiBlastItem(Settings settings) {
        super(settings, 15, 24.0);
    }

    @Override
    protected boolean activate(ServerWorld world, PlayerEntity user) {
        LivingEntity target = findTarget(user);
        if (target == null) {
            beam(world, user, beamEnd(user), ParticleTypes.END_ROD);
            return true;
        }
        target.damage(world.getDamageSources().magic(), 6.0f);
        Vec3d push = target.getPos().subtract(user.getPos()).normalize().multiply(0.8);
        target.addVelocity(push.x, 0.3, push.z);
        target.velocityModified = true;
        beam(world, user, target.getEyePos(), ParticleTypes.END_ROD);
        world.spawnParticles(ParticleTypes.ENCHANTED_HIT, target.getX(), target.getBodyY(0.5), target.getZ(),
                15, 0.3, 0.4, 0.3, 0.2);
        return true;
    }
}
