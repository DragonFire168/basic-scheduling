package Graphing;

/**
 * Created by DragonFire on 11/23/15.
 *
 * Information on creating custom exceptions obtained from http://stackoverflow.com/questions/8423700/how-to-create-a-custom-exception-type-in-java
 */
public class CycleException extends RuntimeException {
    public CycleException() {
        super();
    }

    public CycleException(String s) {
        super(s);
    }

    public CycleException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CycleException(Throwable throwable) {
        super(throwable);
    }

    protected CycleException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
