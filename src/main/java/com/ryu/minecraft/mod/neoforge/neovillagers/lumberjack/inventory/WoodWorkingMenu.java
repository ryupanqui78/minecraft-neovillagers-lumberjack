package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.inventory.slots.ResultSingleSlot;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting.WoodWorkingRecipe;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.network.WoodWorkingRecipes;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupBlocks;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupMenus;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

public class WoodWorkingMenu extends AbstractContainerMenu {
    
    public static final String MENU_NAME = "woodworking";
    
    private static final int RESULT_SLOT = 1;
    private static final int INV_SLOT_START = 2;
    private static final int USE_ROW_SLOT_END = 38;
    
    private final ContainerLevelAccess access;
    private final Slot inputSlot;
    private final Level level;
    private final Slot resultSlot;
    private final ResultContainer resultContainer = new ResultContainer();
    private final DataSlot selectedRecipeIndex = DataSlot.standalone();
    
    private ItemStack input = ItemStack.EMPTY;
    private long lastSoundTime;
    private List<RecipeHolder<WoodWorkingRecipe>> recipesForInput = new ArrayList<>();
    
    Runnable slotUpdateListener = () -> {
    };
    
    public final Container container = new SimpleContainer(1) {
        {
            Objects.requireNonNull(WoodWorkingMenu.this);
        }
        
        @Override
        public void setChanged() {
            super.setChanged();
            WoodWorkingMenu.this.slotsChanged(this);
            WoodWorkingMenu.this.slotUpdateListener.run();
        }
    };
    
    public WoodWorkingMenu(int pContainerId, Inventory playerInv) {
        this(pContainerId, playerInv, ContainerLevelAccess.NULL);
    }
    
    public WoodWorkingMenu(int pContainerId, Inventory inventory, ContainerLevelAccess pAccess) {
        super(SetupMenus.WOODWORKING_CONTAINER.get(), pContainerId);
        this.access = pAccess;
        this.level = inventory.player.level();
        this.inputSlot = this.addSlot(new Slot(this.container, 0, 16, 33));
        this.resultSlot = this
                .addSlot(new ResultSingleSlot(this.resultContainer, WoodWorkingMenu.RESULT_SLOT, 140, 33, this));
        
        this.addInventorySlots(inventory);
        this.addDataSlot(this.selectedRecipeIndex);
    }
    
