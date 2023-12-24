package net.jeserkon.dislocatormod.datagen;

import net.jeserkon.dislocatormod.DislocatorMod;
import net.jeserkon.dislocatormod.block.ModBlocks;
import net.jeserkon.dislocatormod.item.Moditems;
import net.jeserkon.dislocatormod.loot.AddItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, DislocatorMod.MOD_ID);
    }

    @Override
    protected void start() {
        add("escape_rope_from_enderdragon", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/ender_dragon")).build()}, Moditems.ESCAPE_ROPE.get()));

        add("escape_rope_from_enderdragon2", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/ender_dragon")).build()}, Moditems.ESCAPE_ROPE.get()));

        add("return_stone_from_enderman", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/phantom")).build(),
                LootItemKilledByPlayerCondition.killedByPlayer().build(),
                LootItemRandomChanceCondition.randomChance(0.40f).build()}, Moditems.RETURN_STONE.get()));

        add("dislocator_from_village_temple", new AddItemModifier (new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_temple")).build()}, ModBlocks.DISLOCATOR_BLOCK.get().asItem()));

        add("dislocator_from_jungle_temples", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("chests/jungle_temple")).build() }, ModBlocks.DISLOCATOR_BLOCK.get().asItem()));

        add("dislocator_from_desert_temples", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("chests/desert_pyramid")).build() }, ModBlocks.DISLOCATOR_BLOCK.get().asItem()));
    }
}
