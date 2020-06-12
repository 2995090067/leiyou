package test;

import java.util.Arrays;
import java.util.List;

public   class diguisuanfa {
    public  static  void dd(String []chars){
        int charslength=chars.length;
//        数组转换成集合
        List<Object>temp1= Arrays.asList(chars);
        List<Object>temp2=null;
        for(Object temps:temp1){
            for (int i=1;i<charslength;i++){
                if(temps.equals(chars[i])){

                }
            }
        }
        for(int i=0;i<charslength;i++){


        }
    }
}
