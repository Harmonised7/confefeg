package harmonised.confefeg.client;

import harmonised.confefeg.config.Confefeger;
import harmonised.confefeg.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber( value = Dist.CLIENT, modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE )
public class ClientHandler
{
    private static boolean isServerLocal = false;

    @SubscribeEvent
    public static void clientLoggedIn( ClientPlayerNetworkEvent.LoggedInEvent event )
    {
        if( event.getPlayer().world.isRemote() )
            isServerLocal = Minecraft.getInstance().isIntegratedServerRunning();
        Confefeger.saveAllConfefegers();
    }

    public static boolean isServerLocal()
    {
        return isServerLocal;
    }
}
