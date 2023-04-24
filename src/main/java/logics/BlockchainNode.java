package logics;


import util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BlockchainNode extends BlockChain {


    public static String GENESIS_BLOCK_DATA = Utils.getPropertyByName("genesisBlock");
    public static String BLOCK_CHAIN_NODE_DATA = Utils.getPropertyByName("blocksEachNode");
    public static String VALID_FLAG = Utils.getPropertyByName("validFlag");
    public static String NODE_COUNT = Utils.getPropertyByName("nodeCount");


    public BlockchainNode(int index, String prevHash, String data) {
        super(index, prevHash, data);
    }


    public boolean isValid() {
        return hash.endsWith(VALID_FLAG);
    }

    public static void main(String[] args) {
        List<BlockchainNode> blockchain = new ArrayList<>();
        BlockchainNode genesis = new BlockchainNode(0, "", GENESIS_BLOCK_DATA);
        calculateNonce(genesis, 0, blockchain);
        blockchain.add(genesis);
        for (int i = 1; ; i++) {
            BlockchainNode node = null;
            if (i == 1) {
                node = new BlockchainNode(i, genesis.getHash(), BLOCK_CHAIN_NODE_DATA + i);
            } else {
                node = new BlockchainNode(i, blockchain.get(i - 1).getHash(), BLOCK_CHAIN_NODE_DATA + i);
            }
            calculateNonce(node, i, blockchain);
            blockchain.add(node);

        }
    }

    public static void updateBlockChainWithNewBlock(List<BlockchainNode> blockchain, BlockchainNode node, int initial){
        for (int k = initial + 1; k < blockchain.size(); k++) {
            BlockchainNode nextNode = blockchain.get(k);
            if (node.getHash().equals(nextNode.getPrevHash())) {
                blockchain.set(k, node);
                break;
            }
        }
    }

    public static void calculateNonce(BlockchainNode node, int index, List<BlockchainNode> blockchain){
        Random random = new Random();
        if (!node.isValid()) {
            int nonce = random.nextInt();
            while (!Thread.currentThread().isInterrupted()) {
                node.setNonce(nonce);
                if (node.isValid()) {
                    break;
                }
                nonce++;
            }
            System.out.println("Node " + index + " found: " + node.getHash());
            System.out.println("Node " + index + " data: " + node.getData());
            System.out.println("Node " + index + " nonce: " + node.getNonce());
            updateBlockChainWithNewBlock(blockchain, node, index);
        }
    }

}
