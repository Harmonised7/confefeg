package harmonised.confefeg.events;

import harmonised.confefeg.config.Confefeger;
import harmonised.confefeg.config.ExampleConfefe;
import harmonised.confefeg.network.MessageConfig;
import harmonised.confefeg.network.NetworkHandler;
import harmonised.confefeg.util.Reference;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
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
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if( player.world.isRemote() )
            {
                for( Confefeger.Confefeg confefeg : ExampleConfefe.confefeger.getConfefegs().values() )
                {
                    player.sendStatusMessage( new StringTextComponent( confefeg.name + ": " + confefeg.get() ), false );
                }
            }
            else
            {
                ExampleConfefe.confefeger.reloadConfefegs();
                for( Confefeger.Confefeg confefeg : ExampleConfefe.confefeger.getConfefegs().values() )
                {
                    NetworkHandler.sendToPlayer( new MessageConfig( Confefeger.confefegToNBT( confefeg ) ), (ServerPlayerEntity) player );
                }
            }
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

    @SubscribeEvent
    public static void playerLoggedIn( PlayerEvent.PlayerLoggedInEvent event )
    {
        Confefeger.syncAllConfefegs( (ServerPlayerEntity) event.getPlayer() );
    }
}