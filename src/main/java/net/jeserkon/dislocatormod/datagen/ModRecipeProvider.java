package net.jeserkon.dislocatormod.datagen;

import net.jeserkon.dislocatormod.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.UNIVERSAL_RESPAWNANCHOR_BLOCK.get())
                .pattern("   ")
                .pattern("EEE")
                .pattern("SSS")
                .define('E',Items.ENDER_PEARL)
                .define('S',Items.END_STONE_BRICKS)
                .unlockedBy(getHasName(Items.END_STONE_BRICKS), has(Items.END_STONE_BRICKS))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.WARPED_STEM, 2)
                .requires(Items.CHORUS_FLOWER)
                .unlockedBy(getHasName(Items.CHORUS_FLOWER), has(Items.CHORUS_FLOWER))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.WARPED_PLANKS,2)
                .pattern("CC")
                .pattern("CC")
                .define('C',Items.CHORUS_FRUIT)
                .unlockedBy(getHasName(Items.CHORUS_FRUIT), has(Items.CHORUS_FRUIT))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.COBBLESTONE,4)
                .pattern(" S ")
                .pattern("SCS")
                .pattern(" S ")
                .define('C',Items.CHORUS_FRUIT)
                .define('S',Items.END_STONE_BRICKS)
                .unlockedBy(getHasName(Items.END_STONE_BRICKS), has(Items.END_STONE_BRICKS))
                .save(pWriter);
    }

}
