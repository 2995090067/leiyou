package com.zy.upload.service;

import java.util.Scanner;

public class DiGuiTest {
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        System.out.println("请输入一个正整数");
        int x=input.nextInt();
        int y=digui(x);
        System.out.println(y);
    }
    public static int digui(int x){
        if(x==1){
            return x;
        }else{
            return x*digui(x-1);
        }
//        int y=x*digui(x-1);

    }
}

