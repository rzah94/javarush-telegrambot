package com.github.javarushcommunity.bot;


import com.github.javarushcommunity.command.CommandContainer;
import com.github.javarushcommunity.service.SendBotMessageServiceImpl;
import org.jetbrains.annotations.NotNull;
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

import static com.github.javarushcommunity.command.CommandName.NO;

@Component
public class JavarushTelegramBot implements LongPollingSingleThreadUpdateConsumer, SpringLongPollingBot {

    public static String COMMAND_PREFIX = "/";

    private final TelegramClient telegramClient;
    private final CommandContainer commandContainer;
    @Value("${bot.username}")
    private String username;
    @Value("${bot.token}")
    private String token;

    public JavarushTelegramBot(@Value("${bot.token}") String token) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this));

        this.token = token;
        this.telegramClient = new OkHttpTelegramClient(this.token);
    }

    @Override
    public void consume(@NotNull Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            var message = update.getMessage().getText().trim();

            if (message.startsWith(COMMAND_PREFIX)) {
                var commandIdentifier = message.split(" ")[0].trim().toLowerCase();
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
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

    public void execute(SendMessage sendMessage) throws TelegramApiException {
        this.telegramClient.execute(sendMessage);
    }
}
