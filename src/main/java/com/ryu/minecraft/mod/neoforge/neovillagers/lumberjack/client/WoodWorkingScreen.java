package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.client;

import java.util.List;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.NeoVillagersLumberjack;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.inventory.WoodWorkingMenu;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting.WoodWorkingRecipe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

public class WoodWorkingScreen extends AbstractContainerScreen<WoodWorkingMenu> {
    
    private static final int RECIPES_COLUMNS = 4;
    private static final int RECIPES_IMAGE_SIZE = 18;
    private static final int RECIPES_ROWS = 3;
    private static final int RESULT_START_X = 41;
    private static final int RESULT_START_Y = 17;
    private static final int SCROLLER_CONTENT_SIZE = 54;
    private static final int SCROLLER_HEIGHT = 15;
    private static final int SCROLLER_START_X = 116;
    private static final int SCROLLER_START_Y = 17;
    private static final int SCROLLER_WIDTH = 12;
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(NeoVillagersLumberjack.MODID,
            "textures/gui/container/woodworking.png");
    
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
    
    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        graphics.blit(RenderPipelines.GUI_TEXTURED, WoodWorkingScreen.TEXTURE, this.leftPos, this.topPos, 0, 0,
                this.imageWidth, this.imageHeight, 256, 256);
        final int k = (int) (39.0F * this.scrollOffs);
        final int posScrollImageX = this.isScrollBarActive() ? 176 : 188;
        final int initialScrollPosX = this.leftPos + WoodWorkingScreen.SCROLLER_START_X;
        final int initialScrollPosY = this.topPos + WoodWorkingScreen.SCROLLER_START_Y;
        
