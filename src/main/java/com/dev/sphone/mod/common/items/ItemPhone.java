package com.dev.sphone.mod.common.items;

import com.dev.sphone.SPhone;
import com.dev.sphone.api.events.SimRegisterEvent;
import com.dev.sphone.mod.client.tempdata.PhoneSettings;
import com.dev.sphone.mod.common.packets.client.PacketOpenPhone;
import com.dev.sphone.mod.common.register.ItemsRegister;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ItemPhone extends Item {

    public static final String SIM_KEY_TAG = "simcard";

    public ItemPhone(String name, CreativeTabs creativeTabs, int stackcount) {
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(creativeTabs);
        this.setMaxStackSize(stackcount);
        ItemsRegister.INSTANCE.getItems().add(this);
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote && hand == EnumHand.MAIN_HAND) {
            ItemStack stack = player.getHeldItem(hand);

            if (stack.hasTagCompound() && stack.getTagCompound().hasKey("simcard")) {
                SPhone.network.sendTo(new PacketOpenPhone("home", ""), (EntityPlayerMP) player);
            } else {
                int sim = getRandomNumber(1000, 9999);
                int num = getRandomNumber(10000, 99999);
                boolean isExist = false;
                if (MethodesBDDImpl.addSim(sim, String.valueOf(num))) {
                    isExist = true;
                } else {
                    for (int j = 0; j < 50; j++) {
                        sim = getRandomNumber(1000, 9999);
                        num = getRandomNumber(10000, 99999);
                        if (MethodesBDDImpl.addSim(sim, String.valueOf(num))) {
                            isExist = true;
                            break;
                        }
                    }
                    if (!isExist) {
                        sendErrorChat(player, "Il semblerai que la limite de sim soit atteinte, veuillez contacter un administrateur.", false);
                    }
                }
                if (isExist) {
                    MinecraftForge.EVENT_BUS.post(new SimRegisterEvent(player, String.valueOf(sim), String.valueOf(num)));
                    setSimCard(player, stack, sim);

                    getTagCompound(stack).setTag("settings", new PhoneSettings("acsgui").serializeNBT());
                }
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            tooltip.add("");
            tooltip.add("-> Version du Téléphone : " + SPhone.VERSION);
            tooltip.add("-> Mode Développeur : " + SPhone.DEV_MOD);
            tooltip.add("");
        } else {
            if (getSimCard(stack) == 0) {
                tooltip.add("-> Aucune Carte Sim Injecté");
            } else {
                tooltip.add("-> Carte Sim : " + getSimCard(stack));
            }
            tooltip.add("-> Batterie : 100%");
            tooltip.add("");
            tooltip.add("Appuyer sur SHIFT pour plus d'informations.");
        }
        super.addInformation(stack, world, tooltip, flagIn);
    }

    public static boolean isPhone(ItemStack stack) {
        return stack.getItem() == ItemsRegister.ITEM_PHONE;
    }

    public static NBTTagCompound getTagCompound(ItemStack stack) {
        NBTTagCompound nbt;
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        nbt = stack.getTagCompound();
        return nbt;
    }

    public static void setSimCard(EntityPlayer player, ItemStack stack, int sim) {
        if (!isPhone(stack)) return;
        NBTTagCompound nbt = getTagCompound(stack);
        nbt.setInteger(SIM_KEY_TAG, sim);
        sendActionChat(player, "Vous avez injecté la carte sim : " + sim, false);
    }

    public static int getSimCard(ItemStack stack) {
        if (isPhone(stack)) {
            NBTTagCompound nbt = getTagCompound(stack);
            if (nbt.hasKey(SIM_KEY_TAG)) return nbt.getInteger(SIM_KEY_TAG);
        }
        return 0;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static void sendActionChat(EntityPlayer player, String msg, Boolean actionbar) {
        player.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + msg), actionbar);
    }

    public static void sendErrorChat(EntityPlayer player, String msg, Boolean actionbar) {
        player.sendStatusMessage(new TextComponentString(TextFormatting.RED + msg), actionbar);
    }
}
