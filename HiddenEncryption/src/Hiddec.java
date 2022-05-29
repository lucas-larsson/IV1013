import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Author:      "Lucas Larsson"
 * Date:        2022-05-22
 * Description:  A class that can extract hidden files from a file.
 **/
public class Hiddec {

    static byte[] KEY = null;
    static String INPUT_PATH = null;
    static String OUTPUT_PATH = null;
    static byte[] CTR = null;
    static byte[] INPUT_BYTES = null;
    static byte[] DECRYPTED_DATA = null;
    static Cipher cipher;

    public static void input(String[] args) {
        System.out.println(Arrays.toString(args));

        if (args.length != 3 && args.length != 4) {
            System.out.println("please check the next line for usage instructions ");
            System.out.println("Usage: java Hiddec --key=<KEY> --input=<INPUT_FILE> --output=<OUTPUT_FILE>");
            System.out.println("or when using CTR");
            System.out.println("Usage: java Hiddec --key=<KEY> --input=<INPUT_FILE> --output=<OUTPUT_FILE>  --ctr=<CTR>");
            System.exit(-1);
        }

        for (String argument : args) {
            String[] arguments = argument.split("=");
            System.out.println(Arrays.toString(arguments));
            switch (arguments[0]) {
                case "--key" -> KEY = hexStringToByteArray(arguments[1]);
                case "--input" -> INPUT_PATH = arguments[1];
                case "--output" -> OUTPUT_PATH = arguments[1];
                case "--ctr" -> CTR = hexStringToByteArray(arguments[1]);
                default -> {
                    System.out.println("Error reading argument (No match): ");
                    System.out.println(argument);
                    System.exit(-1);
                }
            }
        }

        if ((KEY == null) || (INPUT_PATH == null) || (OUTPUT_PATH == null)) {
            System.err.println("Missing required arguments.");
            System.exit(-1);
        }
    }

    // hexStringToByteArray converts a hex string to a byte array
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    // read_file reads the file at the given path and returns the contents as a byte array
    public static void read_file( String path ) {
        byte[] bytes = null;
        try {
            INPUT_BYTES = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            System.out.println("Error reading file: " + path);
            System.exit(-1);
        }
    }

    // md5Hash takes a byte array and returns a byte array of the md5 hash of the input
    public static byte[] md5Hash(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(input);
        } catch (Exception e) {
            System.out.println("Error generating md5 hash");
            System.exit(-1);
        }
        return null;
    }

    public static byte[] extractBlob(byte[] key, byte[] input, byte[] hash) throws Exception {
        byte [] data;
        init(key);
        for(int i = 0; i < input.length; i += 16) {
            data = cipher.doFinal(Arrays.copyOfRange(input, i, input.length));
            if(isMatch(data, hash, 0))
                return verify(hash, data);
        }
        throw new Exception("No data found");
    }

    public static boolean isMatch(byte[] data, byte[] hash, int offset) {
        for(int i = 0; i < 16; i++) {
            if(data[i + offset] != hash[i])
                return false;
        }
        return true;
    }

    static byte[] verify(byte[] hash, byte[] data) throws Exception {

        int hashLength = hash.length, start, end, offset;
        byte[] extractedData, hashedData;

        for(offset = hashLength; offset < data.length; offset++){

            if(isMatch(data, hash, offset)) {

                extractedData = Arrays.copyOfRange(data, hashLength, offset);
                start = offset += hashLength;
                end = start + hashLength;
                hashedData = Arrays.copyOfRange(data, start, end);

                if(Arrays.equals(md5Hash(extractedData), hashedData))
                    return extractedData;

                else
                    System.out.println("Extracted data do not match verification data"); System.exit(1);
            }
        }
        throw new Exception("No data found");
    }

    // init initializes the cipher with the given key
    public static void init(byte[] key) {
        try {
            SecretKeySpec sKey = new SecretKeySpec(key, "AES");
            if (CTR == null) {
                cipher = Cipher.getInstance("AES/ECB/NoPadding");
                cipher.init(Cipher.DECRYPT_MODE, sKey);
            } else {
                cipher = Cipher.getInstance("AES/CTR/NoPadding");
                IvParameterSpec ivSpec = new IvParameterSpec(CTR);
                cipher.init(Cipher.DECRYPT_MODE, sKey, ivSpec);
            }
        } catch (Exception e) {
            System.out.println("Error initializing cipher");
            System.exit(-1);
        }
    }

    // writeToFile takes a byte array and writes it to the file at the given path
    public static void writeToFile(byte[] data, String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(data);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            System.out.println("Error writing to file: " + path);
            System.exit(-1);
        }
    }

    // main method
    public static void main(String[] args) throws Exception {
        input(args);
        read_file(INPUT_PATH);
        DECRYPTED_DATA = extractBlob(KEY, INPUT_BYTES, md5Hash(KEY));
        writeToFile( DECRYPTED_DATA, OUTPUT_PATH);
    }
}
