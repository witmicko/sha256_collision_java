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

        Map<BigInteger,Integer> map= new HashMap<>(9099999);
        boolean found = false;
        byte[] digest;
        byte[] last50;
        BigInteger bg;
        int i = 0;
            while (!found) {
                ++i;
                md.update(String.valueOf(i).getBytes());
                digest = md.digest();
                last50 = Arrays.copyOfRange(digest, digest.length - 8, digest.length);
                last50[0] = (byte) (last50[0] & 3);
                bg = new BigInteger(last50);

                Integer val = map.put(bg,i);
                if (val != null) {
                    System.out.println(val + "same as " + i);
                    System.out.println(Long.toBinaryString(bg.longValue()));
                }
            }
    }
}
