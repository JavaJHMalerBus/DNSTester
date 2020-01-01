package de.fheuschen.dns;

import org.apache.commons.cli.CommandLine;

/**
 *
 */
public interface Runner {

    /**
     * Executes the runner.
     * @return Return false if an error occurred and the script should exit
     */
    public boolean run();

    /**
     * Decides whether the runner is responsible under the given circumstances.
     * @param cl
     * @return true, if the runner should be executed.
     */
    public boolean isResponsible(CommandLine cl);

    /**
     *
     * @return
     */
    public default String getDescription() {
        return "No description provided.";
    }

}
