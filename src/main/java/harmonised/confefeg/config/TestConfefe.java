package harmonised.confefeg.config;

import harmonised.confefeg.util.Reference;

public class TestConfefe
{
    public static Confefeger confefeger = Confefeger.registerConfefe( Reference.MOD_ID );

    public static void init()
    {
        Confefeger.Confefe<Double> testDouble = new Confefeger
                .ConfefeBuilder( "testDouble" )
                .description( "A Double that's a test" )
                .submit( 50D, 0D, 100D );
    }
}
