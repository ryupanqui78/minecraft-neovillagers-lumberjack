package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.client;

import java.util.List;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.NeoVillagersLumberjack;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.inventory.WoodWorkingMenu;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting.WoodWorkingRecipe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WoodWorkingScreen extends AbstractContainerScreen<WoodWorkingMenu> {
    
    private static final ResourceLocation SCROLLER_SPRITE = new ResourceLocation("container/stonecutter/scroller");
    private static final ResourceLocation SCROLLER_DISABLED_SPRITE = new ResourceLocation(
            "container/stonecutter/scroller_disabled");
    private static final ResourceLocation RECIPE_SELECTED_SPRITE = new ResourceLocation(
            "container/stonecutter/recipe_selected");
    private static final ResourceLocation RECIPE_HIGHLIGHTED_SPRITE = new ResourceLocation(
            "container/stonecutter/recipe_highlighted");
    private static final ResourceLocation RECIPE_SPRITE = new ResourceLocation("container/stonecutter/recipe");
    private static final ResourceLocation TEXTURE = new ResourceLocation(NeoVillagersLumberjack.MODID,
            "textures/gui/container/woodworking.png");
    private static final int SCROLLER_WIDTH = 12;
    private static final int SCROLLER_HEIGHT = 15;
    private static final int SCROLLER_X = 119;
    private static final int SCROLLER_Y = 17;
    private static final int RECIPES_X = 52;
    private static final int RECIPES_Y = 16;
    private static final int RECIPES_COLUMNS = 4;
    private static final int RECIPES_IMAGE_SIZE_WIDTH = 16;
    private static final int RECIPES_IMAGE_SIZE_HEIGHT = 18;
    
    private boolean scrolling;
    private float scrollOffs;
    private int startIndex;
    private boolean displayRecipes;
    
    public WoodWorkingScreen(WoodWorkingMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        menu.registerUpdateListener(this::containerChanged);
    }
    
    private void containerChanged() {
        this.displayRecipes = this.menu.hasInputItem();
        if (!this.displayRecipes) {
            this.scrollOffs = 0.0F;
            this.startIndex = 0;
        }
    }
    
    protected int getOffscreenRows() {
        return (((this.menu.getNumRecipes() + WoodWorkingScreen.RECIPES_COLUMNS) - 1)
                / WoodWorkingScreen.RECIPES_COLUMNS) - 3;
    }
    
    private boolean isScrollBarActive() {
        return this.displayRecipes && (this.menu.getNumRecipes() > WoodWorkingScreen.SCROLLER_WIDTH);
    }
    
    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        this.scrolling = false;
        if (this.displayRecipes) {
            final int k = this.startIndex + WoodWorkingScreen.SCROLLER_WIDTH;
            int i = this.leftPos + WoodWorkingScreen.RECIPES_X;
            int j = this.topPos + WoodWorkingScreen.RECIPES_Y;
            
            for (int l = this.startIndex; l < k; ++l) {
                final int i1 = l - this.startIndex;
                final int carryX = i1 % WoodWorkingScreen.RECIPES_COLUMNS;
                final int carryY = i1 / WoodWorkingScreen.RECIPES_COLUMNS;
                final double d0 = pMouseX - (i + (carryX * WoodWorkingScreen.RECIPES_IMAGE_SIZE_WIDTH));
                final double d1 = pMouseY - (j + (carryY * WoodWorkingScreen.RECIPES_IMAGE_SIZE_HEIGHT));
                if ((d0 >= 0.0) && (d1 >= 0.0) && (d0 < WoodWorkingScreen.RECIPES_IMAGE_SIZE_WIDTH)
                        && (d1 < WoodWorkingScreen.RECIPES_IMAGE_SIZE_HEIGHT)
                        && this.menu.clickMenuButton(this.minecraft.player, l)) {
                    Minecraft.getInstance().getSoundManager()
                            .play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                    this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, l);
                    return true;
                }
            }
            
            i = this.leftPos + WoodWorkingScreen.SCROLLER_X;
            j = this.topPos + 11;
            if ((pMouseX >= i) && (pMouseX < (i + WoodWorkingScreen.SCROLLER_WIDTH)) && (pMouseY >= j)
                    && (pMouseY < (j + 54))) {
                this.scrolling = true;
            }
        }
        
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }
    
    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (this.scrolling && this.isScrollBarActive()) {
            final int i = this.topPos + WoodWorkingScreen.RECIPES_Y;
            final int j = i + 54;
            this.scrollOffs = ((float) pMouseY - i - 7.5F) / (j - i - 15.0F);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.startIndex = (int) ((this.scrollOffs * this.getOffscreenRows()) + 0.5)
                    * WoodWorkingScreen.RECIPES_COLUMNS;
            return true;
        } else {
            return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
        }
    }
    
    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pScrollX, double pScrollY) {
        if (this.isScrollBarActive()) {
            final int i = this.getOffscreenRows();
            final float f = (float) pScrollY / i;
            this.scrollOffs = Mth.clamp(this.scrollOffs - f, 0.0F, 1.0F);
            this.startIndex = (int) ((this.scrollOffs * i) + 0.5) * WoodWorkingScreen.RECIPES_COLUMNS;
        }
        
        return true;
    }
    
    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
    
    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        final int i = this.leftPos;
        final int j = this.topPos;
        pGuiGraphics.blit(WoodWorkingScreen.TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
        final int k = (int) (39.0F * this.scrollOffs);
        final ResourceLocation resourcelocation = this.isScrollBarActive() ? WoodWorkingScreen.SCROLLER_SPRITE
                : WoodWorkingScreen.SCROLLER_DISABLED_SPRITE;
        pGuiGraphics.blitSprite(resourcelocation, i + WoodWorkingScreen.SCROLLER_X,
                j + WoodWorkingScreen.SCROLLER_Y + k, WoodWorkingScreen.SCROLLER_WIDTH,
                WoodWorkingScreen.SCROLLER_HEIGHT);
        final int l = this.leftPos + WoodWorkingScreen.RECIPES_X;
        final int i1 = this.topPos + WoodWorkingScreen.RECIPES_Y;
        final int j1 = this.startIndex + WoodWorkingScreen.SCROLLER_WIDTH;
        this.renderButtons(pGuiGraphics, pMouseX, pMouseY, l, i1, j1);
        this.renderRecipes(pGuiGraphics, l, i1, j1);
    }
    
    private void renderButtons(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int pX, int pY, int pLastVisibleElementIndex) {
        for (int i = this.startIndex; (i < pLastVisibleElementIndex) && (i < this.menu.getNumRecipes()); ++i) {
            final int j = i - this.startIndex;
            final int k = pX + ((j % WoodWorkingScreen.RECIPES_COLUMNS) * WoodWorkingScreen.RECIPES_IMAGE_SIZE_WIDTH);
            final int l = j / WoodWorkingScreen.RECIPES_COLUMNS;
            final int i1 = pY + (l * WoodWorkingScreen.RECIPES_IMAGE_SIZE_HEIGHT) + 2;
            ResourceLocation resourcelocation;
            if (i == this.menu.getSelectedRecipeIndex()) {
                resourcelocation = WoodWorkingScreen.RECIPE_SELECTED_SPRITE;
            } else if ((pMouseX >= k) && (pMouseY >= i1) && (pMouseX < (k + WoodWorkingScreen.RECIPES_IMAGE_SIZE_WIDTH))
                    && (pMouseY < (i1 + WoodWorkingScreen.RECIPES_IMAGE_SIZE_HEIGHT))) {
                resourcelocation = WoodWorkingScreen.RECIPE_HIGHLIGHTED_SPRITE;
            } else {
                resourcelocation = WoodWorkingScreen.RECIPE_SPRITE;
            }
            
            pGuiGraphics.blitSprite(resourcelocation, k, i1 - 1, WoodWorkingScreen.RECIPES_IMAGE_SIZE_WIDTH,
                    WoodWorkingScreen.RECIPES_IMAGE_SIZE_HEIGHT);
        }
    }
    
    private void renderRecipes(GuiGraphics pGuiGraphics, int pX, int pY, int pStartIndex) {
        final List<RecipeHolder<WoodWorkingRecipe>> list = this.menu.getRecipes();
        
        for (int i = this.startIndex; (i < pStartIndex) && (i < this.menu.getNumRecipes()); ++i) {
            final int j = i - this.startIndex;
            final int k = pX + ((j % WoodWorkingScreen.RECIPES_COLUMNS) * WoodWorkingScreen.RECIPES_IMAGE_SIZE_WIDTH);
            final int l = j / WoodWorkingScreen.RECIPES_COLUMNS;
            final int i1 = pY + (l * WoodWorkingScreen.RECIPES_IMAGE_SIZE_HEIGHT) + 2;
            pGuiGraphics.renderItem(list.get(i).value().getResultItem(this.minecraft.level.registryAccess()), k, i1);
        }
    }
    
    @Override
    protected void renderTooltip(GuiGraphics pGuiGraphics, int pX, int pY) {
        super.renderTooltip(pGuiGraphics, pX, pY);
        if (this.displayRecipes) {
            final int i = this.leftPos + WoodWorkingScreen.RECIPES_X;
            final int j = this.topPos + WoodWorkingScreen.RECIPES_Y;
            final int k = this.startIndex + WoodWorkingScreen.SCROLLER_WIDTH;
            final List<RecipeHolder<WoodWorkingRecipe>> list = this.menu.getRecipes();
            
            for (int l = this.startIndex; (l < k) && (l < this.menu.getNumRecipes()); ++l) {
                final int i1 = l - this.startIndex;
                final int j1 = i
                        + ((i1 % WoodWorkingScreen.RECIPES_COLUMNS) * WoodWorkingScreen.RECIPES_IMAGE_SIZE_WIDTH);
                final int k1 = j
                        + ((i1 / WoodWorkingScreen.RECIPES_COLUMNS) * WoodWorkingScreen.RECIPES_IMAGE_SIZE_HEIGHT) + 2;
                if ((pX >= j1) && (pX < (j1 + WoodWorkingScreen.RECIPES_IMAGE_SIZE_WIDTH)) && (pY >= k1)
                        && (pY < (k1 + WoodWorkingScreen.RECIPES_IMAGE_SIZE_HEIGHT))) {
                    pGuiGraphics.renderTooltip(this.font,
                            list.get(l).value().getResultItem(this.minecraft.level.registryAccess()), pX, pY);
                }
            }
        }
    }
}