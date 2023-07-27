/*
 * SITE-23 V2 - Tout droits réservés !
 */

package fr.sandji.sphone.mod.common.packets;

import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.common.packets.client.*;
import fr.sandji.sphone.mod.common.packets.server.PacketEditContact;
import fr.sandji.sphone.mod.common.packets.server.PacketEditNote;
import fr.sandji.sphone.mod.common.packets.server.PacketGetUniqueConv;
import fr.sandji.sphone.mod.common.packets.server.PacketRequestData;
import fr.sandji.sphone.mod.common.packets.server.call.PacketCallRequest;
import fr.sandji.sphone.mod.common.packets.server.call.PacketJoinCall;
import fr.sandji.sphone.mod.common.packets.server.call.PacketQuitCall;
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

        packetFromServer(PacketJoinCall.ServerHandler.class, PacketJoinCall.class);
        packetFromServer(PacketQuitCall.ServerHandler.class, PacketQuitCall.class);
        packetFromClient(PacketCall.Handler.class, PacketCall.class);
        packetFromServer(PacketCallRequest.ServerHandler.class, PacketCallRequest.class);

        packetFromServer(PacketRequestData.ServerHandler.class, PacketRequestData.class);
        packetFromServer(PacketEditNote.ServerHandler.class, PacketEditNote.class);
        packetFromServer(PacketEditContact.ServerHandler.class, PacketEditContact.class);
        packetFromServer(PacketGetUniqueConv.ServerHandler.class, PacketGetUniqueConv.class);

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
