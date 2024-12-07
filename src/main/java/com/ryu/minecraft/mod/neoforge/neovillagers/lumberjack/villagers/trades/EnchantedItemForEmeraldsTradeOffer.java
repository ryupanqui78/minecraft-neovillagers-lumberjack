package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.villagers.trades;

import java.util.Optional;

import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

public class EnchantedItemForEmeraldsTradeOffer implements VillagerTrades.ItemListing {
    
    private final ItemStack itemSell;
    private final int baseEmeraldCost;
    private final int maxUses;
    private final int villagerXp;
    private final float priceMultiplier;
    
    public EnchantedItemForEmeraldsTradeOffer(Item itemSell, int baseEmeraldCost, int maxUses, int villagerXp) {
        this.itemSell = new ItemStack(itemSell);
        this.baseEmeraldCost = baseEmeraldCost;
        this.maxUses = maxUses;
        this.villagerXp = villagerXp;
        this.priceMultiplier = 0.2F;
    }
    
    @Override
    public MerchantOffer getOffer(Entity trader, RandomSource random) {
        final int i = 5 + random.nextInt(15);
        final RegistryAccess registryaccess = trader.level().registryAccess();
        final Optional<HolderSet.Named<Enchantment>> optional = registryaccess.lookupOrThrow(Registries.ENCHANTMENT)
                .get(EnchantmentTags.ON_TRADED_EQUIPMENT);
        final ItemStack itemstack = EnchantmentHelper.enchantItem(random, new ItemStack(this.itemSell.getItem()), i,
                registryaccess, optional);
        final int j = Math.min(this.baseEmeraldCost + i, 64);
        final ItemCost itemcost = new ItemCost(Items.EMERALD, j);
        return new MerchantOffer(itemcost, itemstack, this.maxUses, this.villagerXp, this.priceMultiplier);
    }
}
