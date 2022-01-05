package org.auioc.mods.notenoughluck.common.alchemy.impl;

import org.auioc.mods.notenoughluck.common.alchemy.PotionRegistry;
import org.auioc.mods.notenoughluck.common.alchemy.base.NELPotion;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.registries.RegistryObject;

public class LuckPotion extends NELPotion {

    protected LuckPotion(int duration, int amplifier) {
        super("luck", MobEffects.LUCK, duration, amplifier);
    }

    public static class Long extends LuckPotion {
        protected Long() {
            super(10 * 60 * 20, 0);
        }

        public static RegistryObject<Potion> register() {
            return register("long_luck", Long::new);
        }

        public static boolean registerBrewingRecipe() {
            return registerBrewingRecipe(Potions.LUCK, Items.REDSTONE, PotionRegistry.LONG_LUCK);
        }
    }

    public static class Strong extends LuckPotion {
        protected Strong() {
            super((int) 2.5 * 60 * 20, 1);
        }

        public static RegistryObject<Potion> register() {
            return register("strong_luck", Strong::new);
        }

        public static boolean registerBrewingRecipe() {
            return registerBrewingRecipe(Potions.LUCK, Items.GLOWSTONE_DUST, PotionRegistry.STRONG_LUCK);
        }
    }

}
