package net.psychic.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;

/** Левітація: піднімає ціль у повітря. З Shift - піднімає тебе самого. */
public class LevitationItem extends PsiPowerItem {
    public LevitationItem(Settings settings) {
        super(settings, 60, 20.0);
    }

    @Override
    protected boolean activate(ServerWorld world, PlayerEntity user) {
        if (user.isSneaking()) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 60, 1));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 240, 0));
            world.spawnParticles(ParticleTypes.END_ROD, user.getX(), user.getY(), user.getZ(), 20, 0.4, 0.1, 0.4, 0.05);
            return true;
        }
        LivingEntity target = findTarget(user);
        if (target == null) return false;
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 100, 0));
        beam(world, user, target.getEyePos(), ParticleTypes.END_ROD);
        return true;
    }
}
