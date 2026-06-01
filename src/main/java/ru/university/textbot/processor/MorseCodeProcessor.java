package ru.university.textbot.processor;

/**
 * Класс для кодирования и декодирования текста в азбуку Морзе.
 * Вариант 18: Кодировщик в азбуку Морзе
 * Преобразует текст в азбуку Морзе (только буквы, пробелы между буквами).
 * Пример: "SOS" → "... --- ..."
 */
public class MorseCodeProcessor {

    // Таблица соответствия символов и кодов Морзе
    private static final java.util.Map<Character, String> MORSE_CODE_MAP = new java.util.HashMap<>();

    static {
        // Латинские буквы
        MORSE_CODE_MAP.put('A', ".-");
        MORSE_CODE_MAP.put('B', "-...");
        MORSE_CODE_MAP.put('C', "-.-.");
        MORSE_CODE_MAP.put('D', "-..");
        MORSE_CODE_MAP.put('E', ".");
        MORSE_CODE_MAP.put('F', "..-.");
        MORSE_CODE_MAP.put('G', "--.");
        MORSE_CODE_MAP.put('H', "....");
        MORSE_CODE_MAP.put('I', "..");
        MORSE_CODE_MAP.put('J', ".---");
        MORSE_CODE_MAP.put('K', "-.-");
        MORSE_CODE_MAP.put('L', ".-..");
        MORSE_CODE_MAP.put('M', "--");
        MORSE_CODE_MAP.put('N', "-.");
        MORSE_CODE_MAP.put('O', "---");
        MORSE_CODE_MAP.put('P', ".--.");
        MORSE_CODE_MAP.put('Q', "--.-");
        MORSE_CODE_MAP.put('R', ".-.");
        MORSE_CODE_MAP.put('S', "...");
        MORSE_CODE_MAP.put('T', "-");
        MORSE_CODE_MAP.put('U', "..-");
        MORSE_CODE_MAP.put('V', "...-");
        MORSE_CODE_MAP.put('W', ".--");
        MORSE_CODE_MAP.put('X', "-..-");
        MORSE_CODE_MAP.put('Y', "-.--");
        MORSE_CODE_MAP.put('Z', "--..");

        // Цифры
        MORSE_CODE_MAP.put('0', "-----");
        MORSE_CODE_MAP.put('1', ".----");
        MORSE_CODE_MAP.put('2', "..---");
        MORSE_CODE_MAP.put('3', "...--");
        MORSE_CODE_MAP.put('4', "....-");
        MORSE_CODE_MAP.put('5', ".....");
        MORSE_CODE_MAP.put('6', "-....");
        MORSE_CODE_MAP.put('7', "--...");
        MORSE_CODE_MAP.put('8', "---..");
        MORSE_CODE_MAP.put('9', "----.");

        // Русские буквы (кириллица)
        MORSE_CODE_MAP.put('А', ".-");
        MORSE_CODE_MAP.put('Б', "-...");
        MORSE_CODE_MAP.put('В', ".--");
        MORSE_CODE_MAP.put('Г', "--.");
        MORSE_CODE_MAP.put('Д', "-..");
        MORSE_CODE_MAP.put('Е', ".");
        MORSE_CODE_MAP.put('Ё', ".");
        MORSE_CODE_MAP.put('Ж', "...-");
        MORSE_CODE_MAP.put('З', "--..");
        MORSE_CODE_MAP.put('И', "..");
        MORSE_CODE_MAP.put('Й', ".---");
        MORSE_CODE_MAP.put('К', "-.-");
        MORSE_CODE_MAP.put('Л', ".-..");
        MORSE_CODE_MAP.put('М', "--");
        MORSE_CODE_MAP.put('Н', "-.");
        MORSE_CODE_MAP.put('О', "---");
        MORSE_CODE_MAP.put('П', ".--.");
        MORSE_CODE_MAP.put('Р', ".-.");
        MORSE_CODE_MAP.put('С', "...");
        MORSE_CODE_MAP.put('Т', "-");
        MORSE_CODE_MAP.put('У', "..-");
        MORSE_CODE_MAP.put('Ф', "..-.");
        MORSE_CODE_MAP.put('Х', "....");
        MORSE_CODE_MAP.put('Ц', "-.-.");
        MORSE_CODE_MAP.put('Ч', "---.");
        MORSE_CODE_MAP.put('Ш', "----");
        MORSE_CODE_MAP.put('Щ', "--.-");
        MORSE_CODE_MAP.put('Ъ', "--.--");
        MORSE_CODE_MAP.put('Ы', "-.--");
        MORSE_CODE_MAP.put('Ь', "-..-");
        MORSE_CODE_MAP.put('Э', "..-..");
        MORSE_CODE_MAP.put('Ю', "..--");
        MORSE_CODE_MAP.put('Я', ".-.-");
    }

    /**
     * Преобразует входной текст в азбуку Морзе.
     * Пробелы между словами сохраняются как "/" для разделения.
     * 
     * @param text исходный текст
     * @return строка с кодом Морзе
     */
    public String encodeToMorse(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        String upperText = text.toUpperCase();

        for (int i = 0; i < upperText.length(); i++) {
            char ch = upperText.charAt(i);

            if (ch == ' ') {
                // Разделитель между словами
                if (result.length() > 0 && result.charAt(result.length() - 1) != ' ') {
                    result.append(" / ");
                }
            } else if (MORSE_CODE_MAP.containsKey(ch)) {
                // Добавляем код символа
                if (result.length() > 0 && result.charAt(result.length() - 1) != ' ' 
                    && result.charAt(result.length() - 1) != '/') {
                    result.append(" ");
                }
                result.append(MORSE_CODE_MAP.get(ch));
            }
            // Игнорируем символы, которых нет в таблице
        }

        return result.toString().trim();
    }

    /**
     * Преобразует код Морзе обратно в текст.
     * 
     * @param morseCode строка с кодом Морзе
     * @return расшифрованный текст
     */
    public String decodeFromMorse(String morseCode) {
        if (morseCode == null || morseCode.isEmpty()) {
            return "";
        }

        // Создаем обратную карту
        java.util.Map<String, Character> reverseMap = new java.util.HashMap<>();
        for (java.util.Map.Entry<Character, String> entry : MORSE_CODE_MAP.entrySet()) {
            reverseMap.put(entry.getValue(), entry.getKey());
        }

        StringBuilder result = new StringBuilder();
        // Разделяем по пробелам, но учитываем "/" как разделитель слов
        String[] words = morseCode.split(" / ");

        for (int i = 0; i < words.length; i++) {
            String[] symbols = words[i].trim().split(" ");
            for (String symbol : symbols) {
                if (!symbol.isEmpty() && reverseMap.containsKey(symbol)) {
                    result.append(reverseMap.get(symbol));
                }
            }
            if (i < words.length - 1) {
                result.append(" ");
            }
        }

        return result.toString();
    }

    /**
     * Возвращает информацию о поддерживаемых символах.
     */
    public String getSupportedCharsInfo() {
        return "Поддерживаются: латиница (A-Z), кириллица (А-Я), цифры (0-9)";
    }
}
