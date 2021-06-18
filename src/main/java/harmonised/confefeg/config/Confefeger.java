package harmonised.confefeg.config;

import harmonised.confefeg.util.Reference;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Confefeger
{
    public static final Logger LOGGER = LogManager.getLogger();

    private static Map<String, Confefeger> confefegers = new HashMap<>();
    public final String confefegName;

    private Map<String, Confefeg> confefegs = new HashMap<>();
    private Map<String, String> parsedConfefeg = new HashMap<>();

    public static void parseAllConfefegers()
    {
        for( Confefeger confefeger : confefegers.values() )
        {
            confefeger.parseConfefeg();
        }
    }

    public static Confefeger registerConfefeg( String confefeName )
    {
        Confefeger confefeger = new Confefeger( confefeName );
        confefegers.put( confefeName, confefeger );
        return confefeger;
    }
    private Confefeger( String confefeName )
    {
        this.confefegName = confefeName;
    }

    public ConfefeBuilder build( String name )
    {
        return new Confefeger.ConfefeBuilder( this, name );
    }

    public void saveConfefeg()
    {
        String tomlConfig = getConfefegsAsToml();

        File configFile = getConfigFile();
        try
        {
            configFile.getParentFile().mkdir();
            configFile.createNewFile();
        }
        catch( IOException e )
        {
            LOGGER.error( "Could not save " + Reference.MOD_ID + " Config!", configFile.getPath(), e );
        }

        try( FileOutputStream outputStream = new FileOutputStream( configFile ) )
        {
            System.out.println( "Writing " + Reference.MOD_ID + " Config to " + configFile.getPath() );
            IOUtils.write( tomlConfig, outputStream );
        }
        catch( IOException e )
        {
            LOGGER.error( "Error writing " + Reference.MOD_ID + " Config file to " + configFile.getPath(), configFile.getPath(), e );
        }
    }

    public void parseConfefeg()
    {
        File configFile = getConfigFile();
        if( !configFile.exists() )
            saveConfefeg();
        try
        (
            FileInputStream inputStream = new FileInputStream( configFile );
            InputStreamReader inputStreamReader = new InputStreamReader( inputStream, StandardCharsets.UTF_8 );
            BufferedReader reader = new BufferedReader( inputStreamReader );
        )
        {
            LOGGER.debug( "Reading " + configFile.getName(), configFile.getPath() );
            for( String line : reader.lines().collect( Collectors.toList() ) )
            {
                if( line.length() == 0 || line.charAt(0) == '#' )
                    continue;
                int equalsIndex = line.indexOf( '=' );
                if( equalsIndex != -1 )
                {
                    String key = line.substring( 0, equalsIndex );
                    String stringValue = line.substring( equalsIndex+1 );
                    parsedConfefeg.put( key, stringValue );
                }
            }
        }
        catch( IOException e )
        {
            LOGGER.error( "Error parsing Confefeg: " + configFile.getPath(), e );
        }
    }

    public String getConfefegsAsToml()
    {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, List<Confefeg>> categories = new HashMap<>();
        for( Confefeg confefeg : confefegs.values() )
        {
            String categoryName = confefeg.category;
            if( !categories.containsKey( categoryName ) )
                categories.put( categoryName, new ArrayList<>() );
            categories.get( categoryName ).add( confefeg );
        }
        for( Map.Entry<String, List<Confefeg>> entry : categories.entrySet() )
        {
            stringBuilder.append( "###" + entry.getKey() + "###\n" );
            for( Confefeg confefeg : entry.getValue() )
            {
                stringBuilder.append( generateConfefegString( confefeg ) + "\n" );
            }
        }
        return stringBuilder.toString();
    }

    public static String generateConfefegString( Confefeg confefeg )
    {
        String output = "";

        output += "#Description\t" + confefeg.description + "\n";
        if( !( confefeg.value instanceof String ) )
        output += "#Range\t" + confefeg.min + "\tto\t" + confefeg.max + "\n";
        output += confefeg.name + "=" + confefeg.value + "\n";

        return output;
    }

    public File getConfigFile()
    {
        return FMLPaths.CONFIGDIR.get().resolve( confefegName + ".toml" ).toFile();
    }

    public static class ConfefeBuilder
    {
        private final Confefeger confefeger;
        private final String name;
        private Side side = Side.COMMON;
        private String description = "No Description", category = "General";

        public ConfefeBuilder( Confefeger confefeger, String name )
        {
            this.confefeger = confefeger;
            this.name = name;
        }

        public ConfefeBuilder description( String description )
        {
            this.description = description;
            return this;
        }

        public ConfefeBuilder category( String category )
        {
            this.category = category;
            return this;
        }
        
        public ConfefeBuilder side( Side side )
        {
            this.side = side;
            return this;
        }

        public void addConfefeg( Confefeg confefeg )
        {
            String parsedValueString = confefeg.confefeger.parsedConfefeg.get( confefeg.name );
            if( parsedValueString != null )
            {
                Object value = confefeg.value;
                if( value instanceof Integer )
                    confefeg.set( Integer.parseInt( parsedValueString ) );
                else if( value instanceof Float )
                {
                    Float parsedValue = Float.parseFloat( parsedValueString );
                    if( !parsedValue.isNaN() )
                        confefeg.set( parsedValue );
                }
                else if( value instanceof Double )
                {
                    Double parsedValue = Double.parseDouble( parsedValueString );
                    if( !parsedValue.isNaN() )
                        confefeg.set( parsedValue );
                }
                else if( value instanceof String )
                    confefeg.set( value );
            }
            LOGGER.info( "Loaded Confefeg \"" + confefeg.name + "\" as " + confefeg.value );
            confefeger.confefegs.put( confefeg.name, confefeg );
        }

        public Confefeg<Double> submit( double value, double min, double max )
        {
            Confefeg<Double> confefeg = new Confefeg<>( confefeger, name, description, category, side, value, min, max );
            addConfefeg( confefeg );
            return confefeg;
        }

        public Confefeg<Float> submit( float value, float min, float max )
        {
            Confefeg<Float> confefeg = new Confefeg<>( confefeger, name, description, category, side, value, min, max );
            addConfefeg( confefeg );
            return confefeg;
        }

        public Confefeg<Long> submit( long value, long min, long max )
        {
            Confefeg<Long> confefeg = new Confefeg<>( confefeger, name, description, category, side, value, min, max );
            addConfefeg( confefeg );
            return confefeg;
        }

        public Confefeg<Integer> submit( int value, int min, int max )
        {
            Confefeg<Integer> confefeg = new Confefeg<>( confefeger, name, description, category, side, value, min, max );
            addConfefeg( confefeg );
            return confefeg;
        }

//        public Confefeg<Character> submit( char value )
//        {
//            Confefeg<Character> confefeg = new Confefeg<>( confefeger, name, description, category, side, value );
//            addConfefeg( confefeg );
//            return confefeg;
//        }

        public Confefeg<String> submit( String value )
        {
            Confefeg<String> confefeg = new Confefeg<>( confefeger, name, description, category, side, value );
            addConfefeg( confefeg );
            return confefeg;
        }
    }

    public enum Side
    {
        CLIENT,
        COMMON
    }

    public static class Confefeg<T> implements Supplier<T>
    {
        public final Confefeger confefeger;
        public final String name, description, category;
        public final Side side;
        private T value, min, max;

        public Confefeg( Confefeger confefeger, String name, String description, String category, Side side, T value, T min, T max )
        {
            this.confefeger = confefeger;
            this.name = name;
            this.description = description;
            this.category = category;
            this.side = side;
            this.value = value;
            this.min = min;
            this.max = max;
        }

        public Confefeg( Confefeger confefeger, String name, String description, String category, Side side, T value )
        {
            this.confefeger = confefeger;
            this.name = name;
            this.description = description;
            this.category = category;
            this.side = side;
            this.value = value;
            this.min = value;
            this.max = value;
        }

        @Override
        public T get()
        {
            return value;
        }

        public void set( T value )
        {
            this.value = value;
            confefeger.saveConfefeg();
            if( this.side == Side.COMMON )
            {
                //Send packet to set config on server side
            }
        }
    }
}
