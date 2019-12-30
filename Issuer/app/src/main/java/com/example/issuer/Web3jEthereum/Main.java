package com.example.issuer.Web3jEthereum;

import com.example.issuer.Web3jEthereum.Roles.Bearer;
import com.example.issuer.Web3jEthereum.Roles.Distributor;
import com.example.issuer.Web3jEthereum.Roles.Issuer;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.internal.operators.flowable.FlowableDistinct;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.tx.Contract;

import static org.web3j.tx.Transfer.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Start..");
        Issuer iss = new Issuer("227955d7ff410da1c6f0e50c8f8280d7ee37ca75590302b69b7b66797d34f968");
        Bearer br = new Bearer("fdd865dc00505e217a4327c1f9a7ec94dc705a85da85a702ea4af93ac0acdcad");
        Distributor dis = new Distributor("6e2211d3e98c4fc33906246c11bc900cb0302e7cec62628f882ca17273dbc1f8");

        // Tạo campain mới
//        iss.createCampain(90,true, 100,"Campain #100000000", "car", "Free all items!!!", 2000000000);

        // Lấy danh sách các campain mà issuer đã tạo.
//        for(CampainFactory.NewCampainEventResponse e: iss.getOwnedCampains()){
//            System.out.println("Campain Address: "+e._address);
//            System.out.println("Campain Name: "+e._name);
//            System.out.println("Campain_Description: "+e._description);
//            System.out.println("Campain Num_coupons: " + e._num_coupon);
//            System.out.println("Campain Endtime: "+ e._end_time);
//            System.out.println("--------------------------------");
//        }

//         Lấy danh sách thông tin các distributor tham gia vào một campain.
//        for(Issuer.Distributor dis: iss.getOwnedDistributors("0x80FCB8Eb00AAc5E716E28C8d9C79fb1b5D012230")){
//            System.out.println(dis.address+"  "+dis.num_redeemed+"  "+ dis.num_acquired);
//            System.out.println("-------------------------------");
//        }

        // Thêm distributor.
//        iss.addDistributor("0xBb027De57722739Ef1963503776aC94Ad2BB7d03","0xA9EFa90e062F4CcA3887a227F332Fd39f4B71901");

        // Xóa distributor.
//        iss.removeDistributor("0xf23a159Fc06FfbedF103BA67905000E18b26B734","0xf3A566312478c62e05B1958020B3e837C8E21B42");

        // Kịch bản khi Bearer quét mã QR để nhận coupon
        // Đầu tiên, giả sử rằng QR đã tồn tại, tức là được distributor tạo ra cho mình bearer có thể sử dụng
        // Bearer quét QR và nhận được đoạn qr_text
//        String qr_text = dis.generateQRForBearer("0xBb027De57722739Ef1963503776aC94Ad2BB7d03", "0x7C81d26eA0b2B89B871bdc091f7895782804Df3c");

//        // Ngay khi quét được, bearer gọi tới hàm này để nhận coupon.
//        br.aquire(qr_text);

//         Lấy danh sách các campain mà bearer đã lấy coupon thành công.
//        for(General.CampainInfo e: br.getAllAcquiredCampains()){
//            System.out.println("Campain Address: "+e.address);
//            System.out.println("Campain Name: "+e.name);
//            System.out.println("Campain_Description: "+e.description);
//            System.out.println("Campain Num_coupons: " + e.total_coupons);
//            System.out.println("Campain Num_remain: " + e.num_remain);
//            System.out.println("Campain Num_redeemed: " + e.num_redeemed);
//            System.out.println("Campain Endtime: "+ e.endtime);
//            System.out.println("--------------------------------");
//        }

        //
//        String qr_for_using = br.generateQRToConfirmUsingCoupon("0xBb027De57722739Ef1963503776aC94Ad2BB7d03");

        //
//        iss.confirmUsingCoupon(qr_for_using);

        // Lấy danh sách các campain mà issuer đã tạo.
//        for(CampainFactory.NewCampainEventResponse e: br.getAllFreeCoupons()){
//            System.out.println("Campain Address: "+e._address);
//            System.out.println("Campain Name: "+e._name);
//            System.out.println("Campain_Description: "+e._description);
//            System.out.println("Campain Num_coupons: " + e._num_coupon);
//            System.out.println("Campain Endtime: "+ e._end_time);
//            System.out.println("--------------------------------");
//        }


        System.out.println("End!");
    }
}
