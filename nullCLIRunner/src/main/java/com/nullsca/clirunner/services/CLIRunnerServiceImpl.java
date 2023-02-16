package com.nullsca.clirunner.services;

import com.nullsca.clirunner.dtos.CommandResponse;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class CLIRunnerServiceImpl implements CLIRunnerService {

    private final CommandResponse commandResponse;

    public CLIRunnerServiceImpl() {
        this.commandResponse = new CommandResponse();
    }
    @Override
    public CommandResponse runCommand(String command) throws IOException, InterruptedException {
        if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
            command = "cmd.exe /C " + command;
        }
        Process process = Runtime.getRuntime().exec(command);

        BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stderrReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        StringBuilder outputBuilder = new StringBuilder();
        String line;
        while ((line = stdoutReader.readLine()) != null) {
            outputBuilder.append(line).append(System.lineSeparator());
        }
        while ((line = stderrReader.readLine()) != null) {
            outputBuilder.append(line).append(System.lineSeparator());
        }

        int rc = process.waitFor();

        commandResponse.setOutput(outputBuilder.toString());
        commandResponse.setRc(rc);

        return commandResponse;
    }
}
