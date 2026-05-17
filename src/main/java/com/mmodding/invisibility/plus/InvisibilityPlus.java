package com.mmodding.invisibility.plus;

import com.mmodding.invisibility.plus.init.InvisibilityPlusAttachments;
import com.mmodding.invisibility.plus.init.InvisibilityPlusPotions;
import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.ConfigLevel;
import com.mmodding.library.config.api.content.ConfigSpec;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.ExtendedModInitializer;
import com.mmodding.library.core.api.management.ElementsManager;
import net.fabricmc.fabric.api.registry.FabricPotionBrewingBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;

public class InvisibilityPlus implements ExtendedModInitializer {

	private static final ConfigSpec SPEC = ConfigSpec.create()
		.bool("inv2effects", true)
		.bool("inv3effects", true)
		.bool("inv4effects", true)
		.bool("inv5effects", true);

	public static final Config CONFIG = Config.builder("config.invisibility_plus", "invisibility_plus/common", SPEC)
		.withLevel(ConfigLevel.IN_GAME_MODIFICATION)
		.build(createId("config"));

	@Override
	public void setupManager(ElementsManager manager) {
		manager.content(InvisibilityPlusAttachments::register); // classloads the attachment, because otherwise the client isn't aware of it, and so it breaks pretty much everything
		manager.content(InvisibilityPlusPotions::register);
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {
		mod.logger().info("Oh, I can sense that you like to hide!");

		FabricPotionBrewingBuilder.BUILD.register(builder -> {
			builder.addMix(Potions.INVISIBILITY, Items.GLOWSTONE_DUST, InvisibilityPlusPotions.ENHANCED_INVISIBILITY);
			builder.addMix(InvisibilityPlusPotions.ENHANCED_INVISIBILITY, Items.QUARTZ, InvisibilityPlusPotions.STRONGLY_ENHANCED_INVISIBILITY);
			builder.addMix(InvisibilityPlusPotions.STRONGLY_ENHANCED_INVISIBILITY, Items.GLOWSTONE, InvisibilityPlusPotions.REINFORCED_INVISIBILITY);
			builder.addMix(InvisibilityPlusPotions.REINFORCED_INVISIBILITY, Items.QUARTZ_BLOCK, InvisibilityPlusPotions.STRONGLY_REINFORCED_INVISIBILITY);
			builder.addMix(InvisibilityPlusPotions.ENHANCED_INVISIBILITY, Items.REDSTONE, InvisibilityPlusPotions.LONG_ENHANCED_INVISIBILITY);
			builder.addMix(InvisibilityPlusPotions.STRONGLY_ENHANCED_INVISIBILITY, Items.REDSTONE, InvisibilityPlusPotions.LONG_STRONGLY_ENHANCED_INVISIBILITY);
			builder.addMix(InvisibilityPlusPotions.REINFORCED_INVISIBILITY, Items.REDSTONE, InvisibilityPlusPotions.LONG_REINFORCED_INVISIBILITY);
			builder.addMix(InvisibilityPlusPotions.STRONGLY_REINFORCED_INVISIBILITY, Items.REDSTONE, InvisibilityPlusPotions.LONG_STRONGLY_REINFORCED_INVISIBILITY);
		});
	}

	public static boolean checkIfApplied(Object entity, int requiredAmplifier) {
		if (entity instanceof LivingEntity living && isEffectEnabled(requiredAmplifier)) {
			if (!living.hasAttached(InvisibilityPlusAttachments.LEVEL)) return false;
			Integer value = living.getAttached(InvisibilityPlusAttachments.LEVEL);
			return value != null && value >= requiredAmplifier;
		}
		else {
			return false;
		}
	}

	public static boolean isEffectEnabled(int amplifier) {
		if (amplifier == 0 || amplifier > 4) {
			throw new IllegalArgumentException("Invisibility Plus only add behaviors for [1-4] invisibility amplifiers");
		}
		return CONFIG.getContent().bool("inv" + (amplifier + 1) + "effects");
	}

	public static String namespace() {
		return "invisibility_plus";
	}

	public static Identifier createId(String path) {
		return Identifier.fromNamespaceAndPath(namespace(), path);
	}

	public static <T> ResourceKey<T> createKey(ResourceKey<? extends Registry<T>> registry, String path) {
		return ResourceKey.create(registry, createId(path));
	}
}
