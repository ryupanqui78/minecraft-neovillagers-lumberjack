package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.NeoVillagersLumberjack;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.inventory.WoodWorkingMenu;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupMenus {
    
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU,
            NeoVillagersLumberjack.MODID);
    
    public static final DeferredHolder<MenuType<?>, MenuType<WoodWorkingMenu>> WOODWORKING_CONTAINER = SetupMenus.MENUS
            .register(WoodWorkingMenu.MENU_NAME,
                    () -> new MenuType<WoodWorkingMenu>(WoodWorkingMenu::new, FeatureFlags.DEFAULT_FLAGS));
    
    private SetupMenus() {
    }
}
