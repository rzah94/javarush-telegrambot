package com.github.javarushcommunity.bot;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class JavarushTelegramBot implements LongPollingSingleThreadUpdateConsumer, SpringLongPollingBot {

    private final TelegramClient telegramClient;

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;


    public JavarushTelegramBot(@Value("${bot.token}") String token) {
        this.token = token;
        this.telegramClient = new OkHttpTelegramClient(this.token);
        System.out.println("constructor");
    }


    @Override
    public void consume(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            var message = update.getMessage().getText().trim();
            var chatId = update.getMessage().getChatId().toString();

            var messages = SendMessage.builder()
                    .chatId(chatId)
                    .text(message)
                    .build();

            try {
                telegramClient.execute(messages);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public String getBotToken() {
        return this.token;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @AfterBotRegistration
    public void afterRegistration(BotSession botSession) {
        System.out.println("Registered bot running state is: " + botSession.isRunning());
    }
}
