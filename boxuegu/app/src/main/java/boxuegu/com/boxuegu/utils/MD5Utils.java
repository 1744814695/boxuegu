package boxuegu.com.boxuegu.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    /**
     *  MD5 加密的算法
     */
    public static String md5(String text){

//          MessageDigest digest=null;
//                  try {
//                      digest=MessageDigest.getInstance("md5");
//                      byte[] result=digest.digest();//该方法的返回值就是加密密文
//                      return result.toString();
//                  }catch (Exception e){
//                      e.printStackTrace();
//                      return "";
//                  }

        //书本P60页代码
        MessageDigest digest=null;
         try{
          digest=MessageDigest.getInstance("md5");
          byte[] result=digest.digest(text.getBytes());
          StringBuffer sb=new StringBuffer();
          for (byte b : result){
              int number = b & 0xff;
              String hex=Integer.toHexString(number);
                        if (hex.length()==1){
                             sb.append("0"+hex);
                     }else {
                            sb.append(hex);
                         }
                     }
                      return sb.toString();
                  }
          catch (NoSuchAlgorithmException e){
                 e.printStackTrace();
                 return "";
         }

    }
}
