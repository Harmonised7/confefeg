package harmonised.confefeg.config;

import harmonised.confefeg.util.Reference;

public class ExampleConfefe
{
    public static Confefeger confefeger = Confefeger.registerConfefeg( Reference.MOD_ID );

    public static void init()
    {
        Confefeger.Confefeg<Double> testDouble = confefeger
                .build( "TestDouble" )
                .description( "A Double that's a test" )
                .submit( 50D, 0D, 100D );
    }
}
