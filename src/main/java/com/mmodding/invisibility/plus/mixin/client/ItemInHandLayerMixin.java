package com.mmodding.invisibility.plus.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mmodding.invisibility.plus.InvisibilityPlus;
import com.mmodding.invisibility.plus.client.init.InvisibilityPlusDataKeys;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemInHandLayer.class)
public class ItemInHandLayerMixin {

	@WrapMethod(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/ArmedEntityRenderState;FF)V")
	private <S extends ArmedEntityRenderState> void cancelItemRender(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, S state, float yRot, float xRot, Operation<Void> original) {
		Integer value = state.getData(InvisibilityPlusDataKeys.INV_LEVEL);
		if (!InvisibilityPlus.isEffectEnabled(4) || value == null || value < 4) {
			original.call(poseStack, submitNodeCollector, lightCoords, state, yRot, xRot);
		}
	}
}
