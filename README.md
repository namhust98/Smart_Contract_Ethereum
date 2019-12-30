# Smart_Contract_Ethereum
Tổng quan
--------

- Đây là một ứng dụng phát hành Coupon dựa trên nền tảng smart contract của Ethereum
- File source code (coupon_on_blockchain-master) viết bằng Vyper, được deploy lên mạng ropsten testnest
- 2 file bearer (người dùng) và Issuer (người phát hành) được viết bằng android, deploy chạy trên điện thoại android với phiên bản từ 4.4 trở đi

Chức năng
--------

- Issuer có thể tạo chiến dịch, tạo coupon, phát hành coupon, kích hoạt coupon người dùng bằng cách quét mã QR
- Bearer có thể lấy coupon, sử dụng coupon bằng cách gen ra QR để issuer quét, hoặc tặng coupon cho người dùng khác

Các thư viện sử dụng
--------

- Http request (android)
- Web3j (connect từ android tới mạng ethereum)
- vyper (ngôn ngữ lập trình có hỗ trợ cho smart contract ethereum)
