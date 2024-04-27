package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.villagers.trades;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
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
    public MerchantOffer getOffer(Entity entity, RandomSource source) {
        final int i = 5 + source.nextInt(15);
        final ItemStack itemstack = EnchantmentHelper.enchantItem(source, new ItemStack(this.itemSell.getItem()), i,
                false);
        final int j = Math.min(this.baseEmeraldCost + i, 64);
        final ItemStack itemstack1 = new ItemStack(Items.EMERALD, j);
        return new MerchantOffer(itemstack1, itemstack, this.maxUses, this.villagerXp, this.priceMultiplier);
    }
    
}
