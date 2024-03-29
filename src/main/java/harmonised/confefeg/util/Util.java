package harmonised.confefeg.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class Util
{
    public static ResourceLocation getDimensionResLoc(World world )
    {
        return world.getDimensionKey().getRegistryName();
    }

    public static ResourceLocation getResLoc( String regKey )
    {
        try
        {
            return new ResourceLocation( regKey );
        }
        catch( Exception e )
        {
            return new ResourceLocation( "" );
        }
    }

    public static ResourceLocation getResLoc( String firstPart, String secondPart )
    {
        try
        {
            return new ResourceLocation( firstPart, secondPart );
        }
        catch( Exception e )
        {
            return null;
        }
    }

    public static int hueToRGB( float hue, float saturation, float brightness )
    {
        float r = 0, g = 0, b = 0;

        float chroma = brightness * saturation;
        float hue1 = hue/60F;
        float x = chroma * (1- Math.abs((hue1 % 2) - 1));
        switch( (int) hue1 )
        {
            case 0:
                r = chroma;
                g = x;
                b = 0;
                break;

            case 1:
                r = x;
                g = chroma;
                b = 0;
                break;

            case 2:
                r = 0;
                g = chroma;
                b = x;
                break;

            case 3:
                r = 0;
                g = x;
                b = chroma;
                break;

            case 4:
                r = x;
                g = 0;
                b = chroma;
                break;

            case 5:
                r = chroma;
                g = 0;
                b = x;
                break;
        }

        float m = brightness - chroma;
        int r1 = (int) ((r + m) * 255);
        int g1 = (int) ((g + m) * 255);
        int b1 = (int) ((b + m) * 255);
        return r1 << 16 | b1 << 8 | g1;
    }

    public static double map( double input, double inLow, double inHigh, double outLow, double outHigh )
    {
        return ( (input - inLow) / (inHigh - inLow) ) * (outHigh - outLow) + outLow;
    }

    public static double getDistance( int x1, int y1, int x2, int y2 )
    {
        return Math.sqrt( Math.pow( x2 - x1, 2 ) + Math.pow( y2 - y1, 2 ) );
    }

    public static double getDistance( double x1, double y1, double x2, double y2 )
    {
        return Math.sqrt( Math.pow( x2 - x1, 2 ) + Math.pow( y2 - y1, 2 ) );
    }

    public static float getDeltaChange( float original, float diff, float d )
    {
        if( Math.abs( diff ) < 0.001 )
            return original + diff;
        else
            return original + diff * d;
    }
}