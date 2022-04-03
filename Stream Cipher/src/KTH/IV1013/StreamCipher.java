package KTH.IV1013;

import java.io.*;

public class StreamCipher {

//        private static final int BUFFER_SIZE = 8;

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
        }

        inputFile = new File(args[1]);
        outputFile = new File(args[2]);

        MyRandom generator = new MyRandom(Key);

        // I/O
        try (
                InputStream in = new FileInputStream(inputFile);
                OutputStream out = new FileOutputStream(outputFile)
        ) {
//            byte[] buffer = new byte[BUFFER_SIZE];
//            int bytesRead = -1;
//
//            while ((bytesRead = in.read(buffer)) != -1) {
//                if (buffer == 48)  bit = 0; else {bit = 1;}
//                int outputByte = bytesRead ^ generator.next(8);
////                out.write(buffer, 0, bytesRead);
//                out.write(outputByte);
//                System.out.println(Arrays.toString(buffer));
//                System.out.println(generator.next(8));
//                System.out.println(outputByte);
//            }


            int inputByte = in.read();
            while (inputByte != -1) {
//                System.out.println(inputByte);
                // if (inputByte == 48)  bit = 0; else {bit = 1;}
                // System.out.println(bit);
                int outputByte = inputByte ^ generator.next(8);
                out.write(outputByte);
                inputByte = in.read();
            }
        } catch (IOException e) {
            e.printStackTrace();        // tracers the error origin
            System.out.println("Error Message :" + e.getMessage());
            System.exit(1);
        }
    }
}
