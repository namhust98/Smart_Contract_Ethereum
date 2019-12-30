package com.example.issuer.Web3jEthereum.Roles;

import com.example.issuer.Web3jEthereum.ContractWrapper.CampainFactory;
import com.example.issuer.Web3jEthereum.Utils.Utils;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.websocket.WebSocketService;


public class General {
    protected String private_key;
    protected String address;
    protected final String infura_wss = "wss://ropsten.infura.io/ws/v3/8f21cada4d3d48db8e74bd7643afad9c";
    protected final String factory_address = "0x53Ac62b938119c042DDf1023C67824b737F5A17b";
    protected Credentials credentials;
    protected Web3j web3j;
    protected Utils utils;
    protected CampainFactory factory;

    public class CampainInfo{
        public String address;
        public String name;
        public String category;
        public String description;
        public Long endtime;
        public long total_coupons;
        public Long num_remain;
        public Long num_redeemed;
        public CampainInfo(String _address,String _name, String _category, String _description, long _endtime, long _total_coupons, long _num_remain, long _num_reddemed){
            this.address = _address;
            this.name=_name;
            this.category=_category;
            this.description=_description;
            this.endtime=_endtime;
            this.total_coupons=_total_coupons;
            this.num_remain =_num_remain;
            this.num_redeemed=_num_reddemed;
        }
    }


    public General(String _private_key){
        this.private_key=_private_key;
        this.credentials = Credentials.create(this.private_key);
        this.address=credentials.getAddress();
        WebSocketService web3jService = new WebSocketService(this.infura_wss,true);
        try {
            web3jService.connect();
        }
        catch (Exception e){
            System.out.println("Error on connect to Infura web socket.");
        }
        this.web3j = Web3j.build(web3jService);
        this.utils = new Utils(this.web3j, this.credentials);
        this.factory= utils.loadFactory(this.factory_address);
    }

}
