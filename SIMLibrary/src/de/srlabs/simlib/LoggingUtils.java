package de.srlabs.simlib;

import java.lang.StackWalker;
import java.lang.StackWalker.Option;
import java.lang.StackWalker.StackFrame;

public class LoggingUtils {

    private static StackWalker walker;

    static {
        try {
            walker = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);
        } catch (IllegalStateException e) { // this should never happen really
            e.printStackTrace(System.err);
        }
    }
    
    public static String formatDebugMessage(String message) {
            String result = "[" + getClassName(1) + ", " + getMethodName(1) + "] " + message;
            return result;
    }

    private static String getMethodName(final int depth) {
        StackFrame frame = walker.walk(stream1 -> stream1.skip(depth + 1).findFirst().orElse(null));
        return frame == null ? "caller: null" : frame.getMethodName();
    }

    private static String getClassName(final int depth) {
        Class<?> callerClass = walker.getCallerClass();
        return callerClass.getName();
    }
}
