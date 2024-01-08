package net.jeserkon.dislocatormod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EscapeRopeItem extends Item implements ITeleporter {
    public EscapeRopeItem(Properties pProperties) { super(pProperties); }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (pLevel instanceof ServerLevel && pPlayer.canChangeDimensions() && !pPlayer.isOnPortalCooldown()) {
            ResourceKey<Level> resourcekey = pLevel.dimension() == Level.END ? Level.OVERWORLD : Level.END;
            ServerLevel serverlevel = ((ServerLevel) pLevel).getServer().getLevel(resourcekey);
            if (serverlevel == null) {
                return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
            }

            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.LEASH_KNOT_PLACE, SoundSource.PLAYERS, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));

            if (!(pLevel.dimension() == Level.OVERWORLD)) {
                ServerLevel serverlevel1 = ((ServerLevel) pLevel).getServer().getLevel(Level.OVERWORLD);
                pPlayer.teleportTo(serverlevel1, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), RelativeMovement.ALL, pPlayer.getYRot(),pPlayer.getXRot());
                pLevel = ((ServerPlayer) pPlayer).serverLevel();
                for (int k = pLevel.getMaxBuildHeight() - 1; k > pLevel.getMinBuildHeight(); --k) {
                    BlockPos blockPos = new BlockPos((int) pPlayer.getX(), k, (int) pPlayer.getZ());
                    BlockState blockstate = pLevel.getBlockState(blockPos);
                    if (blockstate.isCollisionShapeFullBlock(pLevel, blockPos) && (!blockstate.is(Blocks.BEDROCK))) {
                        pPlayer.teleportTo(blockPos.getX(),k+1,blockPos.getZ());
                        pPlayer.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100));
                        pPlayer.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 100));
                        break;
                    }
                }
            }

            pPlayer.awardStat(Stats.ITEM_USED.get(this));
        }

        pPlayer.setPortalCooldown();

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.dislocatormod.escape_rope.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }


}
