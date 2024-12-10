package com.github.javarushcommunity.command;

import com.github.javarushcommunity.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.javarushcommunity.command.CommandName.*;

public class HelpCommand implements Command {
    private final SendBotMessageService sendBotMassageService;

    public final static String HELP_MESSAGE = String.format("✨<b>Дотупные команды</b>✨\n\n"

                                                                           + "<b>Начать\\закончить работу с ботом</b>\n"
                                                                           + "%s - начать работу со мной\n"
                                                                           + "%s - приостановить работу со мной\n\n"
                                                                           + "%s - получить помощь в работе со мной\n",
            START.getCommandName(), STOP.getCommandName(), HELP.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMassageService) {
        this.sendBotMassageService = sendBotMassageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMassageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
