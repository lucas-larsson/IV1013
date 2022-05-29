import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

/**
 * Author:      "Lucas Larsson"
 * Date:        2022-05-29
 * Description:
 **/
public class Hidenc {

    static byte[] KEY = null;
    static String INPUT_PATH = null;
    static String OUTPUT_PATH = null;
    static byte[] CTR = null;
    static byte[] INPUT_BYTES = null;
    static byte[] ENCRYPTED_DATA = null;
    static String TEMPLATE_PATH = null;
    static int OFFSET = -1;
    static int SIZE = -1;
    static Cipher cipher;



    public static void input(String[] args) {
        if (args.length < 4 || args.length > 7){
        System.out.println("please check the next line for usage instructions ");
        System.out.println("Usage: java Hidenc --key=<KEY> --input=<INPUT_FILE> --output=<OUTPUT_FILE> --offset=<NUMBER> --template=<TEMPLATE_FILE> --size=<SIZE> ");
        System.out.println("required arguments: --key=<KEY>, --input=<INPUT_FILE>, --output=<OUTPUT_FILE>, and (--size XOR --template)");
        System.exit(-1);
    }

        for (String argument : args) {
        String[] arguments = argument.split("=");
        switch (arguments[0]) {
            case "--key" -> KEY = hexStringToByteArray(arguments[1]);
            case "--input" -> INPUT_PATH = arguments[1];
            case "--output" -> OUTPUT_PATH = arguments[1];
            case "--ctr" -> CTR = hexStringToByteArray(arguments[1]);
            case "--offset " -> OFFSET = Integer.parseInt(arguments[1]);
            case "--template" -> TEMPLATE_PATH = arguments[1];
            case "--size" -> SIZE = Integer.parseInt(arguments[1]);
            default -> {
                System.out.println("Error reading argument (No match): ");
                System.out.println(argument);
                System.exit(-1);
            }
        }
    }

        if ((KEY == null) || (INPUT_PATH == null) || (OUTPUT_PATH == null) || ( (SIZE == -1) && (TEMPLATE_PATH == null) ) || ( (SIZE != -1) ^ (TEMPLATE_PATH != null) ) ) {
        System.err.println("Missing required arguments.");
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

    // main method
//    public static void main(String[] args) throws Exception {
//        input(args);
//        read_file(INPUT_PATH);
//        DECRYPTED_DATA = extractBlob(KEY, INPUT_BYTES, md5Hash(KEY));
//        writeToFile( DECRYPTED_DATA, OUTPUT_PATH);
//    }
}
