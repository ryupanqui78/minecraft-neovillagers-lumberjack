package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.villagers.trades;

import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;

public class ItemForEmeraldVillagerType implements VillagerTrades.ItemListing {
    private final Map<VillagerType, Item> trades;
    private final int cost;
    private final int maxUses;
    private final int villagerXp;
    private final int numberOfItems;
    
    public ItemForEmeraldVillagerType(int pCost, int pCount, int pMaxUses, int pVillagerXp, Map<VillagerType, Item> pTrades) {
        BuiltInRegistries.VILLAGER_TYPE.stream().filter(key -> !pTrades.containsKey(key)).findAny().ifPresent(ex -> {
            throw new IllegalStateException(
                    "Missing trade for villager type: " + BuiltInRegistries.VILLAGER_TYPE.getKey(ex));
        });
        this.trades = pTrades;
        this.cost = pCost;
        this.maxUses = pMaxUses;
        this.villagerXp = pVillagerXp;
        this.numberOfItems = pCount;
    }
    
    @Nullable
    @Override
    public MerchantOffer getOffer(Entity pTrader, RandomSource pRandom) {
        if (pTrader instanceof final VillagerDataHolder villagerdataholder) {
            final ItemStack itemCost = new ItemStack(Items.EMERALD, this.cost);
            final ItemStack itemstack = new ItemStack(this.trades.get(villagerdataholder.getVillagerData().getType()),
                    this.numberOfItems);
            return new MerchantOffer(itemCost, itemstack, this.maxUses, this.villagerXp, 0.05F);
        } else {
            return null;
        }
    }
}
