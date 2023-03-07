package com.nullsca.clirunner.controllers;

import com.nullsca.clirunner.dtos.CommandRequest;
import com.nullsca.clirunner.dtos.ErrorResponse;
import com.nullsca.clirunner.services.CLIRunnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
public class CLIRequestController {

    private final CLIRunnerService cliRunnerService;

    public CLIRequestController(CLIRunnerService cliRunnerService) {
        this.cliRunnerService = cliRunnerService;
    }

    @PostMapping("/nullsca/wscli/v1/run")
    public ResponseEntity<?> commandInterceptor(@RequestBody CommandRequest request) {
        if (request.getCommand() == null || request.getCommand().trim().isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse("Command parameter not supplied"), HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok(cliRunnerService.runCommand(request.getCommand()));
        } catch (IOException | InterruptedException e) {
            return new ResponseEntity<>(new ErrorResponse("Error running command: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
