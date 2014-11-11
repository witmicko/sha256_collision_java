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

        Map<Long, Integer> map = new HashMap<>(41729761);
        boolean found = false;
        byte[] digest;
        byte[] last50;
        int i = 0;
        int max = (int)Math.pow(2,30);
        StringBuilder sb = new StringBuilder();
        while (!found) {
            sb.setLength(0);
            ++i;
            md.update(String.valueOf(i).getBytes());
            digest = md.digest();
            last50 = Arrays.copyOfRange(digest, digest.length - 8, digest.length);
            last50[0]&=0;
            last50[1]&=3;
            ByteBuffer bb = ByteBuffer.wrap(last50);
            long l = bb.getLong();

            Integer val = map.put(l, i);
            if (val != null) {
                System.out.println(val + " same as " + i + " \t\t" + Long.toBinaryString(l));
            }

        }
    }
}
//(30777358, 41729561)

