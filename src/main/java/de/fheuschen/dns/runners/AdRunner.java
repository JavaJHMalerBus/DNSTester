package de.fheuschen.dns.runners;

import de.fheuschen.dns.Runner;
import org.apache.commons.cli.CommandLine;

@de.fheuschen.dns.annotation.Runner
public class AdRunner implements Runner {
    @Override
    public boolean run() {
        return false;
    }

    @Override
    public boolean isResponsible(CommandLine cl) {
        return false;
    }
}
