package com.github.javarushcommunity.command;

import com.github.javarushcommunity.bot.JavarushTelegramBot;
import com.github.javarushcommunity.service.SendBotMessageService;
import com.github.javarushcommunity.service.SendBotMessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

abstract class AbstractCommandTest {
    protected JavarushTelegramBot javarushBot = Mockito.mock(JavarushTelegramBot.class);
    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(javarushBot);

    abstract String getCommandName();

    abstract String getCommandMessage();

    abstract Command getCommand();

    @Test
    void shouldProperlyExecuteCommand() throws TelegramApiException {
        Long chatId = 1234567824356L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(getCommandName());
        update.setMessage(message);

        var sendMessage = SendMessage.builder()
                .chatId(chatId.toString())
                .text(getCommandMessage())
                .build();
        sendMessage.enableHtml(true);

        getCommand().execute(update);
        Mockito.verify(javarushBot).execute(sendMessage);
    }

}
