package KTH.IV1013;

import java.io.*;
import java.nio.ByteBuffer;

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

        Integer key = Integer.valueOf(args[0]);
        File inputFile = new File(args[1]);
        File outputFile = new File(args[2]);
        try (
               // BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                // BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
                InputStream in = new BufferedInputStream(new FileInputStream(inputFile));
                OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile))
            )
        {
            System.out.println(key);
            String st;
           // while ((st = in..readLine()) != null) {
              //  System.out.println(st);
                 RC4 r = new RC4(ByteBuffer.allocate(4).putInt(key).array());
                 byte[] y;
                y = r.encrypt(in.readAllBytes());
                out.write(y);
            //}
        } catch (IOException e) {
            System.out.println("Some I/O Error" + e.getMessage());
            System.exit(1);
        }
        // write your code here
        System.out.println("wazzup22");
    }

}
