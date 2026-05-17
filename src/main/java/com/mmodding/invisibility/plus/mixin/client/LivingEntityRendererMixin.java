package com.mmodding.invisibility.plus.mixin.client;

import com.mmodding.invisibility.plus.client.init.InvisibilityPlusDataKeys;
import com.mmodding.invisibility.plus.init.InvisibilityPlusAttachments;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", at = @At("TAIL"))
	private <T extends LivingEntity, S extends LivingEntityRenderState> void extractInvLevel(T entity, S state, float partialTicks, CallbackInfo ci) {
		if (entity.hasAttached(InvisibilityPlusAttachments.LEVEL)) {
			state.setData(InvisibilityPlusDataKeys.INV_LEVEL, entity.getAttached(InvisibilityPlusAttachments.LEVEL));
		}
	}
}
