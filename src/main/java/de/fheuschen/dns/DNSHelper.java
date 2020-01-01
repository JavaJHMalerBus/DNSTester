package de.fheuschen.dns;

import org.xbill.DNS.*;

import java.io.IOException;
import java.util.Set;

public class DNSHelper {

    public static Record[] resolve(String server, String domain, int t) throws IOException {
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
        Record[] resA = resolve(server, domain, Type.A);
        Record[] resB = resolve(server2, domain, Type.A);

        Object[] res = new Object[3];
        res[0] = join(resA);
        res[1] = join(resB);
        res[2] = res[0].toString().equalsIgnoreCase(res[1].toString());

        return res;
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
            O.p("[T] - Testing domain \"" + domain + "\" using " + server + " and " + server2);

            Object[] o = compare(server, server2, domain);

            int a = 29;
            int l1 = a - server.length(), l2 = a - server2.length();

            if(((Boolean)o[2]) == desired)
            {
                O.p(
                  "[R] - Result from " + server + ":" + ws(l1) + o[0],
                  "[R] - Result from " + server2 + ":" + ws(l2) + o[1],
                  "[S] - Test succeeded"
                );
                return true;
            } else {
                O.p(
                        "[R] - Result from " + server + ":" + ws(l1) + o[0],
                        "[R] - Result from " + server2 + ":" + ws(l2) + o[1],
                        "[F] - Test failed"
                );
                return false;
            }

        } catch (IOException e) {
            O.d(e.getLocalizedMessage());
            return false;
        }
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
