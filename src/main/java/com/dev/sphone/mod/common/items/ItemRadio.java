package com.dev.sphone.mod.common.items;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.client.gui.phone.apps.GuiRadio;
import com.dev.sphone.mod.common.packets.client.PacketTuneRadio;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;

import javax.annotation.Nullable;
import java.util.List;

public class ItemRadio extends Item {

    public static final String FREQ_KEY_TAG = "radioFreq";
    public static final String IN_USE_KEY_TAG = "radioInUse";
    public static final int MIN_FREQ = 1;
    public static final int MAX_FREQ = 1000;

    public static ItemRadio INSTANCE;

    public ItemRadio(String name, CreativeTabs creativeTabs, int stackcount) {
        this.setTranslationKey(name);
        this.setRegistryName(SPhone.MOD_ID, name);
        this.setCreativeTab(creativeTabs);
        this.setMaxStackSize(stackcount);
        INSTANCE = this;
    }

    @Override
public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);

    // Apenas em MAIN_HAND e agachado
    if (player.isSneaking() && hand == EnumHand.MAIN_HAND) {
        // Executar apenas no cliente
        if (world.isRemote) {
            SPhone.proxy.openRadioGui();
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    // Clique normal: lógica de mensagem / envio de pacote
    if (!world.isRemote) {
        int freq = getFrequency(stack);
        // ... validar frequência e, se válida, enviar pacote ao servidor:
        if (freq > 0) {
            //SPhone.network.sendToServer(new PacketTuneRadio(freq));
            //player.sendStatusMessage(
            //    new TextComponentString(TextFormatting.GREEN + "Conectando à frequência: " + freq), true);
        } else {
           // player.sendStatusMessage(
                // new TextComponentString(TextFormatting.RED + "Configure uma frequência antes de usar o rádio!"), true);
        }
    }
    return new ActionResult<>(EnumActionResult.SUCCESS, stack);
}

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flagIn) {
        int freq = getFrequency(stack);
        if (freq > 0) {
         //   tooltip.add(TextFormatting.AQUA + "Frequência: " + freq);
        } else {
            tooltip.add(TextFormatting.RED + "Frequência não configurada");
        }
        tooltip.add(TextFormatting.GRAY + "Shift + botão direito para ajustar frequência.");
        super.addInformation(stack, world, tooltip, flagIn);
    }

    public static int getFrequency(ItemStack stack) {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        return stack.getTagCompound().getInteger(FREQ_KEY_TAG);
    }

    public static void setFrequency(ItemStack stack, int freq) {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger(FREQ_KEY_TAG, freq);
    }

    public static boolean isInUse(ItemStack stack) {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        return stack.getTagCompound().getBoolean(IN_USE_KEY_TAG);
    }

    public static void setInUse(ItemStack stack, boolean inUse) {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setBoolean(IN_USE_KEY_TAG, inUse);
    }
    
    /**
     * Updates the lore of the radio to indicate if it is muted or active.
     */
    public static void setMutedLore(ItemStack stack, boolean muted) {
        if (stack == null || stack.isEmpty()) return;
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        NBTTagCompound tag = stack.getTagCompound();

        NBTTagCompound display = tag.getCompoundTag("display");
        if (!tag.hasKey("display")) {
            display = new NBTTagCompound();
        }

        net.minecraft.nbt.NBTTagList lore = new net.minecraft.nbt.NBTTagList();
        lore.appendTag(new net.minecraft.nbt.NBTTagString(muted ? "Mutado" : "Na escuta"));

        display.setTag("Lore", lore);
        tag.setTag("display", display);
        stack.setTagCompound(tag);
    }
}
