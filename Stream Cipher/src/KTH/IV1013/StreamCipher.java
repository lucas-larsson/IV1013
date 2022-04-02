package KTH.IV1013;

import java.io.*;

public class StreamCipher {

    public static void main(String[] args) {
        if (args.length > 3 ) {
            System.out.println("Too many arguments: " + args.length );
            System.out.println("Usage: StreamCipher <key> <inputFile> <outputFile>");
            System.exit(-1);
        }else if(args.length < 3){
            System.out.println("Too few arguments: " + args.length );
            System.out.println("Usage: StreamCipher <key> <inputFile> <outputFile>");
            System.exit(-2);
        }

        long Key = Long.parseLong(args[0]);
        File inputFile = new File(args[1]);
        File outputFile = new File(args[2]);

        MyRandom generator = new MyRandom(Key);

        try (
                InputStream in = new BufferedInputStream(new FileInputStream(inputFile));
                OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile))
            ){
            int inputByte = in.read();
            while (inputByte != -1) {
                int outputByte = inputByte ^ generator.next(8);
                out.write(outputByte);
                inputByte = in.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Some I/O Error" + e.getMessage());
            System.exit(1);
        }
    }
}
