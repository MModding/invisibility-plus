package com.mmodding.invisibility.plus.init;

import com.mmodding.invisibility.plus.InvisibilityPlus;
import com.mmodding.library.core.api.AdvancedContainer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;

public class InvisibilityPlusPotions {

	public static final Holder.Reference<Potion> ENHANCED_INVISIBILITY = register("enhanced_invisibility", new MobEffectInstance(MobEffects.INVISIBILITY, 3600, 1));
	public static final Holder.Reference<Potion> LONG_ENHANCED_INVISIBILITY = register("long_enhanced_invisibility", new MobEffectInstance(MobEffects.INVISIBILITY, 9600, 1));

	public static final Holder.Reference<Potion> STRONGLY_ENHANCED_INVISIBILITY = register("strongly_enhanced_invisibility", new MobEffectInstance(MobEffects.INVISIBILITY, 3600, 2));
	public static final Holder.Reference<Potion> LONG_STRONGLY_ENHANCED_INVISIBILITY = register("long_strongly_enhanced_invisibility", new MobEffectInstance(MobEffects.INVISIBILITY, 9600, 2));

	public static final Holder.Reference<Potion> REINFORCED_INVISIBILITY = register("reinforced_invisibility", new MobEffectInstance(MobEffects.INVISIBILITY, 3600, 3));
	public static final Holder.Reference<Potion> LONG_REINFORCED_INVISIBILITY = register("long_reinforced_invisibility", new MobEffectInstance(MobEffects.INVISIBILITY, 9600, 3));

	public static final Holder.Reference<Potion> STRONGLY_REINFORCED_INVISIBILITY = register("strongly_reinforced_invisibility", new MobEffectInstance(MobEffects.INVISIBILITY, 3600, 4));
	public static final Holder.Reference<Potion> LONG_STRONGLY_REINFORCED_INVISIBILITY = register("long_strongly_reinforced_invisibility", new MobEffectInstance(MobEffects.INVISIBILITY, 9600, 4));

	public static Holder.Reference<Potion> register(String path, MobEffectInstance... effects) {
		return Registry.registerForHolder(BuiltInRegistries.POTION, InvisibilityPlus.createKey(Registries.POTION, path), new Potion(path, effects));
	}

	public static void register(AdvancedContainer mod) {}
}
