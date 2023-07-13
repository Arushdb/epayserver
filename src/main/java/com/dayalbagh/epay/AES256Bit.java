package com.dayalbagh.epay;

//
//Decompiled by Procyon v0.5.36
//

import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;

import org.apache.commons.codec.binary.Base64;
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
import java.security.spec.AlgorithmParameterSpec;
import java.text.SimpleDateFormat;
import java.security.Key;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES256Bit
{
 private static String res;
 private static byte[] iv;
 
 static {
     AES256Bit.iv = null;
 }
 
 public static String encrypt(final String s, final SecretKeySpec secretkeyspec) {
     String s2 = "";
     try {
         final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
         final IvParameterSpec ivparameterspec = new IvParameterSpec(AES256Bit.iv);
         cipher.init(1, secretkeyspec, ivparameterspec);
         final byte[] abyte0 = cipher.doFinal(s.getBytes("UTF-8"));
         //final BASE64Encoder base64encoder = new BASE64Encoder();
         s2 =Base64.encodeBase64String(abyte0);
         //s2 = base64encoder.encode(abyte0);
     }
     catch (Exception ex) {}
     return s2;
 }
 
 public static String decrypt(final String s, final SecretKeySpec secretkeyspec) {
     String s2 = "";
     try {
         final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
         final IvParameterSpec ivparameterspec = new IvParameterSpec(AES256Bit.iv);
         cipher.init(2, secretkeyspec, ivparameterspec);
         final byte[] abyte0 =Base64.decodeBase64(s);
         //final BASE64Decoder base64decoder = new BASE64Decoder();
         //final byte[] abyte0 = base64decoder.decodeBuffer(s);
         final byte[] abyte2 = cipher.doFinal(abyte0);
         s2 = new String(abyte2);
     }
     catch (Exception ex) {}
     return s2;
 }
 
 public static String asHex(final byte[] abyte0) {
     final StringBuffer stringbuffer = new StringBuffer();
     for (int i = 0; i < abyte0.length; ++i) {
         stringbuffer.append(Integer.toHexString(256 + (abyte0[i] & 0xFF)).substring(1));
     }
     return stringbuffer.toString();
 }
 
 public static SecretKeySpec readKeyBytes(final String s) {
     SecretKeySpec secretkeyspec = null;
     int i = 0;
     final byte[] abyte0 = new byte[16];
     try {
         AES256Bit.res = s;
         final String s2 = AES256Bit.res;
         final byte[] abyte2 = s2.getBytes("UTF8");
         final byte[] abyte3 = s2.getBytes();
         int j = 0;
     Label_0065_Outer:
         while (j < 16) {
             boolean flag1 = false;
             while (true) {
                 while (i < abyte2.length) {
                     if (j != i) {
                         continue Label_0065_Outer;
                     }
                     flag1 = true;
                     if (flag1) {
                         abyte0[j] = abyte2[j];
                     }
                     ++j;
                     ++i;
                     continue Label_0065_Outer;
                 }
                 continue;
             }
         }
         AES256Bit.iv = abyte0;
         secretkeyspec = new SecretKeySpec(abyte0, "AES");
     }
     catch (Exception ex) {}
     return secretkeyspec;
 }
 
 public static String byteToHex(final byte byte0) {
     final char[] ac = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
     final char[] ac2 = { ac[byte0 >> 4 & 0xF], ac[byte0 & 0xF] };
     return new String(ac2);
 }
 
 public static String generateNewKey() {
     String newKey = null;
     try {
         final KeyGenerator kgen = KeyGenerator.getInstance("AES");
         kgen.init(256);
         final SecretKey skey = kgen.generateKey();
         final byte[] raw = skey.getEncoded();
         
      //   newKey = new BASE64Encoder().encode(raw);
         
         newKey = Base64.encodeBase64String(raw);
         newKey = newKey.replace("+", "/");
     }
     catch (Exception ex) {}
     return newKey;
 }
 
// public static void main(String args[]) {
//		String MerchantId = "1000112";
//        String AggregatorId = "SBIEPAY";
//        String SuccessURL = "https://admission.dei.ac.in:8443/epay80/";
//        String FailURL = "https://admission.dei.ac.in:8443/epay80/";
//        String  OperatingMode = "DOM";
//        String  MerchantCountry = "IN";
//        String  MerchantCurrency = "INR";
//        String  TotalDueAmount = "100";
//       // String  MerchantOrderNo = "TXN202105061303111539"; //Order no shoud be unique every time
//        
//        
//        String MerchantOrderNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
//
//        
//        
//        String  MerchantCustomerID = "2";
//        String  Paymode = "NB";
//        String Accesmedium = "ONLINE";
//        String  TransactionSource = "ONLINE";
//        String Otherdetail ="Arush;212099;SM1";
//        String key_Array = "A7C9F96EEE0602A61F184F4F1B92F0566B9E61D98059729EAD3229F882E81C3A";
//
//   		String Single_Request = MerchantId + "|" + OperatingMode + "|" + MerchantCountry + "|" + MerchantCurrency + "|" + TotalDueAmount + "|" + Otherdetail + "|" + SuccessURL + "|" + FailURL + "|" + AggregatorId + "|" + MerchantOrderNo 
//   				+ "|" + MerchantCustomerID + "|" + Paymode + "|" + Accesmedium + "|" + TransactionSource;
//
//        
//        SecretKeySpec key=    AES256Bit.readKeyBytes(key_Array);
//        
//        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
//
//        
//        
//        String s1 =AES256Bit.encrypt(Single_Request, key);
//        System.out.println("encrypted String: "+s1);
//        
//        
//        String Single_Paramresponse = AES256Bit.encrypt(Single_Request, key);
//        
//        String s2 = AES256Bit.decrypt(s1, key);
//        System.out.println("Decrypted String: "+s2);
//        
//        s1="AvVBezx4pAW1zmfTANK0ixW/eBUORNVhMYXFY19ZUgvIjhvuYF29TvA6oWuALQDyiW0uKSkbEypToT7J8aSO98YORTodyxZ7/IecDx2HRIRKUVv/6+vrklsK7noBcDsA3VX1ob4wlW2CU80PrnnaBg==";
//        String s3 = AES256Bit.decrypt(s1, key);
//        System.out.println("Decrypted String: "+s2);
//	 
// }
}

