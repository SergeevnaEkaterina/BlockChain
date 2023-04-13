package logics;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BlockchainNode extends BlockChain {


    public BlockchainNode(int index, String prevHash, String data) {
        super(index, prevHash, data);
    }


    public boolean isValid() {
        return hash.endsWith("0000");
    }

    public static void main(String[] args) {
        int nodeQuantity = 3;
        List<BlockchainNode> blockchain = new ArrayList<>();
        BlockchainNode genesis = new BlockchainNode(0, "", "genesis");
        blockchain.add(genesis);
        for (int i = 1; i < nodeQuantity; i++) {
            BlockchainNode node = new BlockchainNode(i, genesis.getHash(), "node" + i);
            blockchain.add(node);
        }
        while (true) {
            for (int i = 0; i < blockchain.size(); i++) {
                BlockchainNode node = blockchain.get(i);
                calculateNonce(node, i, blockchain);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
            updateBlockChainWithNewBlock(blockchain, node, index);
        }
    }

}
