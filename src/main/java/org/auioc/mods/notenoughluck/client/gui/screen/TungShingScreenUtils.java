package org.auioc.mods.notenoughluck.client.gui.screen;

import java.util.function.Predicate;
import org.apache.commons.lang3.tuple.Pair;
import org.auioc.mods.arnicalib.utils.game.TextUtils;
import org.auioc.mods.arnicalib.utils.java.Validate;
import org.auioc.mods.notenoughluck.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TungShingScreenUtils {

    public static TungShingScreen open(boolean reuse) {
        Minecraft mc = Minecraft.getInstance();
        if (reuse && mc.screen instanceof TungShingScreen) {
            return (TungShingScreen) mc.screen;
        }
        mc.setScreen(new TungShingScreen());
        return (TungShingScreen) mc.screen;
    }

    protected static ResourceLocation texture(String key) {
        return Reference.ResourceId("textures/gui/tung_shing/" + key + ".png");
    }

    protected static Component i18n(String key) {
        return TextUtils.I18nText(Reference.I18nKey("gui.tung_shing." + key));
    }

    protected static final Predicate<String> IS_INTEGER_STRING = (string) -> {
        if (string.isEmpty()) {
            return true;
        }
        try {
            int n = Integer.valueOf(string);
            return n >= 0 ? true : false;
        } catch (
            Exception e
        ) {
            return false;
        }
    };

    protected static enum UnseiTexture {
        DAI("prefixes", "dai"), CHUU("prefixes", "chuu"), SHOU("prefixes", "shou"), SUE("prefixes", "sue"), //
        HEI_A("fortunes", "hei_a"), HEI_B("fortunes", "hei_b"), //
        KICHI("fortunes", "kichi"), KYOU("fortunes", "kyou");

        public final ResourceLocation bigTexture;
        public final ResourceLocation smallTexture;

        private UnseiTexture(String type, String name) {
            this.bigTexture = texture(type + "/" + name + "_big");
            this.smallTexture = texture(type + "/" + name + "_small");
        }
    }

    private static Pair<ResourceLocation, ResourceLocation> getUnseiTexture(Pair<UnseiTexture, UnseiTexture> unsei, boolean small) {
        return Pair.of(
            small ? unsei.getLeft().smallTexture : unsei.getLeft().bigTexture,
            small ? unsei.getRight().smallTexture : unsei.getRight().bigTexture
        );
    }

    protected static Pair<ResourceLocation, ResourceLocation> getUnseiTexture(int unsei, boolean small) {
        Validate.isInCloseInterval(0, 36, unsei);
        if (unsei < 1) {
            return getUnseiTexture(Pair.of(UnseiTexture.DAI, UnseiTexture.KICHI), small);
        }
        if (unsei < 3) {
            return getUnseiTexture(Pair.of(UnseiTexture.CHUU, UnseiTexture.KICHI), small);
        }
        if (unsei < 7) {
            return getUnseiTexture(Pair.of(UnseiTexture.SHOU, UnseiTexture.KICHI), small);
        }
        if (unsei < 15) {
            return getUnseiTexture(Pair.of(UnseiTexture.SUE, UnseiTexture.KICHI), small);
        }
        if (unsei < 25) {
            return getUnseiTexture(Pair.of(UnseiTexture.HEI_A, UnseiTexture.HEI_B), small);
        }
        if (unsei < 31) {
            return getUnseiTexture(Pair.of(UnseiTexture.SUE, UnseiTexture.KYOU), small);
        }
        if (unsei < 34) {
            return getUnseiTexture(Pair.of(UnseiTexture.SHOU, UnseiTexture.KYOU), small);
        }
        if (unsei < 36) {
            return getUnseiTexture(Pair.of(UnseiTexture.CHUU, UnseiTexture.KYOU), small);
        }
        return getUnseiTexture(Pair.of(UnseiTexture.DAI, UnseiTexture.KYOU), small);

        // 1   2     4         8                      10                              6                   3          2       1
        // (0) (1 2) (3 4 5 6) (7 8 9 10 11 12 13 14) (15 16 17 18 19 20 21 22 23 24) (25 26 27 28 29 30) (31 32 33) (34 35) (36)
    }

}
