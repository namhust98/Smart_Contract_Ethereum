package com.example.bearer.Web3jEthereum.Roles;

import com.example.bearer.Web3jEthereum.ContractWrapper.Campain;
import com.example.bearer.Web3jEthereum.ContractWrapper.CampainFactory;
import com.example.bearer.Web3jEthereum.Utils.Utils;

import org.web3j.abi.EventEncoder;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class Bearer extends General {
    public Bearer(String _private_key){
        super(_private_key);
    }

    // Trả về tất cả các campains mà bearer đã nhận được coupons, có thể sử dụng tại quầy.
    public List<CampainInfo> getAllAcquiredCampains() throws Exception {

        EthFilter filter =
                new EthFilter(
                        DefaultBlockParameterName.EARLIEST,
                        DefaultBlockParameterName.LATEST,
                        this.factory_address).addSingleTopic(EventEncoder.encode(CampainFactory.ACQUIRE_EVENT)).addOptionalTopics("0x000000000000000000000000"+this.address.substring(2));
        List<CampainInfo> campain_infos= new ArrayList<CampainInfo>();
        Disposable sub = this.factory.acquireEventFlowable(filter).subscribe(event->{
            String campain_addr=event._campain;
            Campain cp = this.utils.loadCampain(campain_addr);
            List<Boolean> br_status = cp.get_bearer_status(this.address).sendAsync().get();
            Boolean is_transfered = br_status.get(1);
            Boolean is_redeemed = br_status.get(2);
            if(is_transfered || is_redeemed ){}
            else{
            List<BigInteger> status= cp.get_campain_status().sendAsync().get();
            campain_infos.add(new CampainInfo(
                    campain_addr,
                    cp.get_campain_name().sendAsync().get(),
                    cp.get_campain_category().sendAsync().get(),
                    cp.get_campain_description().sendAsync().get(),
                    cp.get_campain_endtime().sendAsync().get().longValue(),
                    status.get(0).longValue(),
                    status.get(1).longValue(),
                    status.get(2).longValue()));
        }});
        sub.dispose();
        return campain_infos;
    }

    public void aquire(String qr_text) throws  Exception {
        String[] splited = qr_text.split("\\s+");
        String campain_address = splited[0];
        String distributor_address = splited[1];
        byte[] hash = Utils.hexStringToByteArray(splited[2]);
        byte[] v = Utils.hexStringToByteArray(splited[3]);
        byte[] r = Utils.hexStringToByteArray(splited[4]);
        byte[] s = Utils.hexStringToByteArray(splited[5]);
        this.utils.loadCampain(campain_address).acquire(hash,new BigInteger(1, v),new BigInteger(1, r),
                new BigInteger(1, s), distributor_address)
                .sendAsync().get();
    }

    // Bearer sử dụng coupon tại quầy, mở hình QR code để quầy (issuer) quét và confirm sử dụng coupon.
    public String generateQRToConfirmUsingCoupon(String campain_address){
        byte[] campain = Utils.hexStringToByteArray(campain_address.substring(2));
        byte[] address = Utils.hexStringToByteArray(this.address.substring(2));
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
        String qr_text = campain_address + " " + this.address+ " " + hash_str + " " + v_str + " " + r_str + " " + s_str;
        System.out.println(qr_text);
        return qr_text;
    }

    public void transferCoupon(String campain_address, String to) throws Exception{
        this.utils.loadCampain(campain_address).transfer_coupon(to);
    }

    public List<CampainFactory.NewCampainEventResponse> getAllFreeCoupons(){
        EthFilter filter =
                new EthFilter(
                        DefaultBlockParameterName.EARLIEST,
                        DefaultBlockParameterName.LATEST,
                        this.factory_address).addSingleTopic(EventEncoder.encode(CampainFactory.NEWCAMPAIN_EVENT)).addNullTopic().addOptionalTopics("0x0000000000000000000000000000000000000000000000000000000000000001");
        List<CampainFactory.NewCampainEventResponse> events= new ArrayList<CampainFactory.NewCampainEventResponse>();
        Disposable sub = this.factory.newCampainEventFlowable(filter).subscribe(event->{events.add(event);});
        sub.dispose();
        return events;
    }

    public void getFreeCoupon(String campain_address) throws Exception{
        this.utils.loadCampain(campain_address).free_acquire_from_issuer().sendAsync().get();
    }

}
