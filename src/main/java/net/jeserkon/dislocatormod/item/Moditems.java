package net.jeserkon.dislocatormod.item;

import net.jeserkon.dislocatormod.DislocatorMod;
import net.jeserkon.dislocatormod.item.custom.*;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Moditems {
    public static  final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DislocatorMod.MOD_ID);

    public static final RegistryObject<Item> RETURN_STONE = ITEMS.register("return_stone",
            () -> new ReturnStoneItem(new Item.Properties().durability(2)));
    public static final RegistryObject<Item> ESCAPE_ROPE = ITEMS.register("escape_rope",
            () -> new EscapeRopeItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
