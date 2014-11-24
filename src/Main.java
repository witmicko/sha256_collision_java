import gnu.trove.map.hash.TLongIntHashMap;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author Michal Ogrodniczak 20057303
 *
 * Simple application to find hash collision in last 50 bits of sha-256 hash function
 * LSB50(SHA256(x)) = LSB50(SHA256(y)).
 * Solution(30777358, 41729561)
 *  SHA256(30777358) = 4c563823993165aa83cb0b79913319aa426a30c7f29bb1c53da e 263aa147c8d4
 *  SHA256(41729561) = 30df2771f4f047207abead85745fd7ffbd6fe860fab731fa5fb 2 263aa147c8d4
 *                                                                      1110 263aa147c8d4
 *                                                                      0010 263aa147c8d4
 *  binary 10001001100011101010100001010001111100100011010100
 *
 *  Using Trove library as it allows for collections of primitives and dramatically lowering
 *  memory requirements for this application.
 *  recommended heapspace size ~ 4gb
 *
 *
 */
public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        long timeStart = System.currentTimeMillis();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        TLongIntHashMap map = new TLongIntHashMap();
        boolean found = false;
        byte[] digest;
        byte[] last50;
        int max = (int) Math.pow(2, 30);
        int i = 0;
        int index;
        long l;
        while (!found && i < max) {
            ++i;
            md.update(String.valueOf(i).getBytes());
            digest = md.digest();
            last50 = Arrays.copyOfRange(digest, digest.length - 8, digest.length);
            last50[0] = 0;
            last50[1] &= 3;
            l = ByteBuffer.wrap(last50).getLong();
            index = map.put(l, i);
            if (index > 0) {
                found = true;
                System.out.println(index + " same as " + i + " \t\t" + Long.toBinaryString(l));
            }
        }
        System.out.println("time taken: " + (System.currentTimeMillis() - timeStart) / 1000);
    }
}


