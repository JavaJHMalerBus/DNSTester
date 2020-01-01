package de.fheuschen.dns;

import org.apache.commons.cli.*;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DNSTester {

    static boolean debug = false;
    private static CommandLine cl;
    public static String server = "1.1.1.1";
    public static String[] resolvers = {"8.8.8.8", "8.8.4.4"};

    public static void main(String[] args) throws ParseException {
        System.err.close();
        System.setErr(System.out);

        Options o = new Options();

        o.addOption("d", "debug", false, "Print debug information");

        Option serv = new Option("s", "server", true, "The DNS-Server IP to test");
        serv.setRequired(true);

        o.addOption(serv);
        o.addOption("r", "resolvers", true, "Resolvers to test against");

        CommandLineParser clp = new DefaultParser();
        cl = clp.parse(o, args);

        debug = cl.hasOption("d");

        if(cl.hasOption("r")) {
            resolvers = cl.getOptionValues("r");
        }

        if(cl.hasOption("s"))
        {
            server = cl.getOptionValue("s");
        }

        O.p("Starting up...");

        startup();
    }

    public static String getTestServer() {
        return server;
    }

    public static String getRandomResolver() {
        if(resolvers.length == 0) {
            O.e("No resolvers configured! Exiting...");
            System.exit(1);
        }
        return resolvers[new Random().nextInt(resolvers.length - 1)];
    }

    private static void startup() {

        O.p("Loading runners...");
        List<Runner> r = searchRunners();

        O.p("Executing runners...");
        for(Runner ru : r) {

            O.p("--- Runner: " + ru.getClass().getCanonicalName() + " (" + ru.getDescription() + ") ---");

            if(!ru.run()) {

                O.e("--> " + ru.getClass().getCanonicalName() + " failed! Exiting...");

                break;
            }

            O.p("--> " + ru.getClass().getCanonicalName() + " succeeded! Continuing...\n\n\n\n\n\n");

        }

        O.p("Shutting down...");
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