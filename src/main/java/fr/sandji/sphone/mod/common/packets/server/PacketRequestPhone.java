
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.common.packets.server;

import fr.sandji.sphone.api.database.DatabaseManager;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PacketRequestPhone implements IMessage {

    public String action;
    public String sim_code;

    public PacketRequestPhone(String action, String sim_code) {
        this.action = action;
        this.sim_code = sim_code;
    }

    public PacketRequestPhone() {}

    @Override
    public void fromBytes(ByteBuf buf) {
        this.action = ByteBufUtils.readUTF8String(buf);
        this.sim_code = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.action);
        ByteBufUtils.writeUTF8String(buf, this.sim_code);
    }

    public static class ServerHandler implements IMessageHandler<PacketRequestPhone, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketRequestPhone message, MessageContext ctx) {
            if (message.action.equals("requestPhone")) {
                try {
                    final Connection connection = DatabaseManager.SPHONE.getDatabaseAccess().getConnection();
                    final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM simcards WHERE sim_code = ?");
                    preparedStatement.setString(1, message.sim_code);
                    preparedStatement.executeQuery();
                    final ResultSet resultSet = preparedStatement.getResultSet();
                    if (resultSet.next()) {
                        System.out.println("LIAM : " + resultSet.getInt("background"));
                    } else {
                        System.out.println("Erreur : Donnée de la Carte Sim introuvable");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        }
    }
}
