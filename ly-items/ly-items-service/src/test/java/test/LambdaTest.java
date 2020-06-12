package test;

import java.util.function.Function;

public class LambdaTest {
    public static void main(String[] args) {
        //把代码逻辑当做参数进行传递
//        getAge(a->Integer.parseInt(a),"a");
        System.out.println(getAge(a->Integer.parseInt(a),"1223"));
        System.out.println(getString(b->b.toString(),"abc123"));
    }
    public static Integer getAge(Function<String,Integer>function,String args){
        return  function.apply(args);
    }
    public static  String getString(Function<String,String>function,String args){
        return function.apply(args);
    }
}