        graphics.blit(RenderPipelines.GUI_TEXTURED, WoodWorkingScreen.TEXTURE, initialScrollPosX, initialScrollPosY + k,
                posScrollImageX, 0, WoodWorkingScreen.SCROLLER_WIDTH, WoodWorkingScreen.SCROLLER_HEIGHT, 256, 256);
        this.renderButtons(graphics, mouseX, mouseY);
    }
    
    @Override
    protected void extractTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractTooltip(graphics, mouseX, mouseY);
        if (this.displayRecipes) {
            final int initialPosX = this.leftPos + WoodWorkingScreen.RESULT_START_X;
            final int initialPosY = this.topPos + WoodWorkingScreen.RESULT_START_Y;
            final int indexLastVisibleElement = this.startIndex
                    + (WoodWorkingScreen.RECIPES_COLUMNS * WoodWorkingScreen.RECIPES_ROWS);
            final List<RecipeHolder<WoodWorkingRecipe>> visibleRecipes = this.menu.getVisibleRecipes();
            
            for (int i = this.startIndex; (i < indexLastVisibleElement)
                    && (i < this.menu.getNumberOfVisibleRecipes()); ++i) {
                final ItemStack resultToDisplay = visibleRecipes.get(i).value().getResultItem();
                final int indexInScreen = i - this.startIndex;
                final int posIngredientX = initialPosX
                        + ((indexInScreen % WoodWorkingScreen.RECIPES_COLUMNS) * WoodWorkingScreen.RECIPES_IMAGE_SIZE);
                final int row = indexInScreen / WoodWorkingScreen.RECIPES_COLUMNS;
                final int posIngredientY = initialPosY + (row * WoodWorkingScreen.RECIPES_IMAGE_SIZE);
                if ((mouseX >= posIngredientX) && (mouseY >= posIngredientY)
                        && (mouseX < (posIngredientX + WoodWorkingScreen.RECIPES_IMAGE_SIZE))
                        && (mouseY < (posIngredientY + WoodWorkingScreen.RECIPES_IMAGE_SIZE))) {
                    graphics.setTooltipForNextFrame(this.font, resultToDisplay, mouseX, mouseY);
                }
            }
        }
    }
    
    protected int getOffscreenRows() {
        return (((this.menu.getNumberOfVisibleRecipes() + WoodWorkingScreen.RECIPES_COLUMNS) - 1)
                / WoodWorkingScreen.RECIPES_COLUMNS) - 3;
    }
    
    private boolean isScrollBarActive() {
        return this.displayRecipes && (this.menu.getNumberOfVisibleRecipes() > WoodWorkingScreen.SCROLLER_WIDTH);
    }
    
    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick) {
        this.scrolling = false;
        if (this.displayRecipes) {
            final int indexLastVisibleElement = this.startIndex
                    + (WoodWorkingScreen.RECIPES_COLUMNS * WoodWorkingScreen.RECIPES_ROWS);
            final int initialIngredientPosX = this.leftPos + WoodWorkingScreen.RESULT_START_X;
            final int initialIngredientPosY = this.topPos + WoodWorkingScreen.RESULT_START_Y;
            final int initialScrollPosX = this.leftPos + WoodWorkingScreen.SCROLLER_START_X;
            final int initialScrollPosY = this.topPos + WoodWorkingScreen.SCROLLER_START_Y;
            final int maxScrollPosY = initialScrollPosY + WoodWorkingScreen.SCROLLER_CONTENT_SIZE;
            
            for (int i = this.startIndex; i < indexLastVisibleElement; ++i) {
                final int indexInScreen = i - this.startIndex;
                final int carryX = indexInScreen % WoodWorkingScreen.RECIPES_COLUMNS;
                final int carryY = indexInScreen / WoodWorkingScreen.RECIPES_COLUMNS;
                final double d0 = event.x() - (initialIngredientPosX + (carryX * WoodWorkingScreen.RECIPES_IMAGE_SIZE));
                final double d1 = event.y() - (initialIngredientPosY + (carryY * WoodWorkingScreen.RECIPES_IMAGE_SIZE));
                if ((d0 >= 0.0) && (d1 >= 0.0) && (d0 < WoodWorkingScreen.RECIPES_IMAGE_SIZE)
                        && (d1 < WoodWorkingScreen.RECIPES_IMAGE_SIZE)
                        && this.menu.clickMenuButton(this.minecraft.player, i)) {
                    Minecraft.getInstance().getSoundManager()
                            .play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                    this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, i);
                    return true;
                }
            }
            
            if ((event.x() >= initialScrollPosX) && (event.x() < (initialScrollPosX + WoodWorkingScreen.SCROLLER_WIDTH))
                    && (event.y() >= initialScrollPosY) && (event.y() < maxScrollPosY)) {
                this.scrolling = true;
            }
        }
        return super.mouseClicked(event, isDoubleClick);
    }
    
    @Override
    public boolean mouseDragged(MouseButtonEvent event, double mouseX, double mouseY) {
        if (this.scrolling && this.isScrollBarActive()) {
            final int initialScrollPosY = this.topPos + WoodWorkingScreen.SCROLLER_START_Y;
            final int maxScrollPosY = initialScrollPosY + WoodWorkingScreen.SCROLLER_CONTENT_SIZE;
            this.scrollOffs = ((float) event.y() - initialScrollPosY - 7.5f)
                    / (maxScrollPosY - initialScrollPosY - 15.0f);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0f);
            this.startIndex = (int) ((this.scrollOffs * this.getOffscreenRows()) + 0.5)
                    * WoodWorkingScreen.RECIPES_COLUMNS;
            return true;
        } else {
            return super.mouseDragged(event, mouseX, mouseY);
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
    
    private void renderButtons(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        final int initialPosX = this.leftPos + WoodWorkingScreen.RESULT_START_X;
        final int initialPosY = this.topPos + WoodWorkingScreen.RESULT_START_Y;
        final int indexLastVisibleElement = this.startIndex
                + (WoodWorkingScreen.RECIPES_COLUMNS * WoodWorkingScreen.RECIPES_ROWS);
        final List<RecipeHolder<WoodWorkingRecipe>> visibleRecipes = this.menu.getVisibleRecipes();
        
        for (int i = this.startIndex; (i < indexLastVisibleElement)
                && (i < this.menu.getNumberOfVisibleRecipes()); ++i) {
            final ItemStack resultToDisplay = visibleRecipes.get(i).value().getResultItem();
            final int indexInScreen = i - this.startIndex;
            final int posIngredientX = initialPosX
                    + ((indexInScreen % WoodWorkingScreen.RECIPES_COLUMNS) * WoodWorkingScreen.RECIPES_IMAGE_SIZE);
            final int row = indexInScreen / WoodWorkingScreen.RECIPES_COLUMNS;
            final int posIngredientY = initialPosY + (row * WoodWorkingScreen.RECIPES_IMAGE_SIZE);
            
            int posImageX = 0;
            if (i == this.menu.getSelectedRecipeIndex()) {
                posImageX = 36;
            } else if ((mouseX >= posIngredientX) && (mouseY >= posIngredientY)
                    && (mouseX < (posIngredientX + WoodWorkingScreen.RECIPES_IMAGE_SIZE))
                    && (mouseY < (posIngredientY + WoodWorkingScreen.RECIPES_IMAGE_SIZE))) {
                posImageX = 18;
            }
            graphics.blit(RenderPipelines.GUI_TEXTURED, WoodWorkingScreen.TEXTURE, posIngredientX, posIngredientY,
                    posImageX, 166, WoodWorkingScreen.RECIPES_IMAGE_SIZE, WoodWorkingScreen.RECIPES_IMAGE_SIZE, 256,
                    256);
            graphics.item(resultToDisplay, posIngredientX + 1, posIngredientY + 1);
        }
    }
    
}