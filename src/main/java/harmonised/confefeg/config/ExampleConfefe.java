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

        Confefeger.Confefeg<Float> testFloat = confefeger
                .build( "TestFloat" )
                .description( "A Float that's a test" )
                .category( "Misc" )
                .submit( 50F, 0, 100 );

        Confefeger.Confefeg<Integer> testInteger = confefeger
                .build( "TestInteger" )
                .description( "A Integer that's a test" )
                .submit( 50, 0, 100 );
    }
}
