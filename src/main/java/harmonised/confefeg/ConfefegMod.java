package harmonised.confefeg;

import harmonised.confefeg.client.ClientHandler;
import harmonised.confefeg.config.ExampleConfefe;
import harmonised.confefeg.events.EventHandler;
import harmonised.confefeg.network.NetworkHandler;
import harmonised.confefeg.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod( Reference.MOD_ID )
public class ConfefegMod
{
    private static boolean isLocal = false;

    private static final String PROTOCOL_VERSION = "1";
    private static final Logger LOGGER = LogManager.getLogger();

    public static SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
            .named( new ResourceLocation( Reference.MOD_ID, "main_channel" ) )
            .clientAcceptedVersions( PROTOCOL_VERSION::equals )
            .serverAcceptedVersions( PROTOCOL_VERSION::equals )
            .networkProtocolVersion( () -> PROTOCOL_VERSION )
            .simpleChannel();


    public ConfefegMod()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener( this::clientLoading );
    }

    private void clientLoading( FMLClientSetupEvent event )
    {
        isLocal = true;
    }

    public static boolean isLocal()
    {
        return isLocal;
    }

    public static boolean isServerLocal()
    {
        return ClientHandler.isServerLocal();
    }
}
