package harmonised.confefeg.events;

import harmonised.confefeg.config.Confefeger;
import harmonised.confefeg.config.ExampleConfefe;
import harmonised.confefeg.network.NetworkHandler;
import harmonised.confefeg.util.Reference;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber( modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD )
public class ModEventHandler
{
    @SubscribeEvent( priority = EventPriority.LOWEST )
    public static void modsLoading( FMLCommonSetupEvent event )
    {
        ExampleConfefe.init();
        NetworkHandler.registerPackets();
    }
}
