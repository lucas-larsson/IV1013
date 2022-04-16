package KTH.IV1013;

import java.io.*;
import java.util.Random;

public class StreamCipher {

    public static void main(String[] args) {


        long Key = 0;
        File inputFile;
        File outputFile;

        // error handling
        if (args.length > 3) {
            System.out.println("Too many arguments: " + args.length);
            System.out.println("Usage: StreamCipher <key> <inputFile> <outputFile>");
            System.exit(-1);
        } else if (args.length < 3) {
            System.out.println("Too few arguments: " + args.length);
            System.out.println("Usage: StreamCipher <key> <inputFile> <outputFile>");
            System.exit(-2);
        }


        try {
            Key = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("[" + args[0] + "] is not a Valid Key");
            System.exit(-5);
        }
        inputFile = new File(args[1]);
        outputFile = new File(args[2]);

//        if (! inputFile.canRead()) {
//            System.out.println("file: \""+ inputFile+ "\"  is not readable or doesn't exist");
//            System.exit(4);
//        }
        Random generator = new Random(Key);
        // I/O
        try (
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(inputFile));
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile))
        ) {
            byte[] bytes = in.readAllBytes();
            for (byte aByte : bytes) {
                int outputByte = aByte ^ generator.nextInt(256);
                out.write(outputByte);
                System.out.println(outputByte);
            }
        } catch (IOException e) {
            e.printStackTrace();        // tracers the error origin
            System.out.println("Error Message :" + e.getMessage());
            System.exit(1);
        }
    }
}
