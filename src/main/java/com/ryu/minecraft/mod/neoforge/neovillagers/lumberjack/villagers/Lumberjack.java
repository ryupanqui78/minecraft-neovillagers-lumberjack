package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.villagers;

import com.google.common.collect.ImmutableMap;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.villagers.trades.EmeraldForItemTradeOffer;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.villagers.trades.EnchantedItemForEmeraldsTradeOffer;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.villagers.trades.ItemForEmeraldTradeOffer;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.villagers.trades.ItemForEmeraldVillagerType;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class Lumberjack extends Worker {
    
    public static final String ENTITY_NAME = "lumberjack";
    public static final String ENTITY_POI_NAME = "lumberjack_poi";
    
    @Override
    protected ItemListing[] getLevel1() {
        final ItemListing option1 = new ItemForEmeraldTradeOffer(Items.OAK_LOG, 1, 6, 12, 1);
        final ItemListing option2 = new EmeraldForItemTradeOffer(Items.OAK_LOG, 4, 12, 1);
        final ItemListing option3 = new ItemForEmeraldTradeOffer(Items.BIRCH_LOG, 1, 6, 12, 1);
        final ItemListing option4 = new EmeraldForItemTradeOffer(Items.BIRCH_LOG, 4, 12, 1);
        
        return new VillagerTrades.ItemListing[] { option1, option2, option3, option4, };
    }
    
    @Override
    protected ItemListing[] getLevel2() {
        final ItemListing option1 = new ItemForEmeraldTradeOffer(Items.OAK_LEAVES, 1, 4, 12, 4);
        final ItemListing option2 = new ItemForEmeraldTradeOffer(Items.BIRCH_LEAVES, 1, 4, 12, 4);
        final ItemListing option3 = new ItemForEmeraldTradeOffer(Items.WOODEN_AXE, 1, 1, 3, 8);
        
        return new VillagerTrades.ItemListing[] { option1, option2, option3, };
    }
    
    @Override
    protected ItemListing[] getLevel3() {
        final ItemListing option1 = new ItemForEmeraldTradeOffer(Items.DARK_OAK_LOG, 1, 3, 10, 6);
        final ItemListing option2 = new EmeraldForItemTradeOffer(Items.DARK_OAK_LOG, 2, 8, 6);
        final ItemListing option3 = new ItemForEmeraldTradeOffer(Items.STONE_AXE, 1, 1, 3, 12);
        final ItemListing option4 = new ItemForEmeraldTradeOffer(Items.DARK_OAK_LEAVES, 1, 4, 12, 8);
        
        return new VillagerTrades.ItemListing[] { option1, option2, option3, option4, };
    }
    
    @Override
    protected ItemListing[] getLevel4() {
        final ItemListing option1 = new EnchantedItemForEmeraldsTradeOffer(Items.IRON_AXE, 4, 3, 10);
        final ItemListing option2 = new ItemForEmeraldVillagerType(1, 3, 6, 10,
                ImmutableMap.<ResourceKey<VillagerType>, Item> builder().put(VillagerType.PLAINS, Items.CHERRY_LOG)
                        .put(VillagerType.TAIGA, Items.SPRUCE_LOG).put(VillagerType.SNOW, Items.SPRUCE_LOG)
                        .put(VillagerType.DESERT, Items.DEAD_BUSH).put(VillagerType.JUNGLE, Items.JUNGLE_LOG)
                        .put(VillagerType.SAVANNA, Items.ACACIA_LOG).put(VillagerType.SWAMP, Items.MANGROVE_LOG)
                        .build());
        final ItemListing option3 = new ItemForEmeraldVillagerType(1, 4, 4, 12,
                ImmutableMap.<ResourceKey<VillagerType>, Item> builder().put(VillagerType.PLAINS, Items.CHERRY_LEAVES)
                        .put(VillagerType.TAIGA, Items.SPRUCE_LEAVES).put(VillagerType.SNOW, Items.SPRUCE_LEAVES)
                        .put(VillagerType.DESERT, Items.DEAD_BUSH).put(VillagerType.JUNGLE, Items.JUNGLE_LEAVES)
                        .put(VillagerType.SAVANNA, Items.ACACIA_LEAVES).put(VillagerType.SWAMP, Items.MANGROVE_LEAVES)
                        .build());
        
        return new VillagerTrades.ItemListing[] { option1, option2, option3, };
    }
    
    @Override
    protected ItemListing[] getLevel5() {
        final ItemListing option1 = new ItemForEmeraldTradeOffer(Items.AZALEA, 1, 6, 12, 4);
        final ItemListing option2 = new EmeraldForItemTradeOffer(Items.CHERRY_LOG, 1, 6, 5);
        final ItemListing option3 = new EnchantedItemForEmeraldsTradeOffer(Items.DIAMOND_AXE, 17, 3, 15);
        final ItemListing option4 = new ItemForEmeraldTradeOffer(Items.AZALEA_LEAVES, 1, 4, 4, 6);
        
        return new VillagerTrades.ItemListing[] { option1, option2, option3, option4, };
    }
    
}
