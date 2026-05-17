package com.mmodding.invisibility.plus.init;

import com.mmodding.invisibility.plus.InvisibilityPlus;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;

public class InvisibilityPlusAttachments {

	public static final AttachmentType<Integer> LEVEL = AttachmentRegistry.create(InvisibilityPlus.createId("level"), builder -> builder
		.persistent(Codec.INT)
		.syncWith(ByteBufCodecs.INT, AttachmentSyncPredicate.all())
	);

	public static void register(AdvancedContainer mod) {
	}
}
