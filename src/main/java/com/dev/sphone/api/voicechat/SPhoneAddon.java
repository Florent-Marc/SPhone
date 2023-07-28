package com.dev.sphone.api.voicechat;

import com.dev.sphone.mod.common.packets.client.PacketCall;
import com.dev.sphone.mod.common.register.ItemsRegister;
import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.*;
import com.dev.sphone.SPhone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.server.FMLServerHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ForgeVoicechatPlugin
public class SPhoneAddon implements VoicechatPlugin {

    public static VoicechatServerApi api;

    @Override
    public String getPluginId() {
        return "SPhoneAddon";
    }

    @Override
    public void initialize(VoicechatApi api) {
        System.out.println("SPhone VoiceChat Addon Initialized");
    }

    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(VoicechatServerStartedEvent.class, this::onServerStarted, 100);
    }

    public void onServerStarted(VoicechatServerStartedEvent e) {
        api = e.getVoicechat();
    }

    public static List<State> d = new ArrayList<>();

    //get state with player and return id of list
    public static int getState(EntityPlayerMP p) {
        for (State s : d) {
            if (s.getPlayer().equals(p)) {
                return d.indexOf(s);
            }
        }
        return -1;
    }
    public static HashMap<String, Group> GroupMap = new HashMap<String, Group>();
    public void j(JoinGroupEvent e) {
        EntityPlayerMP p = (EntityPlayerMP) e.getConnection().getPlayer();
        Group g = e.getGroup();
        for (State s : d) {
            if (s.getGroup().equals(g.getName())) {
                d.add(new State(p, "call", g.getName()));
                d.set(getState(s.getPlayer()), new State(s.getPlayer(), "call", g.getName()));
                SPhone.network.sendTo(new PacketCall(1,g.getName()), p);
                SPhone.network.sendTo(new PacketCall(1,g.getName()), s.getPlayer());
                return;
            }
        }
        if(!checkGroup(g.getName())){
            //close call p
            SPhone.network.sendTo(new PacketCall(0), p);
        }

    }
    //leave group event

    public void l(LeaveGroupEvent e) {
        EntityPlayerMP p = (EntityPlayerMP) e.getConnection().getPlayer();
        Group g = e.getGroup();
        for (State s : d) {
            if (s.getGroup().equals(g.getName())) {
                //remove from list
                d.remove(getState(s.getPlayer()));
                d.remove(getState(p));
                //disconnect from call
                SPhone.network.sendTo(new PacketCall(0), s.getPlayer());
                return;
            }
        }
    }
    //method for check every player has a item phone with a group
    public static boolean checkGroup(String group) {
        List<EntityPlayerMP> g = FMLServerHandler.instance().getServer().getPlayerList().getPlayers();
        for (EntityPlayerMP s : g) {
            if (s.inventory.hasItemStack(new ItemStack(ItemsRegister.ITEM_PHONE))){
                //check if item have nbt tag numero
                if (s.inventory.getStackInSlot(s.inventory.getSlotFor(new ItemStack(ItemsRegister.ITEM_PHONE))).getTagCompound().hasKey("numero")) {
                    //check if item have nbt tag group
                    if(s.inventory.getStackInSlot(s.inventory.getSlotFor(new ItemStack(ItemsRegister.ITEM_PHONE))).getTagCompound().getString("numero").equals(group)) {
                        //packet call enter
                        return true;
                    }
                }
            }

        }
        return false;
    }
    public static void closeCall(String numero) {
        for (State s : d) {
            if (s.getGroup().equals(numero)) {
                //remove from list
                d.remove(getState(s.getPlayer()));
                //disconnect from call
                SPhone.network.sendTo(new PacketCall(0), s.getPlayer());
                return;
            }
        }
    }

    //methode for get tag group of item phone

    public static String getGroup(EntityPlayer p) {
        if (p.inventory.hasItemStack(new ItemStack(ItemsRegister.ITEM_PHONE))) {
            if (p.inventory.getStackInSlot(p.inventory.getSlotFor(new ItemStack(ItemsRegister.ITEM_PHONE))).getTagCompound().hasKey("numero")) {
                return p.inventory.getStackInSlot(p.inventory.getSlotFor(new ItemStack(ItemsRegister.ITEM_PHONE))).getTagCompound().getString("numero");
            }
        }
        return null;
    }

    public static void createGroup (String name, Boolean persistent, Group.Type type) {
        //random password
        String password = "";
        for (int i = 0; i < 10; i++) {
            password += (char) (Math.random() * 26 + 97);
        }
        if (!GroupMap.containsKey(name)) {
            Group g = api.groupBuilder()
                    .setName(name)
                    .setPersistent(persistent)
                    .setType(type)
                    .setPassword(password)
                    .build();
            GroupMap.put(name, g);
        }
    }

    public static void addToGroup(String name, EntityPlayer player) {
        if (GroupMap.containsKey(name)) {
            VoicechatConnection connection = api.getConnectionOf(player.getUniqueID());
            if (connection != null) {
                Group g = GroupMap.get(name);
                connection.setGroup(g);
            }
        }
    }

    public static void removeFromActualGroup(EntityPlayer player) {
        VoicechatConnection connection = api.getConnectionOf(player.getUniqueID());
        if (connection != null) {
            connection.setGroup(null);
        }
    }

    //check if group exists
    public static boolean groupExists(String name) {
        return GroupMap.containsKey(name);
    }
    //check if player is in a group
    public static boolean isInGroup(EntityPlayer player) {
        VoicechatConnection connection = api.getConnectionOf(player.getUniqueID());
        if (connection == null) {
            return false;
        } else {
            return connection.getGroup() != null;
        }
    }

}
