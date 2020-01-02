package de.fheuschen.dns;

import org.xbill.DNS.*;

import java.io.IOException;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class DNSHelper {

    public static Record[] resolve(String server, String domain, int t) throws IOException {

        Lookup.getDefaultCache(DClass.IN).setMaxCache(0);
        Lookup.getDefaultCache(DClass.IN).setMaxNCache(0);

        Lookup l = new Lookup(domain, t, DClass.IN);
        l.setResolver(new SimpleResolver(server));
        l.run();
        if(l.getResult() == Lookup.SUCCESSFUL)
            return l.getAnswers();
        else {
            O.d(domain + " unsuccessful on " + server);
            return new Record[] {};
        }
    }

    public static Object[] compare(String server, String server2, String domain) throws IOException {
        Record[] resA = resolve(server, domain, Type.A); //Tested
        Record[] resB = resolve(server2, domain, Type.A); //Verified (?)
        Object[] res = new Object[3];
        res[0] = join(resA);
        res[1] = join(resB);
        if(resA.length != resB.length)
            res[2] = ((resA.length > 0 && resB.length > 0) && resA[0].rdataToString().equalsIgnoreCase(resB[0].rdataToString())) && !resA[0].rdataToString().equalsIgnoreCase("0.0.0.0");
        else {
            O.d("Same amount of records found - looping through them.");
            boolean[] bs = new boolean[resA.length];
            for(int i = 0; i < resA.length; i++)
            {
                O.d("Testing " + resA[i].rdataToString() + " against " + resB[i].rdataToString());
                bs[i] = resA[i].rdataToString().equalsIgnoreCase(resB[i].rdataToString()) && !resA[i].rdataToString().equalsIgnoreCase("0.0.0.0");
            }
            res[2] = aT(bs);
        }

        return res;
    }

    private static boolean aT(boolean[] b)
    {
        for(boolean bs:b) if(!bs) return false;
        return true;
    }

    private static String ws(int l)
    {
        String s = "";
        for(int i = 0; i < l; i++)
            s += " ";
        return s;
    }

    public static boolean c(String server, String server2, String domain, boolean desired) {
        try {
            O.p(ansi().fg(Color.CYAN).a("[T] - Testing domain \"").fg(GREEN).a(domain).fg(CYAN).a("\" using ").fg(GREEN).a(server).fg(CYAN).a(" and ").fg(GREEN).a(server2).reset());

            Object[] o = compare(server, server2, domain);

            int a = 29;
            int l1 = a - server.length(), l2 = a - server2.length();

            if(((Boolean)o[2]) == desired)
            {
                O.p(
                  ansi().fg(Color.BLUE).a("[R] - Result from " + server + ":" + ws(l1) + o[0]).reset(),
                  ansi().fg(BLUE).a("[R] - Result from " + server2 + ":" + ws(l2) + o[1]).reset(),
                  ansi().fg(GREEN).a("[S] - ").bg(GREEN).fg(BLACK).a("Test succeeded").reset().a("\n")
                );
                return true;
            } else {
                O.p(
                        ansi().fg(Color.BLUE).a("[R] - Result from " + server + ":" + ws(l1) + o[0]).reset(),
                        ansi().fg(BLUE).a("[R] - Result from " + server2 + ":" + ws(l2) + o[1]).reset(),
                        ansi().fg(RED).a("[F] - ").bg(RED).fg(BLACK).a("Test failed").reset().a("\n")
                );
                return false;
            }


        } catch (IOException e) {
            O.d(e.getLocalizedMessage());
            return false;
        }
    }

    public static boolean c(String domain, boolean desired) {
        return c(DNSTester.getTestServer(), DNSTester.getRandomResolver(), domain, desired);
    }

    public static String join(Object[] a)
    {
        if(a == null)
            return "-";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            sb.append(a[i].toString());
            if (i != a.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

}
