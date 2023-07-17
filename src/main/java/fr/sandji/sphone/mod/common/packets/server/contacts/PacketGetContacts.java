
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.common.packets.server.contacts;

import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.api.database.DatabaseManager;
import fr.sandji.sphone.mod.common.packets.client.PacketOpenPhone;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PacketGetContacts implements IMessage {

    public PacketGetContacts() {}

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class ServerHandler implements IMessageHandler<PacketGetContacts, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketGetContacts message, MessageContext ctx) {
            String simcode = "1234";
            try {
                final Connection connection = DatabaseManager.SPHONE.getDatabaseAccess().getConnection();
                final PreparedStatement preparedStatement = connection.prepareStatement("SELECT contacts FROM simcards WHERE sim_code = ?");
                preparedStatement.setString(1, simcode);
                preparedStatement.executeQuery();
                final ResultSet resultSet = preparedStatement.getResultSet();
                if (resultSet != null) {
                    if (resultSet.next()) {
                        EntityPlayer player = ctx.getServerHandler().player;
                        SPhone.network.sendTo(new PacketOpenPhone("openContacts", resultSet.getString("contacts")), (EntityPlayerMP) player);
                    } else {
                        System.out.println("Erreur : Donnée de la Carte Sim introuvable");
                    }
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
