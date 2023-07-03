/*
 * SITE-23 V2 - Tout droits réservés !
 */

package fr.sandji.sphone.mod.common.packets;

import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.common.packets.server.PacketRequestPhone;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class Network {
    public static void init(){
        SPhone.network = NetworkRegistry.INSTANCE.newSimpleChannel("sphone");
        SPhone.network.registerMessage(PacketRequestPhone.ServerHandler.class, PacketRequestPhone.class,0, Side.SERVER);
    }
}
