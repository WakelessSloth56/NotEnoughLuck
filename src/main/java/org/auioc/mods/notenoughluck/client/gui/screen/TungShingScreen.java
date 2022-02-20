package org.auioc.mods.notenoughluck.client.gui.screen;

import java.util.HashMap;
import java.util.Map;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import org.apache.commons.lang3.tuple.Pair;
import org.auioc.mods.arnicalib.utils.java.Validate;
import org.auioc.mods.notenoughluck.common.network.PacketHandler;
import org.auioc.mods.notenoughluck.server.network.RequestUpdateTungShingPacket;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TungShingScreen extends Screen {

    private static final ResourceLocation BACKGROUND_TEXTURE = TungShingScreenUtils.texture("background");
    private static final int BG_TEXTURE_SIZE = 180;
    private static final int EDITBOX_WIDTH = 95;
    private static final int EDITBOX_HEIGHT = 20;
    private static final int EDITBOX_X_OFFSET = 10;
    private static final int EDITBOX_Y_OFFSET = 180;
    private static final int BUTTON_WIDTH = 60;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_X_OFFSET = 110;
    private static final int BUTTON_Y_OFFSET = 180;
    private static final int BIG_UNSEI_TEXTURE_SIZE = 40;
    private static final int SMALL_UNSEI_TEXTURE_SIZE = 20;
    private static final int SIDE_UNSEI_Y_OFFSET = 120;
    private static final int RIGHT_UNSEI_X_OFFSET = 150;
    private static final int LEFT_UNSEI_X_OFFSET = 10;
    private static final int CENTER_UNSEI_X_OFFSET = 50;
    private static final int CENTER_UNSEI_Y_OFFSET = 118;



    private EditBox editbox;
    private Button button;

    private final Map<Integer, Integer> unseiMap = new HashMap<Integer, Integer>();

    protected TungShingScreen() {
        super(TungShingScreenUtils.i18n("title"));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        int divX = center(this.width, BG_TEXTURE_SIZE);
        int divY = center(this.height, BG_TEXTURE_SIZE);

        this.editbox = new EditBox(
            this.font,
            divX + EDITBOX_X_OFFSET, divY + EDITBOX_Y_OFFSET,
            EDITBOX_WIDTH, EDITBOX_HEIGHT,
            TungShingScreenUtils.i18n("editbox")
        );
        this.editbox.setFilter(TungShingScreenUtils.IS_INTEGER_STRING);
        this.addWidget(this.editbox);

        this.button = new Button(
            divX + BUTTON_X_OFFSET, divY + BUTTON_Y_OFFSET,
            BUTTON_WIDTH, BUTTON_HEIGHT,
            TungShingScreenUtils.i18n("button"),
            (button) -> {
                String day = this.editbox.getValue();
                if (!day.isEmpty()) {
                    PacketHandler.sendToServer(new RequestUpdateTungShingPacket(this.minecraft.player.getUUID(), Integer.valueOf(day)));
                }
            }
        );
        this.addWidget(this.button);

        super.init();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        int divX = center(this.width, BG_TEXTURE_SIZE);
        int divY = center(this.height, BG_TEXTURE_SIZE);

        this.renderBackground(poseStack);

        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        blit(poseStack, divX, divY, BG_TEXTURE_SIZE);

        int i = 0;
        for (Map.Entry<Integer, Integer> entry : this.unseiMap.entrySet()) {
            boolean small = i != 1;

            Pair<ResourceLocation, ResourceLocation> textures = TungShingScreenUtils.getUnseiTexture(entry.getValue(), small);

            int size = small ? SMALL_UNSEI_TEXTURE_SIZE : BIG_UNSEI_TEXTURE_SIZE;
            int offsetX = i == 0 ? LEFT_UNSEI_X_OFFSET : (i == 1 ? CENTER_UNSEI_X_OFFSET : RIGHT_UNSEI_X_OFFSET);
            int offsetY = small ? SIDE_UNSEI_Y_OFFSET : CENTER_UNSEI_Y_OFFSET;

            RenderSystem.setShaderTexture(0, textures.getLeft());
            blit(poseStack, divX + offsetX, divY + offsetY, size);

            RenderSystem.setShaderTexture(0, textures.getRight());
            blit(poseStack, divX + offsetX + (small ? 0 : BIG_UNSEI_TEXTURE_SIZE), divY + offsetY + (small ? SMALL_UNSEI_TEXTURE_SIZE : 0), size);

            i++;
        }

        this.editbox.render(poseStack, mouseX, mouseY, partialTicks);
        this.button.render(poseStack, mouseX, mouseY, partialTicks);

        super.render(poseStack, mouseX, mouseY, partialTicks);
    }


    public void updateUnsei(Map<Integer, Integer> newUnseiMap) {
        Validate.isTrue(newUnseiMap.size() == 3);
        this.unseiMap.clear();
        this.unseiMap.putAll(newUnseiMap);
    }

    private static int center(int screen, int b) {
        return (screen - b) / 2;
    }

    private static void blit(PoseStack poseStack, int x, int y, int size) {
        // X, Y, U, V, W, H, TW, TH
        blit(poseStack, x, y, 0, 0, size, size, size, size);
    }

}
