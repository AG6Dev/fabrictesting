package com.example.client.screen;

import com.example.ExampleMod;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class AlloySmelterScreen extends HandledScreen<AlloySmelterScreenHandler> {
    public static final Identifier TEXTURE = new Identifier(ExampleMod.MOD_ID, "textures/gui/alloy_smelter.png");

    public AlloySmelterScreen(AlloySmelterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override

    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        //TODO: Fix it drawing the burn progress bar backwards
        int burnProgress = MathHelper.ceil(((float) this.handler.getBurnTime() / this.handler.getTotalBurnTime()) * 13);
        context.drawTexture(TEXTURE, this.x + 47, this.y + 37, 176, 0, 14, burnProgress);

        int smeltProgress = MathHelper.ceil(((float) this.handler.getSmeltProgress() / this.handler.getMaxProgress()) * 24);
        context.drawTexture(TEXTURE, this.x + 79, this.y + 35, 176, 14, smeltProgress + 1, 16);
    }
}
