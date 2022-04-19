package KTH.IV1013;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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

    static long counter = 0;

    static void bruteForced(byte[] digest) throws IOException, NoSuchAlgorithmException {

        byte[] guessedHash;

        System.out.print("\nCracking the Password");

        while(true) {
            bruteForceBits = longToByteArray(counter);
            MessageDigest md = MessageDigest.getInstance(digestAlgorithm);
            md.update(bruteForceBits);
            guessedHash = md.digest();
            counter++;

            boolean cracked = checkIfCracked(digest,guessedHash);
            if (cracked) return;

            if (counter % 2000000 == 0) {
                System.out.print(".");
            }
        }
    }

    /**
     * @param digest:           The original digest of the plain text
     * @param guessedHash :   The hash generated to test
     * @return True : if the first 3 bytes from @digest equal guessedHash
     */

    //Compare the first 24 bits (3 bytes) of the digests
    private static boolean checkIfCracked(byte[] digest, byte[] guessedHash ){
     if(digest[0] == guessedHash[0] && digest[1] == guessedHash[1] && digest[2] == guessedHash[2]) {
        //If the same, print the results
        System.out.println("\n\nIt took [" + counter + "] trials to generate an identical digest");
        System.out.print("\nThe brute-forced digest is:    0x");
         IntStream.range(0, 3).forEach(i -> System.out.format("%02x", guessedHash[i] & 0xff));
        return true;
    }
     else return false;
    }

    //Convert the counter value to a ByteArray
    private static byte[] longToByteArray(final long counter) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(outputStream);
        dos.writeLong(counter);
        dos.flush();
        return outputStream.toByteArray();
    }


}
