/*
 * SITE-23 V2 - Tout droits réservés !
 */

package com.dev.sphone.mod.common.packets;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.packets.client.*;
import com.dev.sphone.mod.common.packets.server.*;
import com.dev.sphone.mod.common.packets.server.call.PacketCallRequest;
import com.dev.sphone.mod.common.packets.server.call.PacketCallResponse;
import com.dev.sphone.mod.common.packets.server.call.gabiwork.PacketAcceptRequest;
import com.dev.sphone.mod.common.packets.server.call.gabiwork.PacketQuitCall;
import com.dev.sphone.mod.common.packets.server.call.gabiwork.PacketSendRequestCall;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class Network {

    static int id = -1;
    public static void init(){
        SPhone.network = NetworkRegistry.INSTANCE.newSimpleChannel("sphone");
        // Global
        packetFromClient(PacketOpenPhone.Handler.class, PacketOpenPhone.class);
        packetFromClient(PacketOpenNotes.Handler.class, PacketOpenNotes.class);
        packetFromClient(PacketSendWeather.Handler.class, PacketSendWeather.class);
        packetFromClient(PacketOpenListConv.Handler.class, PacketOpenListConv.class);
        packetFromClient(PacketOpenContacts.Handler.class, PacketOpenContacts.class);
        packetFromClient(PacketOpenConvContact.Handler.class, PacketOpenConvContact.class);
        packetFromClient(PacketPlayerHudState.Handler.class, PacketPlayerHudState.class);
        packetFromClient(PacketOpenSIMGui.Handler.class, PacketOpenSIMGui.class);
        packetFromClient(PacketStopSound.Handler.class, PacketStopSound.class);

        // *** SERVERS *** //

        packetFromClient(PacketCall.Handler.class, PacketCall.class);
        packetFromServer(PacketCallRequest.ServerHandler.class, PacketCallRequest.class);
        packetFromServer(PacketCallResponse.ServerHandler.class, PacketCallResponse.class);
        packetFromServer(PacketSendRequestCall.ServerHandler.class, PacketSendRequestCall.class);

        packetFromServer(PacketRequestData.ServerHandler.class, PacketRequestData.class);
        packetFromServer(PacketEditNote.ServerHandler.class, PacketEditNote.class);
        packetFromServer(PacketEditContact.ServerHandler.class, PacketEditContact.class);
        packetFromServer(PacketGetUniqueConv.ServerHandler.class, PacketGetUniqueConv.class);

        packetFromServer(PacketSetAnim.ServerHandler.class, PacketSetAnim.class);
        packetFromServer(PacketSendMessage.ServerHandler.class, PacketSendMessage.class);
        packetFromServer(PacketAcceptRequest.ServerHandler.class, PacketAcceptRequest.class);

        packetFromServer(PacketQuitCall.ServerHandler.class, PacketQuitCall.class);

        packetFromServer(PacketManageApp.ServerHandler.class, PacketManageApp.class);
        packetFromServer(PacketSetBackground.ServerHandler.class, PacketSetBackground.class);

        // Contacts
        //packetFromServer(PacketGetContacts.ServerHandler.class, PacketGetContacts.class);
        //packetFromServer(PacketUpdateContacts.ServerHandler.class, PacketUpdateContacts.class);

    }

    public static void packetFromClient(Class handler, Class packet) {
        SPhone.network.registerMessage(handler, packet, id++, Side.CLIENT);
    }

    public static void packetFromServer(Class handler, Class packet) {
        SPhone.network.registerMessage(handler, packet, id++, Side.SERVER);
    }
}
