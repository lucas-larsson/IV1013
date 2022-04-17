package KTH.IV1013;

import java.math.BigInteger;

/**
 * Author:      Lucas Larsson
 * Date:        2022-04-17
 * Description: A program that reads two hashes and count different bits in them
 **/
public class BitCounter {
    public static void main(String[] args) {

        String A = toHex(args[0]);
        String B = toHex(args[1]);
        System.out.println(A);
        System.out.println(A.length());
        System.out.println(B);
        System.out.println(B.length());

        int counter = 0;
        int i = 0;
        while (i < A.length()) {
            if (A.charAt(i) != (B.charAt(i))) counter++;
            i++;
        }
        System.out.println("["+counter+"] diffrent bits of total ["+ A.length()+"]");
    }

    // okey method
    public static String toHex(String a) {
        return new BigInteger(a, 16).toString(2);
    }
    /**
     *  EX: the previous one might put 01 for input of 0x1 the below method will always
     *  append 0001 for an input of 0x1
     */

    // Great method, not as little as the privious but it takes into account, extra 0 and such
    private String hexToBin(String hex){
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }
}
