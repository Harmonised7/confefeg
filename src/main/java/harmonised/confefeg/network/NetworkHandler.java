package harmonised.confefeg.network;

import harmonised.confefeg.ConfefegMod;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetworkHandler
{
    public static final Logger LOGGER = LogManager.getLogger();

    public static void registerPackets()
    {
        int index = 0;

        ConfefegMod.HANDLER.registerMessage( index++, MessageConfefeg.class, MessageConfefeg::encode, MessageConfefeg::decode, MessageConfefeg::handlePacket ); }

    public static void sendToPlayer(MessageConfefeg packet, ServerPlayerEntity player )
    {
        ConfefegMod.HANDLER.sendTo( packet, player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT );
    }
}
