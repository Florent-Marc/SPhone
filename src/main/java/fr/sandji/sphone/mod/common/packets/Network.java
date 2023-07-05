/*
 * SITE-23 V2 - Tout droits réservés !
 */

package fr.sandji.sphone.mod.common.packets;

import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.common.packets.client.PacketCall;
import fr.sandji.sphone.mod.common.packets.client.PacketOpenPhone;
import fr.sandji.sphone.mod.common.packets.server.PacketCallRequest;
import fr.sandji.sphone.mod.common.packets.server.PacketJoinCall;
import fr.sandji.sphone.mod.common.packets.server.PacketQuitCall;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class Network {

    static int id = -1;
    public static void init(){
        SPhone.network = NetworkRegistry.INSTANCE.newSimpleChannel("sphone");
        packetFromClient(PacketOpenPhone.Handler.class, PacketOpenPhone.class);
        packetFromServer(PacketJoinCall.ServerHandler.class, PacketJoinCall.class);
        packetFromServer(PacketQuitCall.ServerHandler.class, PacketQuitCall.class);
        packetFromClient(PacketCall.Handler.class, PacketCall.class);
        packetFromServer(PacketCallRequest.ServerHandler.class, PacketCallRequest.class);

    }

    public static void packetFromClient(Class handler, Class packet) {
        SPhone.network.registerMessage(handler, packet, id++, Side.CLIENT);
    }

    public static void packetFromServer(Class handler, Class packet) {
        SPhone.network.registerMessage(handler, packet, id++, Side.SERVER);
    }
}
