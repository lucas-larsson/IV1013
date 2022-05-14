package KTH.IV1013;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.IntStream;

import static KTH.IV1013.SampleDigest.bruteForceBits;
import static KTH.IV1013.SampleDigest.digestAlgorithm;


/**
 * Author:      Lucas Larsson
 * Date:        2022-04-19
 * Description: A class that uses brute-force method to guess the first 3 bytes of a hash
 **/
public class BruteForceDigest {
    static void bruteForced( byte[] digest) throws IOException, NoSuchAlgorithmException {

        byte[] guessedHash;
        long counter = 0;
        System.out.print("\nCracking the Password");

        while(true) {
            bruteForceBits = longToByteArray(counter);
            MessageDigest md = MessageDigest.getInstance(digestAlgorithm);
            md.update(bruteForceBits);
            guessedHash = md.digest();
            counter++;

            boolean cracked = checkIfCracked(digest,guessedHash, counter);
            if (cracked) return;

            // A "." ticks every 2 million guesses
            if (counter % 2000000 == 0) {
                System.out.print(".");
            }
        }
    }

    /**
     * @param digest  The original digest of the plain text
     * @param guessedHash The hash generated to test
     * @param counter counter variable indicating the numer of guesses
     * @return True : if the first 3 bytes from both digest and guessedHash are equal
     */

    //Compare the first 24 bits (3 bytes) of the digests
    private static boolean checkIfCracked(byte[] digest, byte[] guessedHash, long counter ){
     if(digest[0] == guessedHash[0] && digest[1] == guessedHash[1] && digest[2] == guessedHash[2]) {
        //If the same, print the results
         System.out.println("\n\nIt took [" + counter + "] trials to generate an identical digest");
        System.out.print("\nThe brute-forced digest is:    0x");
         IntStream.range(0, 3).forEach(i -> System.out.format("%02x", guessedHash[i] & 0xff));
         System.out.println("\n------------------------------------------------------------");
        return true;
    }
     else return false;
    }

    /**
     * Convert the counter value to a ByteArray a method I wrote inspired by this post from
     * StackOverflow "https://stackoverflow.com/questions/4485128/how-do-i-convert-long-to-byte-and-back-in-java"
     *
     * @param counter counter variable indicating the numer of guesses
     * @return  outputStream.toByteArray() A byte array of counter
     */
   
    private static byte[] longToByteArray(long counter) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(counter);
        return buffer.array();
    }
}