    protected void addInventorySlots(Inventory inventory) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + (i * 9) + 9, 8 + (j * 18), 84 + (i * 18)));
            }
        }
        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(inventory, k, 8 + (k * 18), 142));
        }
    }
    
    @Override
    public boolean canTakeItemForPickAll(ItemStack carried, Slot target) {
        return (target.container != this.resultContainer) && super.canTakeItemForPickAll(carried, target);
    }
    
    @Override
    public boolean clickMenuButton(Player player, int buttonId) {
        if (this.selectedRecipeIndex.get() == buttonId) {
            return false;
        }
        
        if (this.isValidRecipeIndex(buttonId)) {
            this.selectedRecipeIndex.set(buttonId);
            this.setupResultSlot(buttonId);
        }
        return true;
    }
    
    public void executeAccess() {
        this.access.execute((_, pos) -> {
            final long gameTime = this.level.getGameTime();
            if (this.lastSoundTime != gameTime) {
                this.level.playSound(null, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
                this.lastSoundTime = gameTime;
            }
        });
    }
    
    public ContainerLevelAccess getAccess() {
        return this.access;
    }
    
    public Slot getInputSlot() {
        return this.inputSlot;
    }
    
    public int getNumberOfVisibleRecipes() {
        return this.recipesForInput.size();
    }
    
    public ResultContainer getResultContainer() {
        return this.resultContainer;
    }
    
    public int getSelectedRecipeIndex() {
        return this.selectedRecipeIndex.get();
    }
    
    public List<RecipeHolder<WoodWorkingRecipe>> getVisibleRecipes() {
        return this.recipesForInput;
    }
    
    public boolean hasInputItem() {
        return this.inputSlot.hasItem() && !this.recipesForInput.isEmpty();
    }
    
    private boolean isValidRecipeIndex(int buttonId) {
        return (buttonId >= 0) && (buttonId < this.recipesForInput.size());
    }
    
    private boolean quickMoveInventory(int slotIndex, ItemStack stack) {
        if ((slotIndex >= WoodWorkingMenu.INV_SLOT_START) && (slotIndex < 29)) {
            if (!this.moveItemStackTo(stack, 29, WoodWorkingMenu.USE_ROW_SLOT_END, false)) {
                return true;
            }
        } else if ((slotIndex >= 29) && (slotIndex < WoodWorkingMenu.USE_ROW_SLOT_END)
                && !this.moveItemStackTo(stack, WoodWorkingMenu.INV_SLOT_START, 29, false)) {
            return true;
        }
        return false;
    }
    
    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack clicked = ItemStack.EMPTY;
        final Slot slot = this.slots.get(slotIndex);
        if (!slot.hasItem()) {
            return clicked;
        }
        
        final ItemStack stack = slot.getItem();
        final Item item = stack.getItem();
        clicked = stack.copy();
        if (slotIndex == WoodWorkingMenu.RESULT_SLOT) {
            item.onCraftedBy(stack, player);
            if (!this.moveItemStackTo(stack, WoodWorkingMenu.INV_SLOT_START, WoodWorkingMenu.USE_ROW_SLOT_END, true)) {
                return ItemStack.EMPTY;
            }
            
            slot.onQuickCraft(stack, clicked);
        } else if (slotIndex == 0) {
            if (!this.moveItemStackTo(stack, WoodWorkingMenu.INV_SLOT_START, WoodWorkingMenu.USE_ROW_SLOT_END, false)) {
                return ItemStack.EMPTY;
            }
        } else if (WoodWorkingRecipes.inputs(this.level).test(stack)
                && !this.moveItemStackTo(stack, 0, WoodWorkingMenu.RESULT_SLOT, false)) {
            return ItemStack.EMPTY;
        } else if (this.quickMoveInventory(slotIndex, stack)) {
            return ItemStack.EMPTY;
        }
        
        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        }
        
        slot.setChanged();
        if (stack.getCount() == clicked.getCount()) {
            return ItemStack.EMPTY;
        }
        
        slot.onTake(player, stack);
        if (slotIndex == WoodWorkingMenu.RESULT_SLOT) {
            player.drop(stack, false);
        }
        this.broadcastChanges();
        
        return clicked;
    }
    
    public void registerUpdateListener(Runnable slotUpdateListener) {
        this.slotUpdateListener = slotUpdateListener;
    }
    
    @Override
    public void removed(Player player) {
        super.removed(player);
        this.resultContainer.removeItemNoUpdate(1);
        this.access.execute((_, _) -> this.clearContainer(player, this.container));
    }
    
    public void setupResultSlot(int index) {
        Optional<RecipeHolder<WoodWorkingRecipe>> usedRecipe;
        if (!this.recipesForInput.isEmpty() && this.isValidRecipeIndex(index)) {
            usedRecipe = Optional.ofNullable(this.recipesForInput.get(index));
        } else {
            usedRecipe = Optional.empty();
        }
        
        usedRecipe.ifPresentOrElse(recipe -> {
            this.resultContainer.setRecipeUsed(recipe);
            this.resultSlot.set(recipe.value().assemble(new SingleRecipeInput(this.container.getItem(0))));
        }, () -> {
            this.resultSlot.set(ItemStack.EMPTY);
            this.resultContainer.setRecipeUsed(null);
        });
        this.broadcastChanges();
    }
    
    @Override
    public void slotsChanged(Container container) {
        final ItemStack inputTmp = this.inputSlot.getItem();
        if (!inputTmp.is(this.input.getItem())) {
            this.input = inputTmp.copy();
            this.selectedRecipeIndex.set(-1);
            this.resultSlot.set(ItemStack.EMPTY);
            if (!inputTmp.isEmpty()) {
                this.recipesForInput = WoodWorkingRecipes.inputs(level).recipes().stream()
                        .filter(holder -> holder.value().input().test(inputTmp)).toList();
            } else {
                this.recipesForInput = new ArrayList<>();
            }
        }
    }
    
    @Override
    public boolean stillValid(Player pPlayer) {
        return AbstractContainerMenu.stillValid(this.access, pPlayer, SetupBlocks.WOODWORKING.get());
    }
}