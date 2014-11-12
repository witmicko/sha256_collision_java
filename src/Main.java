import gnu.trove.map.hash.TLongIntHashMap;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Main {
    Set<byte[]> bytes = new HashSet<byte[]>();

    public static void main(String[] args) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        TLongIntHashMap map = new TLongIntHashMap();
        boolean found = false;
        byte[] digest;
        byte[] last50;
        int max = (int)Math.pow(2,30);
        int i = 0;
        int index;
        ByteBuffer bb;
        long l;
        while (!found && i < max) {
                ++i;
                md.update(String.valueOf(i).getBytes());
                digest = md.digest();
                last50 = Arrays.copyOfRange(digest, digest.length - 8, digest.length);
                last50[0] = 0;
                last50[1] &= 3;
                bb = ByteBuffer.wrap(last50);
                l = bb.getLong();
                index = map.put(l, i);
                if (index > 0) {
                    found = true;
                    System.out.println(index + " same as " + i + " \t\t" + Long.toBinaryString(l));
                }
        }
    }
}
//(30777358, 41729561)

