package com.example.cccp.classified;

/**
 * Created by CCCP on 18.07.2017.
 */
public class Encoder {
    static String alphabet[] = {"А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "Й", "К", "Л", "М",
            "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Ъ", "Ы", "Ь",
            "Э", "Ю", "Я",
            "а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м",
            "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь",
            "э", "ю", "я",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
            "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "!", "@", "#", "$", "%", "%", "^", "&", "*", "(", ")", "-", "_","+", "=",
            "{", "}", "[", "]", ":", ";", "'", " ", ",", "<", ">", ".", "?", "/"};

    public String encryption(String original_text, int key){
        String text = original_text;

        String alf[] = alphabet;

        String[] m = new String[text.length()];

        for (int i = 0; i < text.length(); i++) {
            m[i] = text.substring(i, i+1);
        }

        String[] m2 = m;

        for (int i = 0; i < m.length; i++){
            for (int z = 0; z < alf.length; z++) {
                if (m[i].equals(alf[z])) {
                    if (z + key >= alf.length-1){
                        m2[i] = alf[z - (alf.length - key)];
                    }
                    else {
                        m2[i] = alf[z + key];
                    }
                    break;
                }
            }
        }

        text = "";

        for (int i = 0; i < m2.length; i++){
            text += m2[i];
        }

        return text;
    }

    public String decryption(String encrypted_text, int key){
        String text = encrypted_text;

        String alf[] = alphabet;

        String[] m = new String[text.length()];

        for (int i = 0; i < text.length(); i++) {
            m[i] = text.substring(i, i+1);
        }

        String[] m2 = m;

        for (int i = 0; i < m2.length; i++){
            for (int z = 0; z < alf.length; z++) {
                if (m2[i].equals(alf[z])) {
                    if (z - key < 0){
                        m2[i] = alf[alf.length + (z - key)];
                    }
                    else {
                        m2[i] = alf[z - key];
                    }
                    break;
                }
            }
        }

        text = "";

        for (int i = 0; i < m2.length; i++){
            text += m2[i];
        }

        return text;
    }
}
