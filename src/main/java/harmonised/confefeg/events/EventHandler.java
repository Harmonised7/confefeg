package harmonised.confefeg.events;

import harmonised.confefeg.util.Reference;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

@Mod.EventBusSubscriber( modid = Reference.MOD_ID )
public class EventHandler
{
    @SubscribeEvent
    public static void jumpEvent( LivingEvent.LivingJumpEvent event )
    {
        if( event.getEntityLiving() instanceof PlayerEntity )
        {
        }
    }

    @SubscribeEvent
    public static void playerTickEvent( TickEvent.PlayerTickEvent event )
    {
//        System.out.println( ConfefegMod.isLocal() + " " + ConfefegMod.isServerLocal() );
    }

//    @SubscribeEvent
//    public static void worldTickEvent( FMLServerAboutToStartEvent event )
//    {
//        System.out.println( "a" );
//    }
}