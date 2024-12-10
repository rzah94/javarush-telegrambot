package com.github.javarushcommunity.command;

import com.github.javarushcommunity.service.SendBotMessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

@DisplayName("Unit-level testing for CommandContainer")
class CommandContainerTest {
    private CommandContainer commandContainer;

    @BeforeEach
    void innit() {
        var sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        this.commandContainer = new CommandContainer(sendBotMessageService);
    }

    @Test
    void shouldGetAllTheExistingCommands() {
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    var command = this.commandContainer.retrieveCommand(commandName.getCommandName());
                    Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
                });
    }

    @Test
    void shouldReturnUnknownCommand() {
        var unknowCommand = "/unknowCommand";
        var command = this.commandContainer.retrieveCommand(unknowCommand);
        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }

}