package com.github.javarushcommunity.command;

import com.github.javarushcommunity.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class NoCommand implements Command {
    private final SendBotMessageService sendBotMassageService;

    public final static String NO_MESSAGE = "Я поддерживаю команды, начинающиеся со слеша(/).\n"
                                            + "Чтобы посмотреть список команд введите /help";

    public NoCommand(SendBotMessageService sendBotMassageService) {
        this.sendBotMassageService = sendBotMassageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMassageService.sendMessage(update.getMessage().getChatId().toString(), NO_MESSAGE);
    }
}
