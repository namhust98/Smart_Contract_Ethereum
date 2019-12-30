package com.example.issuer.Web3jEthereum.Roles;

import com.example.issuer.Web3jEthereum.ContractWrapper.Campain;
import com.example.issuer.Web3jEthereum.ContractWrapper.CampainFactory;
import com.example.issuer.Web3jEthereum.Utils.Utils;

import org.web3j.abi.EventEncoder;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class Issuer extends General {
    public Issuer(String _private_key){
        super(_private_key);
    }

    public class Distributor{
        public String address;
        public long num_acquired;
        public long num_redeemed;
    }

    /** Tạo campain mới.
     @param number_of_coupons: Số lượng coupons phát động trong chiến dịch.
     @param is_free_from_issuer: true thì bearer có thể lấy  miễn phí coupon từ issuer (đây là những coupons mà bearer có thể nhìn thấy trong app, ko cần quét QR để lấy.
     @param wei_per_redeemtion: Số wei (tiền) mà mỗi distributor nhận được khi giới thiệu.
     @param campain_name: tên chiến dịch.
     @param category: tên category.
     @param description: description
     @param time_limit: thời gian tồn tại của coupons. Cái này cho user chọn ngày tháng giờ, ô tính delta vs hiện tại để quy ra giây rồi pass vào
     */
    public void createCampain(long number_of_coupons, boolean is_free_from_issuer, long wei_per_redeemtion, String campain_name,
                              String category, String description, long time_limit) throws Exception {
        TransactionReceipt txn_recv = this.factory.create_campain(BigInteger.valueOf(number_of_coupons), is_free_from_issuer, BigInteger.valueOf(wei_per_redeemtion),
                campain_name, category, description, BigInteger.valueOf(time_limit), BigInteger.valueOf(number_of_coupons*wei_per_redeemtion)).sendAsync().get();
        if(txn_recv.getBlockHash().isEmpty()) {
            throw new Exception();
        }
    }

    public List<CampainFactory.NewCampainEventResponse> getOwnedCampains() throws Exception {
        EthFilter filter =
                new EthFilter(
                        DefaultBlockParameterName.EARLIEST,
                        DefaultBlockParameterName.LATEST,
                        this.factory_address).addSingleTopic(EventEncoder.encode(CampainFactory.NEWCAMPAIN_EVENT)).addOptionalTopics("0x000000000000000000000000"+this.address.substring(2));
        List<CampainFactory.NewCampainEventResponse> events= new ArrayList<CampainFactory.NewCampainEventResponse>();
        Disposable sub = this.factory.newCampainEventFlowable(filter).subscribe(event->{events.add(event);});
        sub.dispose();
        return events;
    }

    public List<Distributor>  getOwnedDistributors(String campain_address) throws  Exception{
        Campain campain = this.utils.loadCampain(campain_address);
        List<Distributor> rets = new ArrayList<Distributor>();
        for(int i = 0; i<50; i++) {
            Distributor dis = new Distributor();
            dis.address = campain.get_distributors_address(BigInteger.valueOf(i)).sendAsync().get();
            if(dis.address.equals("0x0000000000000000000000000000000000000000")){
                continue;
            }
            List<BigInteger> status = campain.get_distributors_status(BigInteger.valueOf(i)).sendAsync().get();
            dis.num_acquired=Long.valueOf(status.get(0).longValue());
            dis.num_redeemed=Long.valueOf(status.get(1).longValue());
            rets.add(dis);
        }
        return rets;
    }

    public void addDistributor(String campain_address, String distributor_address) throws Exception{
        this.utils.loadCampain(campain_address).add_distributor(distributor_address).sendAsync().get();
    }

    public void removeDistributor(String campain_address, String distributor_address) throws Exception{
        this.utils.loadCampain(campain_address).remove_distributor(distributor_address).sendAsync().get();
    }

    // Quên chưa viết function contract này, bổ sung sau.
//    public void addBearerToCampain(String campain_address, String bearer_address) throws Exception{
//        this.utils.loadCampain(campain_address).
//    }

    public void confirmUsingCoupon(String qr_text) throws Exception {
        String[] splited = qr_text.split("\\s+");
        String campain_address = splited[0];
        String bearer_address = splited[1];
        byte[] hash = Utils.hexStringToByteArray(splited[2]);
        byte[] v = Utils.hexStringToByteArray(splited[3]);
        byte[] r = Utils.hexStringToByteArray(splited[4]);
        byte[] s = Utils.hexStringToByteArray(splited[5]);
        this.utils.loadCampain(campain_address).redeem(bearer_address,hash, new BigInteger(1, v),new BigInteger(1, r),
                new BigInteger(1, s))
                .sendAsync().get();
    }

    public void giveFreeCouponTo(String campain_address, String bearer_address) throws  Exception{
        this.utils.loadCampain(campain_address).give_coupon_to(bearer_address).sendAsync().get();
    }

}