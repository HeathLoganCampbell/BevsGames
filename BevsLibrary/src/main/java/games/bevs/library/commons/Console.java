package games.bevs.library.commons;

import org.fusesource.jansi.Ansi;

public class Console
{
    public static boolean DEBUG_MODE = true;
    private static String LOG = Ansi.ansi().fg(Ansi.Color.GREEN).toString();
    private static String DEBUG = Ansi.ansi().fg(Ansi.Color.GREEN).toString();
    private static String RESET = Ansi.ansi().fg(Ansi.Color.WHITE).toString();

    public static void log(String header, String message)
    {
        System.out.println(LOG + header + " ]> " + message + RESET);
    }

    public static void debug(String header, String message)
    {
        if(DEBUG_MODE) System.out.println(DEBUG + "DEBUG " + header + " ]> " + message + RESET);
    }
}
