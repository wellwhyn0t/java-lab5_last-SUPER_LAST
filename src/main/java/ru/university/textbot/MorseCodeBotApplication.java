package ru.university.textbot;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.university.textbot.config.BotConfig;

/**
 * Главный класс приложения для запуска Telegram-бота.
 * Вариант 18: Кодировщик в азбуку Морзе
 * 
 * Для запуска бота необходимо:
 * 1. Создать бота через @BotFather в Telegram
 * 2. Получить токен и имя бота
 * 3. Вставить их в класс BotConfig
 * 4. Запустить этот класс
 */
public class MorseCodeBotApplication {

    public static void main(String[] args) {
        System.out.println("🚀 Запуск бота 'Азбука Морзе' (Вариант 18)...");

        try {
            // Создаем экземпляр бота
            MorseCodeBot morseBot = new MorseCodeBot();

            // Создаем приложение Long Polling
            TelegramBotsLongPollingApplication botsApplication =
                    new TelegramBotsLongPollingApplication();

            // Регистрируем бота с использованием токена из конфигурации
            botsApplication.registerBot(BotConfig.BOT_TOKEN, morseBot);

            System.out.println("✅ Бот успешно запущен!");
            System.out.println("🤖 Имя бота: @" + BotConfig.BOT_USERNAME);
            System.out.println("📊 Ожидание сообщений от пользователей...");
            System.out.println("ℹ️  Для остановки нажмите Ctrl+C в консоли");
            System.out.println("");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            // Бесконечный цикл для работы приложения
            // Бот будет работать пока не будет остановлен пользователем
            Thread.currentThread().join();

        } catch (TelegramApiException e) {
            System.err.println("❌ Ошибка Telegram API: " + e.getMessage());
            System.err.println("");
            System.err.println("Возможные причины:");
            System.err.println("  • Неправильный токен бота");
            System.err.println("  • Бот не найден или заблокирован");
            System.err.println("  • Проблемы с интернет-соединением");
            System.err.println("");
            System.err.println("Проверьте данные в классе BotConfig.java");
            e.printStackTrace();
            
        } catch (InterruptedException e) {
            System.out.println("");
            System.out.println("👋 Бот остановлен пользователем");
            System.out.println("До свидания!");
            
        } catch (Exception e) {
            System.err.println("❌ Неожиданная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
