package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.inventory;

import java.util.List;

import com.google.common.collect.Lists;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting.WoodWorkingRecipe;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupBlocks;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupMenus;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupRecipeType;

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
    
    private static final int INV_SLOT_START = 2;
    private static final int USE_ROW_SLOT_END = 38;
    
    private final DataSlot selectedRecipeIndex = DataSlot.standalone();
    private final ContainerLevelAccess access;
    private final Level level;
    private final Slot inputSlot;
    private final Slot resultSlot;
    private final ResultContainer resultContainer = new ResultContainer();
    
    private ItemStack input = ItemStack.EMPTY;
    private long lastSoundTime;
    private List<RecipeHolder<WoodWorkingRecipe>> recipes = Lists.newArrayList();
    Runnable slotUpdateListener = () -> {
    };
    
    public final Container container = new SimpleContainer(1) {
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
    
    public WoodWorkingMenu(int pContainerId, Inventory playerInv, ContainerLevelAccess pAccess) {
        super(SetupMenus.WOODWORKING_CONTAINER.get(), pContainerId);
        this.access = pAccess;
        this.level = playerInv.player.level();
        this.inputSlot = this.addSlot(new Slot(this.container, 0, 20, 33));
        this.resultSlot = this.addSlot(new Slot(this.resultContainer, 1, 143, 33) {
            
            private List<ItemStack> getRelevantItems() {
                return List.of(WoodWorkingMenu.this.inputSlot.getItem());
            }
            
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return false;
            }
            
            @Override
            public void onTake(Player pPlayer, ItemStack pStack) {
                pStack.onCraftedBy(pPlayer.level(), pPlayer, pStack.getCount());
                WoodWorkingMenu.this.resultContainer.awardUsedRecipes(pPlayer, this.getRelevantItems());
                final ItemStack itemstack = WoodWorkingMenu.this.inputSlot.remove(1);
                if (!itemstack.isEmpty()) {
                    WoodWorkingMenu.this.setupResultSlot();
                }
                
                pAccess.execute((pLevel, pPos) -> {
                    final long l = pLevel.getGameTime();
                    if (WoodWorkingMenu.this.lastSoundTime != l) {
                        pLevel.playSound(null, pPos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0F,
                                1.0F);
                        WoodWorkingMenu.this.lastSoundTime = l;
                    }
                });
                super.onTake(pPlayer, pStack);
            }
        });
        
        this.addInventorySlots(playerInv);
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
    public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
        return (pSlot.container != this.resultContainer) && super.canTakeItemForPickAll(pStack, pSlot);
    }
    
    @Override
    public boolean clickMenuButton(Player pPlayer, int pId) {
        if (this.isValidRecipeIndex(pId)) {
            this.selectedRecipeIndex.set(pId);
            this.setupResultSlot();
        }
        
        return true;
    }
    
    public int getNumRecipes() {
        return this.recipes.size();
    }
    
    public List<RecipeHolder<WoodWorkingRecipe>> getRecipes() {
        return this.recipes;
    }
    
    public int getSelectedRecipeIndex() {
        return this.selectedRecipeIndex.get();
    }
    
    public boolean hasInputItem() {
        return this.inputSlot.hasItem() && !this.recipes.isEmpty();
    }
    
    private boolean isValidRecipeIndex(int pRecipeIndex) {
        return (pRecipeIndex >= 0) && (pRecipeIndex < this.recipes.size());
    }
    
    private boolean quickMoveInventory(int pIndex, ItemStack itemstack1) {
        if ((pIndex >= WoodWorkingMenu.INV_SLOT_START) && (pIndex < 29)) {
            if (!this.moveItemStackTo(itemstack1, 29, WoodWorkingMenu.USE_ROW_SLOT_END, false)) {
                return true;
            }
        } else if ((pIndex >= 29) && (pIndex < WoodWorkingMenu.USE_ROW_SLOT_END)
                && !this.moveItemStackTo(itemstack1, WoodWorkingMenu.INV_SLOT_START, 29, false)) {
            return true;
        }
        return false;
    }
    
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        final Slot slot = this.slots.get(pIndex);
        if (!slot.hasItem()) {
            return itemstack;
        }
        
        final ItemStack itemstack1 = slot.getItem();
        final Item item = itemstack1.getItem();
        itemstack = itemstack1.copy();
        if (pIndex == 1) {
            item.onCraftedBy(itemstack1, pPlayer.level(), pPlayer);
            if (!this.moveItemStackTo(itemstack1, WoodWorkingMenu.INV_SLOT_START, WoodWorkingMenu.USE_ROW_SLOT_END,
                    true)) {
                return ItemStack.EMPTY;
            }
            
            slot.onQuickCraft(itemstack1, itemstack);
        } else if (pIndex == 0) {
            if (!this.moveItemStackTo(itemstack1, WoodWorkingMenu.INV_SLOT_START, WoodWorkingMenu.USE_ROW_SLOT_END,
                    false)) {
                return ItemStack.EMPTY;
            }
        } else if (this.level.getRecipeManager()
                .getRecipeFor(SetupRecipeType.WOODWORKING.get(), new SingleRecipeInput(itemstack1), this.level)
                .isPresent()) {
            if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                return ItemStack.EMPTY;
            }
        } else if (this.quickMoveInventory(pIndex, itemstack1)) {
            return ItemStack.EMPTY;
        }
        
        if (itemstack1.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        }
        
        slot.setChanged();
        if (itemstack1.getCount() == itemstack.getCount()) {
            return ItemStack.EMPTY;
        }
        
        slot.onTake(pPlayer, itemstack1);
        this.broadcastChanges();
        
        return itemstack;
    }
    
    public void registerUpdateListener(Runnable pListener) {
        this.slotUpdateListener = pListener;
    }
    
    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.resultContainer.removeItemNoUpdate(1);
        this.access.execute((pLevel, pPos) -> this.clearContainer(pPlayer, this.container));
    }
    
    private static SingleRecipeInput createRecipeInput(Container container) {
        return new SingleRecipeInput(container.getItem(0));
    }
    
    private void setupRecipeList(Container pContainer, ItemStack pStack) {
        this.recipes.clear();
        this.selectedRecipeIndex.set(-1);
        this.resultSlot.set(ItemStack.EMPTY);
        if (!pStack.isEmpty()) {
            this.recipes = this.level.getRecipeManager().getRecipesFor(SetupRecipeType.WOODWORKING.get(),
                    createRecipeInput(pContainer), this.level);
        }
    }
    
    protected void setupResultSlot() {
        if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get())) {
            final RecipeHolder<WoodWorkingRecipe> recipeholder = this.recipes.get(this.selectedRecipeIndex.get());
            final ItemStack itemstack = recipeholder.value().assemble(createRecipeInput(this.container),
                    this.level.registryAccess());
            if (itemstack.isItemEnabled(this.level.enabledFeatures())) {
                this.resultContainer.setRecipeUsed(recipeholder);
                this.resultSlot.set(itemstack);
            } else {
                this.resultSlot.set(ItemStack.EMPTY);
            }
        } else {
            this.resultSlot.set(ItemStack.EMPTY);
        }
        
        this.broadcastChanges();
    }
    
    @Override
    public void slotsChanged(Container pInventory) {
        final ItemStack itemstack = this.inputSlot.getItem();
        if (!itemstack.is(this.input.getItem())) {
            this.input = itemstack.copy();
            this.setupRecipeList(pInventory, itemstack);
        }
    }
    
    @Override
    public boolean stillValid(Player pPlayer) {
        return AbstractContainerMenu.stillValid(this.access, pPlayer, SetupBlocks.WOODWORKING_TABLE.get());
    }
}