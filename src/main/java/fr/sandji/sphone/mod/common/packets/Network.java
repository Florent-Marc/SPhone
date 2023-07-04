/*
 * SITE-23 V2 - Tout droits réservés !
 */

package fr.sandji.sphone.mod.common.packets;

import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.common.packets.client.PacketOpenPhone;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class Network {
    public static void init(){
        SPhone.network = NetworkRegistry.INSTANCE.newSimpleChannel("sphone");
        SPhone.network.registerMessage(PacketOpenPhone.Handler.class, PacketOpenPhone.class,0, Side.CLIENT);
    }
}
