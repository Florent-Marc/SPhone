package com.dev.sphone.mod.common.items;

import com.dev.sphone.api.events.SimRegisterEvent;
import com.dev.sphone.mod.common.register.ItemsRegister;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import com.dev.sphone.mod.utils.UtilsServer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

public class ItemSim extends Item {

    public static final String SIM_KEY_TAG = "simcard";
    public static final String NUM_KEY_TAG = "numero";

    public ItemSim(String name, CreativeTabs creativeTabs, int stackcount) {
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(creativeTabs);
        this.setMaxStackSize(stackcount);
        ItemsRegister.INSTANCE.getItems().add(this);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote && hand == EnumHand.MAIN_HAND) {
            ItemStack stack = player.getHeldItem(hand);

            if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("simcard")) {
                int sim = UtilsServer.getRandomNumber(1000, 9999);
                int num = UtilsServer.getRandomNumber(10000, 99999);
                boolean isExist = false;
                if (MethodesBDDImpl.getDatabaseInstance().addSim(sim, String.valueOf(num))) {
                    isExist = true;
                } else {
                    for (int j = 0; j < 50; j++) {
                        sim = UtilsServer.getRandomNumber(1000, 9999);
                        num = UtilsServer.getRandomNumber(10000, 99999);
                        if (MethodesBDDImpl.getDatabaseInstance().addSim(sim, String.valueOf(num))) {
                            isExist = true;
                            break;
                        }
                    }
                    if (!isExist) {
                        UtilsServer.sendErrorChat(player, "Il semblerai que la limite de sim soit atteinte, veuillez contacter un administrateur.", false);
                    }
                }
                if (isExist) {
                    MinecraftForge.EVENT_BUS.post(new SimRegisterEvent(player, String.valueOf(sim), String.valueOf(num)));
                    setSimCard(player, stack, sim);
                    setNumero(player, stack, String.valueOf(num));

                }
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("-> Carte SIM N° " +  getSimCard(stack) + ""); // TODO: Traduction
        tooltip.add("");
        super.addInformation(stack, world, tooltip, flagIn);
    }

    public static boolean isSIM(ItemStack stack) {
        return stack.getItem() == ItemsRegister.SIM_CARD;
    }



    public static NBTTagCompound getTagCompound(ItemStack stack) {
        NBTTagCompound nbt;
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        nbt = stack.getTagCompound();
        return nbt;
    }

    public static void setSimCard(EntityPlayer player, ItemStack stack, int sim) {
        if (!isSIM(stack)) return;
        NBTTagCompound nbt = getTagCompound(stack);
        nbt.setInteger(SIM_KEY_TAG, sim);
        UtilsServer.sendActionChat(player, "Vous avez injecté la carte sim : " + sim, false);
    }

    public static int getSimCard(ItemStack stack) {
        if (isSIM(stack)) {
            NBTTagCompound nbt = getTagCompound(stack);
            if (nbt.hasKey(SIM_KEY_TAG)) return nbt.getInteger(SIM_KEY_TAG);
        }
        return 0;
    }

    public static void setNumero(EntityPlayer player, ItemStack stack, String s) {
        if (!isSIM(stack)) return;
        NBTTagCompound nbt = getTagCompound(stack);
        nbt.setString(NUM_KEY_TAG, s);
        UtilsServer.sendActionChat(player, "Vous avez injecté le numéro : " + s, false);
    }

    public static String getNumero(ItemStack stack) {
        if (isSIM(stack)) {
            NBTTagCompound nbt = getTagCompound(stack);
            if (nbt.hasKey(NUM_KEY_TAG)) return nbt.getString(NUM_KEY_TAG);
        }
        return null;
    }
}
