package de.fheuschen.dns.runners;

import de.fheuschen.dns.DNSHelper;
import de.fheuschen.dns.Runner;
import org.apache.commons.cli.CommandLine;

@de.fheuschen.dns.annotation.Runner
public class AdRunner implements Runner {
    @Override
    public boolean run() {


        DNSHelper.c("googleadservices.com", false);
        DNSHelper.c("banners.ebay.com", false);
        DNSHelper.c("fheuschen.de", true);

        return true;
    }

    @Override
    public boolean isResponsible(CommandLine cl) {
        return true;
    }

    @Override
    public String getDescription() {
        return "This runner tests, whether the given DNSR is blocking ads.";
    }
}
