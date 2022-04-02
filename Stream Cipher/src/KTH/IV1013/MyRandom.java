package KTH.IV1013;

import java.util.Random;

/**
 * Author:      Lucas Larsson
 * Date:        2022-03-26
 * Description:
 **/
public class MyRandom extends Random {

    long seed;
    byte[] byteKey;
    int[] sVector = new int[256];
    int a, b;

    public MyRandom(long seed) {
        setSeed(seed);
        RC4setup();
    }

    private void RC4setup() {

        //Key to byte array
        byteKey = (seed + "").getBytes();

        //Fill sVector
        for (int i = 0; i < 256; i++) {
            sVector[i] = i;
        }

        //Randomize sVector
        int j = 0;
        int tmp;
        for (int i = 0; i < 256; i++) {
            j = (j + sVector[i] + byteKey[i % byteKey.length]) % 256;
            //Swap
            tmp = sVector[i];
            sVector[i] = sVector[j];
            sVector[j] = tmp;
        }
    }

    @Override
    protected int next(int bits) {

        //Increment and randomize
        a = (a + 1) % 256;
        b = (b + sVector[a]) % 256;

        //Swap
        int swapper = sVector[a];
        sVector[a] = sVector[b];
        sVector[b] = swapper;

        return sVector[(sVector[a] + sVector[b]) % 256];
    }

    @Override
    public synchronized void setSeed(long seed) {
        this.seed = seed;
    }
}

