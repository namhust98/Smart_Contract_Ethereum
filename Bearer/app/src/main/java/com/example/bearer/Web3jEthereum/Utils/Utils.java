package com.example.bearer.Web3jEthereum.Utils;

import com.example.bearer.Web3jEthereum.ContractWrapper.Campain;
import com.example.bearer.Web3jEthereum.ContractWrapper.CampainFactory;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import java.math.BigInteger;


public class Utils {

    private Web3j web3j;
    private Credentials credentials;

    public Utils(Web3j web3j, Credentials credentials){
        this.web3j=web3j;
        this.credentials=credentials;
    }

    public Campain loadCampain(String address){
        return Campain.load(address, web3j, credentials, BigInteger.valueOf(8000000000L), BigInteger.valueOf(500000L) );
    }

    public CampainFactory loadFactory(String address){
        return CampainFactory.load(address, web3j, credentials, BigInteger.valueOf(8000000000L), BigInteger.valueOf(700000L) );
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static String byteArrayToHexString(byte[] byteArr){
        StringBuilder sb = new StringBuilder();
        for (byte b : byteArr) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
