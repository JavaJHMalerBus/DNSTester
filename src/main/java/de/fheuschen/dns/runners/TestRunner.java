package de.fheuschen.dns.runners;

import de.fheuschen.dns.annotation.Runner;
import org.apache.commons.cli.CommandLine;

@Runner
public class TestRunner implements de.fheuschen.dns.Runner {

    public TestRunner() {

    }

    @Override
    public boolean run() {
        return false;
    }

    @Override
    public boolean isResponsible(CommandLine cl) {
        return true;
    }
}
