
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package com.dev.sphone.mod.server.commands;

import com.dev.sphone.mod.common.items.ItemPhone;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

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
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1) {
            EntityPlayer player = (EntityPlayer) sender;
            ItemStack stack = player.getHeldItemMainhand();
            //get tag of stack
            NBTTagCompound tag = stack.getTagCompound();
            tag.setString("numero", args[0]);
            //set tag number to item
            stack.setTagCompound(tag);

            ItemPhone.setSimCard(player, stack, Integer.parseInt(args[0]));
        }
    }
}
