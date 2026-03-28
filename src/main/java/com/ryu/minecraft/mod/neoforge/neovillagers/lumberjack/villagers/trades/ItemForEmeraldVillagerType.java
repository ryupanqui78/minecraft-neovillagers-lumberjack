package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.villagers.trades;

import java.util.Map;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.VillagerDataHolder;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

public class ItemForEmeraldVillagerType implements VillagerTrades.ItemListing {
    
    private final Map<ResourceKey<VillagerType>, Item> trades;
    private final int cost;
    private final int maxUses;
    private final int villagerXp;
    private final int numberOfItems;
    
    public ItemForEmeraldVillagerType(int pCost, int pCount, int pMaxUses, int pVillagerXp, Map<ResourceKey<VillagerType>, Item> pTrades) {
        BuiltInRegistries.VILLAGER_TYPE.registryKeySet().stream().filter(key -> !pTrades.containsKey(key)).findAny()
                .ifPresent(ex -> {
                    throw new IllegalStateException("Missing trade for villager type: " + ex);
                });
        this.trades = pTrades;
        this.cost = pCost;
        this.maxUses = pMaxUses;
        this.villagerXp = pVillagerXp;
        this.numberOfItems = pCount;
    }
    
    @Override
    public @org.jspecify.annotations.Nullable MerchantOffer getOffer(ServerLevel level, Entity entity, RandomSource random) {
        if (entity instanceof final VillagerDataHolder villagerdataholder) {
            final ResourceKey<VillagerType> resourcekey = villagerdataholder.getVillagerData().type().unwrapKey()
                    .orElse(null);
            if (resourcekey == null) {
                return null;
            } else {
                final ItemCost itemCost = new ItemCost(Items.EMERALD, this.cost);
                final ItemStack itemstack = new ItemStack(this.trades.get(resourcekey), this.numberOfItems);
                return new MerchantOffer(itemCost, itemstack, this.maxUses, this.villagerXp, 0.05F);
            }
        } else {
            return null;
        }
    }
}
