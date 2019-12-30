package com.example.bearer.Web3jEthereum.Roles;

import com.example.bearer.Web3jEthereum.Utils.Utils;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;


public class Distributor extends General {
    public Distributor(String _private_key) {
        super(_private_key);
    }

    public String generateQRForBearer(String campain_address, String bearer_address){
        byte[] campain = Utils.hexStringToByteArray(campain_address.substring(2));
        byte[] address = Utils.hexStringToByteArray(bearer_address.substring(2));
        System.out.println(campain.length+address.length);
        byte[] msg = new byte[64];
        System.arraycopy(address,0,msg,12,20);
        System.arraycopy(campain, 0,msg,44,20);
        byte[] hash = Hash.sha3(msg);
        String hash_str = Utils.byteArrayToHexString(hash);
        System.out.println(hash_str);
        Sign.SignatureData sig = Sign.signMessage(hash, Credentials.create(this.private_key).getEcKeyPair(), false);

        byte[] bytes = new byte[1];
        bytes[0] = sig.getV();
        String v_str = Utils.byteArrayToHexString(bytes);
        String r_str = Utils.byteArrayToHexString(sig.getR());
        String s_str = Utils.byteArrayToHexString(sig.getS());
        String qr_text = campain_address + " " +this.address +" "+ hash_str +" "+ v_str +" "+ r_str +" "+ s_str;
        System.out.println(qr_text);
        return qr_text;
    }
}