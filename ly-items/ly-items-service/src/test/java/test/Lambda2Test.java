package test;

import java.util.Scanner;

public class Lambda2Test {
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        System.out.println("我们是把小写转化为大写的字符串测试");
        System.out.println("请输入字符串");
        String name= input.next();
        mainx(name);
        System.out.println();
    }

    public static void mainx(String name){
        System.out.println(name.toUpperCase());
    }
    public void lambdaTest(String named){
        System.out.println(named+"你好啊");
    }
}
