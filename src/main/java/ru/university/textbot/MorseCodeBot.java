package ru.university.textbot;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.university.textbot.config.BotConfig;
import ru.university.textbot.processor.MorseCodeProcessor;


public class MorseCodeBot implements LongPollingSingleThreadUpdateConsumer {
    
    private final TelegramClient telegramClient;
    private final MorseCodeProcessor morseProcessor;


    public MorseCodeBot() {
        this.telegramClient = new OkHttpTelegramClient(BotConfig.BOT_TOKEN);
        this.morseProcessor = new MorseCodeProcessor();
    }

    @Override
    public void consume(Update update) {
        // Проверяем, что обновление содержит текстовое сообщение
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String userMessage = update.getMessage().getText().trim();
        long chatId = update.getMessage().getChatId();

        // Обрабатываем сообщение и получаем ответ
        String response = processMessage(userMessage);

        // Отправляем ответ пользователю
        sendText(chatId, response);
    }


    private String processMessage(String text) {
        // Обработка команд
        switch (text.toLowerCase()) {
            case "/start":
                return " Привет! Я бот для кодирования в азбуку Морзе (Вариант 18).\n\n" +
                       "Что я умею:\n" +
                       "• Кодировать текст в азбуку Морзе\n" +
                       "• Декодировать азбуку Морзе обратно в текст\n\n" +
                       "Команды:\n" +
                       "/encode <текст> - закодировать текст\n" +
                       "/decode <код> - декодировать код Морзе\n" +
                       "/help - справка\n" +
                       "\n" +
                       "Просто напиши текст, и я переведу его в азбуку Морзе!";

            case "/help":
                return "Справка по использованию:\n\n" +
                       "1) Кодирование текста:\n" +
                       "   Напишите /encode followed by ваш текст\n" +
                       "   Пример: /encode SOS\n" +
                       "   Результат: ... --- ...\n\n" +
                       "2) Декодирование:\n" +
                       "   Напишите /decode и код Морзе\n" +
                       "   Пример: /decode ... --- ...\n" +
                       "   Результат: SOS\n\n" +
                       "3) Быстрое кодирование:\n" +
                       "   Просто отправьте любой текст без команды\n" +
                       "   Бот автоматически переведет его в азбуку Морзе\n\n" +
                       morseProcessor.getSupportedCharsInfo();

            case "/encode":
                return "❌ Ошибка: укажите текст для кодирования.\n" +
                       "Пример: /encode Привет";

            case "/decode":
                return "❌ Ошибка: укажите код Морзе для декодирования.\n" +
                       "Пример: /decode ... --- ...";

            default:
                // Проверяем, не является ли сообщение командой с аргументом
                if (text.startsWith("/")) {
                    return handleCommandWithArgument(text);
                }
                // Обычный текст - кодируем в азбуку Морзе
                return encodeText(text);
        }
    }

    private String handleCommandWithArgument(String text) {
        if (text.toLowerCase().startsWith("/encode ")) {
            String message = text.substring(8).trim();
            if (message.isEmpty()) {
                return "Укажите текст после команды /encode";
            }
            return encodeText(message);
        }

        if (text.toLowerCase().startsWith("/decode ")) {
            String morseCode = text.substring(8).trim();
            if (morseCode.isEmpty()) {
                return "Укажите код Морзе после команды /decode";
            }
            return decodeText(morseCode);
        }

        return "Неизвестная команда. Напишите /help для справки.";
    }


    private String encodeText(String text) {
        String morseCode = morseProcessor.encodeToMorse(text);
        if (morseCode.isEmpty()) {
            return " В тексте нет символов, которые можно закодировать.\n" +
                   "Используйте буквы (A-Z, А-Я) или цифры (0-9).";
        }
        return " Исходный текст: " + text + "\n\n" +
               " Азбука Морзе:\n" +
               "---\n" +
               morseCode +
               "\n---";
    }


    private String decodeText(String morseCode) {
        String text = morseProcessor.decodeFromMorse(morseCode);
        if (text.isEmpty()) {
            return "Не удалось декодировать код.\n" +
                   "Проверьте правильность ввода (используйте точки, тире и пробелы).";
        }
        return "Код Морзе: " + morseCode + "\n\n" +
               "Расшифровка:\n" +
               "---\n" +
               text +
               "\n---";
    }


    private void sendText(long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка отправки сообщения: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
