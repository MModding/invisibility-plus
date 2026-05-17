package com.mmodding.invisibility.plus.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.invisibility.plus.InvisibilityPlus;
import com.mmodding.invisibility.plus.init.InvisibilityPlusAttachments;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {

	@Shadow
	public abstract boolean hasEffect(Holder<MobEffect> effect);

	@Shadow
	public abstract @Nullable MobEffectInstance getEffect(Holder<MobEffect> effect);

	@Shadow
	@Final
	private Map<Holder<MobEffect>, MobEffectInstance> activeEffects;

	@Inject(method = "updateInvisibilityStatus", at = @At(value = "HEAD"))
	private void updateInvisibilityEffects(CallbackInfo ci) {
		LivingEntity living = (LivingEntity) (Object) this;
		if (this.activeEffects.isEmpty()) {
			living.setAttached(InvisibilityPlusAttachments.LEVEL, null);
		}
		else {
			living.setAttached(InvisibilityPlusAttachments.LEVEL, this.hasEffect(MobEffects.INVISIBILITY) ? this.getEffect(MobEffects.INVISIBILITY).getAmplifier() : null);
		}
	}

	@WrapOperation(method = "tickEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
	private void conditionalEffectParticles(Level instance, ParticleOptions particle, double x, double y, double z, double xd, double yd, double zd, Operation<Void> original) {
		if (!InvisibilityPlus.checkIfApplied(this, 2)) {
			original.call(instance, particle, x, y, z, xd, yd, zd);
		}
	}

	@WrapOperation(method = "checkFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"))
	private <T extends ParticleOptions> int conditionalFallParticles(ServerLevel instance, T particle, double x, double y, double z, int count, double xDist, double yDist, double zDist, double speed, Operation<Integer> original) {
		if (!InvisibilityPlus.checkIfApplied(this, 2)) {
			return original.call(instance, particle, x, y, z, count, xDist, yDist, zDist, speed);
		}
		else {
			return 0;
		}
	}

	@WrapOperation(method = "makeDrownParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
	private void conditionalDrownParticles(Level instance, ParticleOptions particle, double x, double y, double z, double xd, double yd, double zd, Operation<Void> original) {
		if (!InvisibilityPlus.checkIfApplied(this, 2)) {
			original.call(instance, particle, x, y, z, xd, yd, zd);
		}
	}

	@WrapMethod(method = "getArmorCoverPercentage")
	private float armorVisibility(Operation<Float> original) {
		if (InvisibilityPlus.checkIfApplied(this, 3)) {
			return 0.1f;
		}
		else {
			return original.call();
		}
	}

	@WrapOperation(method = "spawnItemParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
	private void conditionalItemParticles(Level instance, ParticleOptions particle, double x, double y, double z, double xd, double yd, double zd, Operation<Void> original) {
		if (!InvisibilityPlus.checkIfApplied(this, 4)) {
			original.call(instance, particle, x, y, z, xd, yd, zd);
		}
	}
}
