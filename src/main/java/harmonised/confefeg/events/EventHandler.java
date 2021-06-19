package harmonised.confefeg.events;

import harmonised.confefeg.config.Confefeger;
import harmonised.confefeg.config.ExampleConfefe;
import harmonised.confefeg.util.Reference;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

@Mod.EventBusSubscriber( modid = Reference.MOD_ID )
public class EventHandler
{
    @SubscribeEvent
    public static void jumpEvent( LivingEvent.LivingJumpEvent event )
    {
        if( event.getEntityLiving() instanceof PlayerEntity )
        {
            ExampleConfefe.confefeger.reloadConfefegs();
        }
    }

    @SubscribeEvent
    public static void playerTickEvent( TickEvent.PlayerTickEvent event )
    {
//        System.out.println( ConfefegMod.isLocal() + " " + ConfefegMod.isServerLocal() );
    }

    @SubscribeEvent
    public static void serverStartedEvent( FMLServerStartedEvent event )
    {
        Confefeger.saveAllConfefegers();
    }
}