package KTH.IV1013;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.stream.IntStream;

public class SampleDigest {
    static String digestAlgorithm = "SHA-256";
//    static String textEncoding = "UTF-8";
//    Note: no error handling is implemented to check for encoding, since it is not a requirement of this assignment
    static byte [] bruteForceBits;

    public static void main(String[] args) {
            /*
            this is commented away due to a change in input for easier run

        if (args.length != 1){
            System.err.println("This program needs [1] argument, you used ["+ args.length+"]");
            System.err.println("Usage: java SampleDigest <inputFile> ");
            System.exit(-1);
        }
        File inputFile = new File(args[0]);
            */


        IntStream.range(1, 6).mapToObj(i -> new File("src/KTH/IV1013/test/" + i + ".txt")).forEach(inputFile -> {
            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(inputFile))) {

                byte[] inputBytes = in.readAllBytes();
                String plainText = new String(inputBytes, StandardCharsets.UTF_8);

                byte[] hashed = getDigest(inputBytes);
                printDigest(plainText, digestAlgorithm, hashed);

                BruteForceDigest.bruteForced( hashed);

            } catch (FileNotFoundException e) {
                System.err.println("File: \"" + inputFile + "\" Not Found");
                System.err.println(e.getMessage());
                System.exit(-2);
            } catch (NoSuchAlgorithmException e) {
                System.out.println("Sorry could not find the Algorithm :( ");
                System.err.println(e.getMessage());
                e.printStackTrace();
                System.exit(-3);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
                System.exit(-4);
            }
        });
    }

    /**
     *  copied from the class SampleDigest provided from Canvas, I modified the method to use for my program
     *  some parameters are removed and are imported as static variables EX:"digestAlgorithm"  since the assignment is
     *  only regarding the SHA-256
     *
     * @param plainText A byte array to be hashed using Java.Security class using the algorithm @digestAlgorithm
     * @return digest A byte array containing the hash of plainText
     */
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

    /**
     * A method to print out the info about the hashing, copied from Canvas
     *
     * @param inputText The inputted String to be hashed
     * @param algorithm The algorithm used to generate the hash
     * @param digest    The hash of inputText
     */
    public static void printDigest(String inputText, String algorithm, byte[] digest) {
        System.out.println("Digest for the message \"" + inputText +"\", using " +
                algorithm + " is:");
        for (byte b : digest) System.out.format("%02x", b & 0xff);
//        for (int i=0; i<digest.length; i++) System.out.format("%02x", digest[i]&0xff);
    }
}