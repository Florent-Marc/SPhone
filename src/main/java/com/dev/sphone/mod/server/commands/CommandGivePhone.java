package com.dev.sphone.mod.server.commands;

import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.common.register.ItemsRegister;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandGivePhone extends CommandBase {
    @Override
    public String getName() {
        return "givephone";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/givephone <simcode>";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if(args[0].equals("test")) {
            EntityPlayer player = (EntityPlayer) sender;
            ItemStack stack = player.getHeldItem(player.getActiveHand());

            sender.sendMessage(new TextComponentString(stack.serializeNBT().toString()));
            return;
        }
        if (args.length == 1) {
            EntityPlayer player = (EntityPlayer) sender;
            ItemStack stack = new ItemStack(ItemsRegister.ITEM_PHONE);
            //get tag of stack
            NBTTagCompound tag = stack.getTagCompound();
            tag.setInteger("simcard", Integer.parseInt(args[0]));

            //tag.setInteger("numero", Integer.parseInt(args[1]));
            //set tag number to item
            stack.setTagCompound(tag);
            //give item to player
            player.addItemStackToInventory(stack);
            //set simcard to item

            ItemPhone.setSimCard(player, stack, Integer.parseInt(args[0]));
        }
    }
}
