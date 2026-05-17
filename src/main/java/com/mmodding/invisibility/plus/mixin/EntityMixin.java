package com.mmodding.invisibility.plus.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.invisibility.plus.InvisibilityPlus;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@WrapMethod(method = "isSilent")
	private boolean forceSilent(Operation<Boolean> original) {
		return InvisibilityPlus.checkIfApplied(this, 1) || original.call();
	}

	@WrapOperation(method = "doWaterSplashEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
	private void conditionalSwimParticles(Level instance, ParticleOptions particle, double x, double y, double z, double xd, double yd, double zd, Operation<Void> original) {
		if (!InvisibilityPlus.checkIfApplied(this, 2)) {
			original.call(instance, particle, x, y, z, xd, yd, zd);
		}
	}

	@WrapMethod(method = "canSpawnSprintParticle")
	private boolean removeIfEnough(Operation<Boolean> original) {
		return !InvisibilityPlus.checkIfApplied(this, 2) && original.call();
	}
}
