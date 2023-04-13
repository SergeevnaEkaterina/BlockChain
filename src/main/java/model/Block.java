package model;

import lombok.Data;

@Data
public abstract class Block {
    public int index;
    public String prevHash;
    public String hash;
    public String data;
    public int nonce;

    public Block(int index, String prevHash, String data) {
        this.index = index;
        this.prevHash = prevHash;
        this.data = data;
        this.nonce = 0;
        this.hash = calculateHashSum();
    }


    public void setNonce(int nonce) {
        this.nonce = nonce;
        this.hash = calculateHashSum();
    }

    public abstract String calculateHashSum();

}
