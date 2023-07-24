/*
 * SITE-23 V2 - Tout droits réservés !
 */

package fr.sandji.sphone.mod.common.packets;

import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.common.packets.client.PacketCall;
import fr.sandji.sphone.mod.common.packets.client.PacketOpenPhone;
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

        // Call
        packetFromServer(PacketJoinCall.ServerHandler.class, PacketJoinCall.class);
        packetFromServer(PacketQuitCall.ServerHandler.class, PacketQuitCall.class);
        packetFromClient(PacketCall.Handler.class, PacketCall.class);
        packetFromServer(PacketCallRequest.ServerHandler.class, PacketCallRequest.class);
        packetFromServer(PacketRequestData.ServerHandler.class, PacketRequestData.class);

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
