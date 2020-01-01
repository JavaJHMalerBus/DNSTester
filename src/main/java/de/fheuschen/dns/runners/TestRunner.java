package de.fheuschen.dns.runners;

import de.fheuschen.dns.DNSHelper;
import de.fheuschen.dns.DNSTester;
import de.fheuschen.dns.annotation.Runner;
import org.apache.commons.cli.CommandLine;

@Runner
public class TestRunner implements de.fheuschen.dns.Runner {

    public TestRunner() {

    }

    @Override
    public boolean run() {

        boolean o = DNSHelper.c(DNSTester.getTestServer(), DNSTester.getRandomResolver(), "fheuschen.de", true);


        return true;
    }

    @Override
    public boolean isResponsible(CommandLine cl) {
        return false;
    }
}
