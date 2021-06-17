package harmonised.confefeg.config;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class Confefeger
{
    private static Set<Confefeger> confefegers = new HashSet<>();
    
    public final String confefeName;
    
    public static Confefeger registerConfefe( String confefeName )
    {
        Confefeger confefeger = new Confefeger( confefeName );
        confefegers.add( confefeger );
        return confefeger;
    }
    private Confefeger( String confefeName )
    {
        this.confefeName = confefeName;
    }

    public static class ConfefeBuilder
    {
        private final String name;
        private Side side = Side.COMMON;
        private String description = null;

        public ConfefeBuilder( String name )
        {
            this.name = name;
        }

        public ConfefeBuilder description( String description )
        {
            this.description = description;
            return this;
        }
        
        public ConfefeBuilder side( Side side )
        {
            this.side = side;
            return this;
        }

        public Confefe<Double> submit( double value, double min, double max )
        {
            return new Confefe<>( name, description, value, min, max );
        }

        public Confefe<Float> submit( float value, float min, float max )
        {
            return new Confefe<>( name, description, value, min, max );
        }

        public Confefe<Long> submit( long value, long min, long max )
        {
            return new Confefe<>( name, description, value, min, max );
        }

        public Confefe<Integer> submit( int value, int min, int max )
        {
            return new Confefe<>( name, description, value, min, max );
        }

        public Confefe<Character> submit( char value, char min, char max )
        {
            return new Confefe<>( name, description, value, min, max );
        }

        public Confefe<String> submit( String value )
        {
            return new Confefe<>( name, description, value );
        }
    }

    public enum Side
    {
        CLIENT,
        COMMON,
        SERVER
    }

    public static class Confefe<T> implements Supplier<T>
    {
        public final String name, description;
        private T value, min, max;

        public Confefe( String name, @Nullable String description, T value, T min,  T max )
        {
            this.name = name;
            this.description = description;
            this.value = value;
            this.min = min;
            this.max = max;
        }

        public Confefe( String name, @Nullable String description, T value )
        {
            this.name = name;
            this.description = description;
            this.value = value;
            this.min = value;
            this.max = value;
        }

        @Override
        public T get()
        {
            return value;
        }
    }
}
