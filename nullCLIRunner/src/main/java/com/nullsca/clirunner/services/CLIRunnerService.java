package com.nullsca.clirunner.services;

import com.nullsca.clirunner.dtos.CommandResponse;

import java.io.IOException;


public interface CLIRunnerService {
    CommandResponse runCommand(String command) throws IOException, InterruptedException;
}
