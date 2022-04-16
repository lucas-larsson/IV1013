package KTH.IV1013;

import java.util.Arrays;
import java.util.Random;

/**
 * Author:      Lucas Larsson
 * Date:        2022-03-26
 * Description:
 **/
public class MyRandom extends Random {

    long seed;
    byte[] byteKey;
    int[] s = new int[256];
    int a, b;

    public MyRandom(long seed) {
        setSeed(seed);
        RC4();
    }

    private void RC4() {
        // inserts the Key to a byte array s
        byteKey = (seed + "").getBytes();
        for (int i = 0; i < 256; i++) {
            s[i] = i;
        }
        // Randomizes s
        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + s[i] + byteKey[i % byteKey.length]) % 256;
            swap( i, j);
        }
    }

    // next() method that overrides the superclasses
    @Override
    protected int next(int bits) {
        //Increment and randomize
        a = (a + 1) % 256;
        b = (b + s[a]) % 256;
        swap( b, a );
        return s[(s[a] + s[b]) % 256];
    }

    // setSeed() method that overrides the superclasses
    @Override
    public synchronized void setSeed(long seed) {
        this.seed = seed;
    }

    // swap method
    public void swap(int i, int j){
        int temp = s[i];
        s[i] = s[j];
        s[j] = temp;
    }
}