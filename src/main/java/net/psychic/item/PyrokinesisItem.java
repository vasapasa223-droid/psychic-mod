package net.psychic.item;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/** Пірокінез: підпалює ціль або блок, на який дивишся. */
public class PyrokinesisItem extends PsiPowerItem {
    public PyrokinesisItem(Settings settings) {
        super(settings, 30, 20.0);
    }

    @Override
    protected boolean activate(ServerWorld world, PlayerEntity user) {
        LivingEntity target = findTarget(user);
        if (target != null) {
            target.setOnFireFor(6);
            beam(world, user, target.getEyePos(), ParticleTypes.FLAME);
            return true;
        }
        HitResult hit = user.raycast(range, 0.0f, false);
        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult bhr = (BlockHitResult) hit;
            BlockPos firePos = bhr.getBlockPos().offset(bhr.getSide());
            if (world.getBlockState(firePos).isAir()) {
                world.setBlockState(firePos, AbstractFireBlock.getState(world, firePos));
            }
            beam(world, user, Vec3d.ofCenter(firePos), ParticleTypes.FLAME);
            return true;
        }
        return false;
    }
}
