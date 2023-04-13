package logics;

import model.Block;
import util.Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BlockChain extends Block {

    public static String ALGORITHM = Utils.getPropertyByName("cryptAlgorithm");



    public BlockChain(int index, String prevHash, String data) {
        super(index, prevHash, data);
    }


    @Override
    public String toString() {
        return "Block{" +
                "index=" + index +
                ", prevHash='" + prevHash + '\'' +
                ", hash='" + hash + '\'' +
                ", data='" + data + '\'' +
                ", nonce=" + nonce +
                '}';
    }

    @Override
    public String calculateHashSum() {
        StringBuilder builder = new StringBuilder();
        builder.append(index).append(prevHash).append(data).append(nonce);
        String dataToHash = builder.toString();
        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance(ALGORITHM);
            bytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return bytes != null ? Utils.bytesToHex(bytes) : "";
    }

}


