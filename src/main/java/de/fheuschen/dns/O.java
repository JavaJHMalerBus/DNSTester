package de.fheuschen.dns;

import java.text.SimpleDateFormat;
import java.util.Date;

public class O {

    public static void p(String... s)
    {
        for (String string:s) {
            p(string);
        }
    }

    public static void p(String s)
    {
        System.out.println(prep("INFO") + s);
    }

    public static void e(String... s)
    {
        for (String string:s) {
            p(string);
        }
    }

    public static void e(String s)
    {
        System.err.println(prep("ERROR") + s);
    }

    public static void d(String... s)
    {
        for (String string:s) {
            d(string);
        }
    }

    public static void d(String s)
    {
        if(DNSTester.debug)
            System.out.println(prep("DEBUG") + s);
    }

    protected static String prep(String level)
    {
        String a = "[" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS").format(new Date()) + " | " + level + "]";
        int f = 40;
        int spc = f - a.length();
        String e = "";
        for(int i = 0; i < spc; i++)
        {
            e += " ";
        }
        return a + e;
    }

}
