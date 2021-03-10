package telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    private final String BOT_USERNAME;
    private final String BOT_TOKEN;
    private int state = 0;

    public Bot(String BOT_USERNAME, String BOT_TOKEN) {
        this.BOT_USERNAME = BOT_USERNAME;
        this.BOT_TOKEN = BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            long chat_id = update.getMessage().getChatId();
            String text = update.getMessage().getText();

            // state != 0 means that right now user is adding a new advertisement
            if (state == 0) {
                switch (text) {
                    case "/start" -> {
                        String msg_text = """
                                Hello and welcome to Temir's Market Bot, the best bot for buying and selling products and services on the entire Telegram!
                                """;
                        sendMessage(chat_id, msg_text);
                        sendMenu(chat_id);
                    }
                    /*case "/help" -> {
                        sendMessage();
                        sendMenu();
                    }*/
                }
            }

                // https://api.telegram.org/bot1609256838:AAHl7lQCfPDySQKhkUDMChFDWSnwmfZEuhI/getUpdates
                /*String update_content = String.format(
                        """
                        update_id: %d
                        message:
                            message_id: %d
                            from:
                                id: %d
                                is_bot: %b
                                first_name: %s
                                last_name: %s
                                username: %s
                                language_code: %s
                            chat:
                                id: %d
                                first_name: %s
                                last_name: %s
                                username: %s
                                type: %s
                            date: %d
                            text: %s
                        """,
                        update.getUpdateId(),
                        update.getMessage().getMessageId(),
                        update.getMessage().getFrom().getId(),
                        update.getMessage().getFrom().getIsBot(),
                        update.getMessage().getFrom().getFirstName(),
                        update.getMessage().getFrom().getLastName(),
                        update.getMessage().getFrom().getUserName(),
                        update.getMessage().getFrom().getLanguageCode(),
                        update.getMessage().getChat().getId(),
                        update.getMessage().getChat().getFirstName(),
                        update.getMessage().getChat().getLastName(),
                        update.getMessage().getChat().getUserName(),
                        update.getMessage().getChat().getType(),
                        update.getMessage().getDate(),
                        update.getMessage().getText()
                );
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = new ArrayList<>();
                rowInline.add(newInlineButton("Add new advertisement", "new_ad"));
                rowInline.add(newInlineButton("List all advertisements", "list_ads"));
                rowsInline.add(rowInline);
                markupInline.setKeyboard(rowsInline);
                sendMessage(update.getMessage().getChatId(), update_content, markupInline);*/
        } else {
            update.hasCallbackQuery();
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();

            switch (call_data) {
                case "new_ad" -> {
                    state++;
                    sendMessage(chat_id, "What will be the title of your advertisement?");
                }
            }
        }
    }

    private InlineKeyboardButton newInlineButton(String text, String callback_data) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callback_data);
        return button;
    }

    private void sendMessage(long chat_id, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chat_id));
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(long chat_id, String text, InlineKeyboardMarkup inlineKeyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chat_id));
        message.setText(text);
        message.setReplyMarkup(inlineKeyboard);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMenu(long chat_id) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(newInlineButton("Add new advertisement", "new_ad"));
        rowInline.add(newInlineButton("List all advertisements", "list_ads"));
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        String text = "You can both buy and sell product and services using this Bot. Just press the button below";
        sendMessage(chat_id, text, markupInline);
    }
}
