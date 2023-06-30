/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author ptd
 */
public class Contant {

    public static final int PAGE_SIZE = 6;

    // code for VNPAY
    public static final String VNP_COMMAND = "pay";
    public static final String VNP_CURCODE = "VND";
    public static final String VNP_VERSION = "2.1.0";
    public static final String VNP_TMNCODE = "B3GJ4EAH";
    public static final String VNP_HASHSCRET = "RAOFLVMYIXMFIPSSRIFYIAWLBOSIJTPQ";
    public static final String VNP_URI = "http://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String VNP_ORDER_TYPE_HOTEL = "170000";

//  public static final String VNP_RETURNURL = "https://5men.netlify.app/bookingConfirm";
    public static final String VNP_RETURNURL = "http://localhost:3000/bookingConfirm";

    public static final String VNP_RETURN_URL_APP = "";

    public static final String IPN_URL_MOMO = "http://localhost:8080/wedding_photography/DispatcherServlet?btAction=PaymentConfirms";
//     public static final String IPN_URL_MOMO = "http://localhost:3000/momoWebViewConfirm";
//
    public static final String REDIRECT_URL_MOMO = "http://localhost:8080/wedding_photography/";

    public static final String PARTNER_CODE = "MOMODJMX20220717";
    public static final String ACCESS_KEY = "WehkypIRwPP14mHb";
    public static final String SECRET_KEY = "3fq8h4CqAAPZcTTb3nCDpFKwEkQDsZzz";
    public static final String MOMO_URI = "https://test-payment.momo.vn/v2/gateway/api/create";

}
