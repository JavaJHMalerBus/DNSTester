package de.fheuschen.dns;

import org.apache.commons.cli.*;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DNSTester {

    static boolean debug = false;
    private static CommandLine cl;
    public static String server;
    public static String[] resolvers = {"8.8.8.8", "8.8.4.4"};

    public static void main(String[] args) throws ParseException {
        Options o = new Options();

        o.addOption("d", "debug", false, "Print debug information");

        Option serv = new Option("s", "server", true, "The DNS-Server IP to test");
        serv.setRequired(true);

        o.addOption(serv);

        CommandLineParser clp = new DefaultParser();
        cl = clp.parse(o, args);

        debug = cl.hasOption("d");

        O.p("Starting up...");

        startup();
    }

    private static void startup() {

        O.p("Loading runners...");
        List<Runner> r = searchRunners();

        O.p("Executing runners...");
        for(Runner ru : r) {

            O.p("--- Runner: " + ru.getClass().getCanonicalName() + " (" + ru.getDescription() + ") ---\n\n\n");

            if(!ru.run()) {

                O.e("--> " + ru.getClass().getCanonicalName() + " failed! Exiting...");

                break;
            }

            O.p("--> " + ru.getClass().getCanonicalName() + " succeeded! Continuing...");

        }

        O.p("Shuting down...");
        O.p("Good bye!");
    }

    private static List<Runner> searchRunners() {
        List<Runner> r = new ArrayList<>();

        Set<Class<?>> s = new Reflections("de.fheuschen.dns.runners").getTypesAnnotatedWith(de.fheuschen.dns.annotation.Runner.class);

        for(Class c : s)
        {
            try {
                O.d("Found runner: " + c.getCanonicalName());
                Runner ru = (Runner) c.getDeclaredConstructor().newInstance();

                if(ru.isResponsible(cl)) {
                    O.d("Added responsible runner: " + c.getCanonicalName());
                    r.add(ru);
                }

            } catch(Exception e) {
                O.e(e.getLocalizedMessage());
            }
        }

        return r;
    }

}