package harmonised.confefeg.network;

import harmonised.confefeg.config.Confefeger;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class MessageConfig
{
    public static final Logger LOGGER = LogManager.getLogger();

    public CompoundNBT confefeg;

    public MessageConfig( CompoundNBT config )
    {
        this.confefeg = config;
    }

    public MessageConfig()
    {
    }

    public static MessageConfig decode(PacketBuffer buf )
    {
        MessageConfig packet = new MessageConfig();

        packet.confefeg = buf.readCompoundTag();

        return packet;
    }

    public static void encode(MessageConfig packet, PacketBuffer buf )
    {
        buf.writeCompoundTag( packet.confefeg );
    }

    public static void handlePacket( MessageConfig packet, Supplier<NetworkEvent.Context> ctx )
    {
        ctx.get().enqueueWork(() ->
        {
            CompoundNBT nbt = packet.confefeg;
            Confefeger confefeger = Confefeger.confefegers.get( nbt.getString( "key" ) );
            if( confefeger != null )
            {
                Confefeger.Confefeg confefeg = confefeger.getConfefeg( nbt.getString( "name" ) );
                if( confefeg != null )
                {
                    Object value = confefeg.get();
                    if( value instanceof Integer )
                        confefeg.set( nbt.getInt( "value" ) );
                    else if( value instanceof Float )
                        confefeg.set( nbt.getFloat( "value" ) );
                    else if( value instanceof Double )
                        confefeg.set( nbt.getDouble( "value" ) );
                    else if( value instanceof String )
                        confefeg.set( nbt.getString( "value" ) );
                    else
                        LOGGER.error( "Received an invalid type Config! How!?" );
                }
            }
        });
        ctx.get().setPacketHandled( true );
    }
}