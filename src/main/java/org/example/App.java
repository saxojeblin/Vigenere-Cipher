package org.example;

import java.io.*;

public class App
{
    public static void main( String[] args ) throws IOException {
        //Open input files
        File key = new File("src/main/java/org/example/key.txt");
        File plaintext = new File("src/main/java/org/example/plaintext.txt");

        //Declare string variables
        String keyString, plaintextString, cipher;

        /* Get the key and plaintext into a string into proper form
        (Only alphabetic characters in lower case and have x's padded
        at the end so the length is 512 */
        keyString = getInput(key);
        plaintextString = getInput(plaintext);
        keyString = removeNotAlphabetic(keyString);
        plaintextString = removeNotAlphabetic(plaintextString);

        //Pad the plaintext file if its length is less than 512
        plaintextString = padPlaintext(plaintextString);

        cipher = vinCipher(keyString,plaintextString);

        printResults(keyString,plaintextString,cipher);
    }

    public static String getInput(File input) throws IOException {
        String inputString = "";
        String line = null;
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(input));

        while((line = reader.readLine()) != null) {
            sb.append(line);
        }
        inputString = sb.toString();
        reader.close();
        return inputString;
    }

    public static String removeNotAlphabetic(String input) {

        input = input.replaceAll("[^a-zA-Z]", "");

        return input.toLowerCase();
    }

    public static String padPlaintext(String plaintext) {
        char x = 'x';
        while(plaintext.length() < 512) {
            plaintext = plaintext + x;
        }
        return plaintext;
    }

    public static String vinCipher(String key, String plaintext) {
        //Convert strings into char arrays for convenience
        char[] plainTextArray = plaintext.toCharArray();
        char[] keyArray = new char[plainTextArray.length];
        char[] cipherArray = new char[plainTextArray.length];

        //Get the key to be the same length of the plaintext
        for (int i=0, j=0; i<plainTextArray.length; i++, j++) {
            if(j == key.length()) {
                j = 0;
            }
            keyArray[i] = key.charAt(j);
        }

        //Encrypt the plaintext using the key and cast it to the cipher Array
        for(int i=0; i< keyArray.length; i++) {
            cipherArray[i] = (char) (((plainTextArray[i] + keyArray[i]) % 26) + 'a');
        }

        //Return the cipher
        return String.valueOf(cipherArray);
    }

    public static void printResults(String key, String plaintext, String cipher) {
        System.out.print("\n\nVigenere Key:\n\n");
        int index = 0;
        int charCount = 0;
        while(index < key.length()) {
            System.out.print(key.charAt(index));
            index++;
            charCount++;
            if (charCount == 80) {
                System.out.print("\n");
                charCount = 0;
            }
        }

        System.out.print("\n\n\nPlaintext Key:\n\n");
        index = 0;
        charCount = 0;

        while(index < plaintext.length()) {
            System.out.print(plaintext.charAt(index));
            index++;
            charCount++;
            if (charCount == 80) {
                System.out.print("\n");
                charCount = 0;
            }
        }

        System.out.print("\n\n\nCiphertext:\n\n");
        index = 0;
        charCount = 0;
        while(index < cipher.length()) {
            System.out.print(cipher.charAt(index));
            index++;
            charCount++;
            if (charCount == 80) {
                System.out.print("\n");
                charCount = 0;
            }
        }

    }
}
