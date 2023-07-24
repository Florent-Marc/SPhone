
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.common.packets.server.contacts;

import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.server.database.DatabaseManager;
import fr.sandji.sphone.mod.common.packets.client.PacketOpenPhone;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PacketUpdateContacts implements IMessage {

    private String jsonFormat;

    public PacketUpdateContacts() {}

    public PacketUpdateContacts(String jsonFormat) {
        this.jsonFormat = jsonFormat;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        jsonFormat = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, jsonFormat);
    }

    public static class ServerHandler implements IMessageHandler<PacketUpdateContacts, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketUpdateContacts message, MessageContext ctx) {
            String simcode = "1234";
            try {
                final Connection connection = DatabaseManager.SPHONE.getDatabaseAccess().getConnection();
                final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE simcards SET `contacts`=? WHERE sim_code = ?");
                preparedStatement.setString(1, message.jsonFormat);
                preparedStatement.setString(2, simcode);
                preparedStatement.execute();
                connection.close();
                EntityPlayer player = ctx.getServerHandler().player;
                SPhone.network.sendTo(new PacketOpenPhone("openContacts", message.jsonFormat), (EntityPlayerMP) player);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}


