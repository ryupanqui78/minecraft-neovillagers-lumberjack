package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class WoodWorkingSerialize implements RecipeSerializer<WoodWorkingRecipe> {
    
    private static final String ELEMENT_INGREDIENT = "ingredient";
    private static final String ELEMENT_GROUP = "group";
    
    @Override
    public Codec<WoodWorkingRecipe> codec() {
        return RecordCodecBuilder.create(elements -> elements
                .group(ExtraCodecs.strictOptionalField(Codec.STRING, WoodWorkingSerialize.ELEMENT_GROUP, "")
                        .forGetter(Recipe::getGroup),
                        Ingredient.CODEC_NONEMPTY.fieldOf(WoodWorkingSerialize.ELEMENT_INGREDIENT)
                                .forGetter(WoodWorkingRecipe::getIngredient),
                        ItemStack.RESULT_CODEC.forGetter(WoodWorkingRecipe::getResult))
                .apply(elements, WoodWorkingRecipe::new));
    }
    
    @Override
    public WoodWorkingRecipe fromNetwork(FriendlyByteBuf pBuffer) {
        final String group = pBuffer.readUtf();
        final Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
        final ItemStack itemstack = pBuffer.readItem();
        return new WoodWorkingRecipe(group, ingredient, itemstack);
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, WoodWorkingRecipe pRecipe) {
        pBuffer.writeUtf(pRecipe.getGroup());
        pRecipe.getIngredient().toNetwork(pBuffer);
        pBuffer.writeItem(pRecipe.getResult());
    }
}
