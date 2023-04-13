import logics.BlockchainNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockChainTests {
    private BlockchainNode genesis;
    private BlockchainNode blockChainNodeFirst;
    private BlockchainNode blockChainNodeSecond;
    private BlockchainNode blockChainNodeThird;

    @BeforeEach
    public void setUp() {
        genesis = new BlockchainNode(0, "", "genesis");
        blockChainNodeFirst = new BlockchainNode(1, genesis.getHash(), "first");
        blockChainNodeSecond = new BlockchainNode(2, blockChainNodeFirst.getHash(), "second");
        blockChainNodeThird = new BlockchainNode(3, blockChainNodeSecond.getHash(), "third");
    }

    @Test
    public void testCalculateHash() {
        String expectedHash = "5f881b69a5e125a15647c64990459211919f083ee283ec2b4d8967f56ddbea17";
        Assertions.assertEquals(expectedHash, genesis.calculateHashSum());
    }

    @Test
    public void testIsValidNegative() {
        Assertions.assertFalse(genesis.isValid());
        Assertions.assertFalse(blockChainNodeFirst.isValid());
        Assertions.assertFalse(blockChainNodeSecond.isValid());
        Assertions.assertFalse(blockChainNodeThird.isValid());
    }

    @Test
    public void testIsValidNegativeAfterSettingNonce() {
        blockChainNodeFirst.setNonce(8000);
        blockChainNodeSecond.setNonce(9000);
        blockChainNodeThird.setNonce(1000);
        Assertions.assertFalse(blockChainNodeFirst.isValid());
        Assertions.assertFalse(blockChainNodeSecond.isValid());
        Assertions.assertFalse(blockChainNodeThird.isValid());
    }

    @Test
    public void testIsValidPositive() {
        BlockchainNode node = new BlockchainNode(4, "", "genesis");
        node.setHash("11111111110000");
        Assertions.assertTrue(node.isValid());
        node.setHash("00001111111111");
        Assertions.assertFalse(node.isValid());

    }

    @Test
    void testBytesToHex() {
        byte[] bytes = {0x12, 0x34, (byte) 0xAB, (byte) 0xCD};
        String expected = "1234abcd";
        String actual = Utils.bytesToHex(bytes);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testBytesToHexEmpty() {
        byte[] bytes = {};
        String actual = Utils.bytesToHex(bytes);
        Assertions.assertEquals("", actual);
    }

    @Test
    void testBytesToHexNull() {
        byte[] bytes = null;
        assertThrows(NullPointerException.class, () -> Utils.bytesToHex(bytes));
    }

    @Test
    public void testCalculateHashAfterSettingNonce() {
        BlockchainNode block = new BlockchainNode(1, "prevHash", "data");
        String originalHash = block.getHash();
        block.setNonce(1);
        String newHash = block.getHash();
        assertNotEquals(originalHash, newHash);
    }

    @Test
    public void getPropertiesPositive() {
        String property = Utils.getPropertyByName("not existing");
        assertNull(property);
    }

    @Test
    public void getPropertiesNegative() {
        String property = Utils.getPropertyByName("cryptAlgorithm");
        assertEquals(property, "SHA-256");
    }
}
