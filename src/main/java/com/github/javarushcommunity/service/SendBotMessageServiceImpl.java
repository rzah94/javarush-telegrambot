package com.github.javarushcommunity.service;

import com.github.javarushcommunity.bot.JavarushTelegramBot;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final JavarushTelegramBot javaRushTelegramBot;

    public SendBotMessageServiceImpl(JavarushTelegramBot javaRushTelegramBot) {
        this.javaRushTelegramBot = javaRushTelegramBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        var sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .build();
        sendMessage.enableHtml(true);

        try {
            javaRushTelegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
