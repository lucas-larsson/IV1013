package KTH.IV1013;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class SampleDigest {
    static String digestAlgorithm = "SHA-256";
//    static String textEncoding = "UTF-8";
//    Note: no error handling is implemented to check for encoding, since it is not a requirement of this assignment
    static byte [] bruteForceBits;


    public static void main(String[] args) {

        if (args.length != 1){
            System.err.println("This program needs [1] argument, you used ["+ args.length+"]");
            System.err.println("Usage: java SampleDigest <inputFile> ");
            System.exit(-1);
        }

        File inputFile = new File(args[0]);

        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(inputFile) )){

            byte[] inputBytes = in.readAllBytes();
            String plainText = new String (inputBytes, StandardCharsets.UTF_8);

            byte [] hashed = getDigest(inputBytes);
            printDigest( plainText,digestAlgorithm,  hashed);
            BruteForceDigest.bruteForced(hashed);

        } catch (FileNotFoundException e) {
            System.err.println("File: \""+ inputFile +"\" Not Found");
            System.err.println(e.getMessage());
            System.exit(-2);
        }catch (NoSuchAlgorithmException e) {
            System.out.println("Sorry could not find the Algorithm :( ");
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(-3);
        }catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(-4);
        }
    }

    // copied from the class Sample digest provided from Canvas
    private static byte[] getDigest(byte [] plainText) {

            byte[] digest = new byte[0];
            try {
                MessageDigest md = MessageDigest.getInstance(digestAlgorithm);
                md.update(plainText);
                digest = md.digest();
            } catch (NoSuchAlgorithmException e) {
                System.out.println("Algorithm \"" + digestAlgorithm + "\" is not available");
            } catch (Exception e) {
                System.out.println("Exception " + e);
            }
            return digest;
    }

    public static void printDigest(String inputText, String algorithm, byte[] digest) {
        System.out.println("Digest for the message \"" + inputText +"\", using " +
                algorithm + " is:");
        for (byte b : digest) System.out.format("%02x", b & 0xff);
//        for (int i=0; i<digest.length; i++) System.out.format("%02x", digest[i]&0xff);
    }
}