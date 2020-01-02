package de.fheuschen.dns;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

import static org.fusesource.jansi.Ansi.ansi;

public class O {

    public static void p(Object... s)
    {
        for (Object string:s) {
            p(string);
        }
    }

    public static void p(Object s)
    {
        if(s instanceof String)
            s = ansi().fg(YELLOW).a(s.toString()).reset();
        System.out.println(prep("INFO") + s);
    }

    public static void e(Object... s)
    {
        for (Object string:s) {
            p(string);
        }
    }

    public static void e(Object s)
    {
        if(s instanceof String)
            s = ansi().fg(RED).a(s.toString()).reset();
        System.err.println(prep("ERROR") + s);
    }

    public static void d(Object... s)
    {
        for (Object string:s) {
            d(string);
        }
    }

    public static void d(Object s)
    {
        if(s instanceof String)
            s = ansi().fg(CYAN).a(s.toString()).reset();
        if(DNSTester.debug)
            System.out.println(prep("DEBUG") + s);
    }

    protected static String prep(String level)
    {
        String a = ansi().fg(YELLOW).a("[").fg(CYAN).a(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS").format(new Date())).fg(YELLOW).a(" | ").fg(RED).a(level).fg(YELLOW).a("]").reset().toString();
        int f = 65;
        int spc = f - a.length();
        String e = "";
        for(int i = 0; i < spc; i++)
        {
            e += " ";
        }
        return a + e;
    }

}
