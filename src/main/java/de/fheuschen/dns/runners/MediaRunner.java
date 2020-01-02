package de.fheuschen.dns.runners;

import de.fheuschen.dns.DNSHelper;
import de.fheuschen.dns.Runner;
import org.apache.commons.cli.CommandLine;

@de.fheuschen.dns.annotation.Runner
public class MediaRunner implements Runner {
    @Override
    public boolean run() {

        DNSHelper.c("nytimes.com", true);
        DNSHelper.c("reuters.com", true);
        DNSHelper.c("faz.net", true);
        DNSHelper.c("cnn.com", true);
        DNSHelper.c("fox.com", true);
        DNSHelper.c("spiegel.de", true);
        DNSHelper.c("tagesschau.de", true);
        DNSHelper.c("euronews.com", true);
        DNSHelper.c("france.tv", true);
        DNSHelper.c("arte.tv", true);

        return true;
    }

    @Override
    public boolean isResponsible(CommandLine cl) {
        return true;
    }

    @Override
    public String getDescription() {
        return "Checks if news sites are being blocked.";
    }
}
