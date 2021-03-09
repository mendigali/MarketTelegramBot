import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "TemirMarketBot";
    }

    @Override
    public String getBotToken() {
        return "1609256838:AAHl7lQCfPDySQKhkUDMChFDWSnwmfZEuhI";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

//            if (update.getMessage().getText().equals("/start")) {
                String update_content = String.format(
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
                sendMessage(update.getMessage().getChatId(), update_content, markupInline);
//            }
        } else {
            update.hasCallbackQuery();/*String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();

            if (call_data.equals("update_msg_text")) {

            }*/
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
}
