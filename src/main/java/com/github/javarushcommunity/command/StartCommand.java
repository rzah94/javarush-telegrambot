package com.github.javarushcommunity.command;

import com.github.javarushcommunity.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command {
    private final SendBotMessageService sendBotMassageService;

    public final static String START_MESSAGE = "Привет. Я Javarush Telegram Bot. Я помогу тебе быть в курсе последних " +
    "статей тех авторов, которые тебе интересны. Я еще маленький и только учусь.";

    public StartCommand(SendBotMessageService sendBotMassageService) {
        this.sendBotMassageService = sendBotMassageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMassageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}
