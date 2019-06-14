package sample;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Encrypter {
    private static String[] encrypted = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "?", ">", "<", "`", "~", "-", "_", "+",
            "=", "]", "[", "{", "}", "/", "|"};
    private static String[] decrypted = {"YQzl9", "grnuv", "4POI6", "VvJcG", "bOMkp", "SqEi7", "ua8b3", "1TFcn", "wh9E9", "4qcs4", "ceKgO", "edlCG", "10eRZ", "3Y3gN",
            "DOLVr", "46Z7b", "rtr0W", "nKWjd", "rxq5i", "TCjao", "3o4ci", "N0c1k", "AR9hj", "7Yasn", "nLlzj", "AGabu", "l8ApV", "eeNXm", "o8BDl",
            "PK2za", "ECS9h", "uZjWI", "ecYTX", "LeCmb", "3yx76", "MB2vd", "yJ2zb", "vvIJB", "sbuoM", "JuCMK", "TpXq2", "aVBTp", "u9Jyl", "VpGwr",
            "r88Mp", "ih8fE", "whmmA", "U5fX0", "JfcWw", "NIDFP", "T5m3i", "bJY3o", "jpG2t", "nUDsJ", "ljUJ4", "PBN7g", "RrUOh", "uH73A", "j6CiF",
            "B9pqW", "XN4mC", "8XPNj", "z2Gfs", "D5Sy2", "nyvZD", "qxwNh", "ssCom", "rLC5O", "ZXz7r", "lxPAO", "bxznz", "Mx5fj", "N95ET", "XwAuA",
            "ntyDK", "jq1ba", "fO8vi", "myAt1", "yjoI5", "KA8nq", "5ePFF", "MOGew", "TABqf", "ltZme", "WjqHn", "hnvFW", "119QG"};

    public static String encrypt(String string) {
        int rotateAmount = (int) (Math.random() * 44) + 1;
        String rotateDirection = Integer.toHexString(Math.random() < 0.5 ? 56 : 69);
        String[] decrypted;
        if (Integer.toHexString(56).equals(rotateDirection))
            decrypted = rotateRight(Encrypter.decrypted, rotateAmount);
        else
            decrypted = rotateLeft(Encrypter.decrypted, rotateAmount);
        String encryptedString = "";
        for (int i = 0; i < string.length(); i++) {
            String letter = Character.toString(string.charAt(i));
            int position = Arrays.asList(Encrypter.encrypted).indexOf(letter);
            encryptedString = encryptedString.concat(decrypted[position]);
        }
        return ("♠♥♢♧☻" + Integer.toHexString(rotateAmount) + "♤♡"+rotateDirection+"♦♣☺").concat(encryptedString);
    }

    public static String decrypt(String string) {
        int rotateAmount = Integer.parseInt(string.substring(string.indexOf("☻")+1, string.indexOf("♤")), 16);
        boolean rotateRightTrue = Integer.parseInt(string.substring(string.indexOf("♡")+1, string.indexOf("♦")), 16) == 56;
        string = string.substring(string.indexOf("☺")+1);
        String[] decrypted = !rotateRightTrue ? rotateLeft(Encrypter.decrypted, rotateAmount) : rotateRight(Encrypter.decrypted, rotateAmount);
        String decryptedString = "";
        for (int i = 0; i < string.length(); i += 5) {
            String encryptedString = string.substring(i, i + 1) + string.substring(i + 1, i + 1 + 1)
                    + string.substring(i + 2, i + 2 + 1) + string.substring(i + 3, i + 3 + 1) + string.substring(i + 4, i + 4 + 1);
            int position = Arrays.asList(decrypted).indexOf(encryptedString);
            decryptedString = decryptedString.concat(Encrypter.encrypted[position]);
        }
        return decryptedString;
    }

    public static String[] rotateRight(String[] array, int amount) {
        String[] copyOfArray = new String[array.length];
        System.arraycopy(array, 0, copyOfArray, 0, array.length);
        for (int i = 0; i < amount; i++) {
            for (int j = copyOfArray.length - 1; j > 0; j--) {
                String temp = copyOfArray[j];
                copyOfArray[j] = copyOfArray[j - 1];
                copyOfArray[j - 1] = temp;
            }
        }
        return copyOfArray;
    }
    public static String[] rotateLeft(String[] array, int amount) {
        String[] copyOfArray = new String[array.length];
        System.arraycopy(array, 0, copyOfArray, 0, array.length);
        for (int i = 0; i < amount; i++) {
            for (int j = 0; j < copyOfArray.length-1; j++) {
                String temp = copyOfArray[j];
                copyOfArray[j] = copyOfArray[j + 1];
                copyOfArray[j + 1] = temp;
            }
        }
        return copyOfArray;
    }
}
