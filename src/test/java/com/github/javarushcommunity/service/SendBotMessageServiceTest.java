package com.github.javarushcommunity.service;

import com.github.javarushcommunity.bot.JavarushTelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@DisplayName("Unit-level testing for SendBotMessageService")
class SendBotMessageServiceTest {

    private SendBotMessageService sendBotMessageService;
    private JavarushTelegramBot javaRushBot;

    @BeforeEach
    void init() {
        this.javaRushBot = Mockito.mock(JavarushTelegramBot.class);
        this.sendBotMessageService = new SendBotMessageServiceImpl(javaRushBot);
    }

    @Test
    void shouldProperlySendMessage() throws TelegramApiException {
        var chatId = "test_chat_id";
        var message = "test_message";

        var sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .build();

        sendMessage.enableHtml(true);

        sendBotMessageService.sendMessage(chatId, message);

        Mockito.verify(javaRushBot).execute(sendMessage);
    }
}