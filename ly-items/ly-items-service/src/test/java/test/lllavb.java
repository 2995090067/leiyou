package test;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class lllavb {
    public static void main(String[] args) {
//        funnable fun=new funnable();
//        fun.run();
//        System.out.println("我是渣渣劉");
//        String a="abc";
//        System.out.println(a.indexOf('b'));
//        System.out.println(a.indexOf(a));
//        System.out.println(a.charAt(2));
//        System.out.println(a.replace('a','2'));
//        System.out.println(a.replace('b','a'));
//        person per=new person();
//        Class pe=person.class;

//        递归算法 A3 B3 C3 不连号
        String []chars={"A","A","A","B","B","B","C","C","C"};
        int CharLengths=chars.length;
        System.out.println(CharLengths);
        int num=StringLength(chars,CharLengths);
        System.out.println("几个不一样？"+num);

    }
    private  static int StringLength(String [] chars,int CharLengths) {
        int num = 0;
        List<String>strings=new ArrayList<>();
        for (int i=0;i<CharLengths;i++){
            strings.add(chars[i]);
        }
        List<String>map;
        for (String s:strings) {
            for (int i=0;i<CharLengths;i++){
            if(!s.equals(chars[i+1])){
                strings.remove(s);
            }
         }
        }
         num=strings.size();
     return num;
    }













    private  static int StringLength2(String [] chars,int CharLengths){
        int num=0,repetition=0;
        String start=chars[0];
        String temp="";

        String []temps=new  String [CharLengths];
        for(int i=1;i<CharLengths-1;i++){
            if((start.equals(chars[i]))){
                repetition++;
            }
            if(start.equals(chars[i])){
                chars.toString();
            }
            return repetition+1;
        }
        for (int i=1;i<=CharLengths-1;i++){
            if(!(start.equals(chars[i]))){
                num++;
            }
        }
        return num;
    }
    private static  void RecursionSequence(String [] chars,int CharLengths){
        String start= chars[0],end=chars[5],temp;

    }
//    输入的
    private static  void RecursionSequence(char [] chars,char strat,char end){

    }
    private  static void sbliuping(){
//        2.2
//        申请后系统的响应
//        栈：只要栈的剩余空间大于所申请空间，系统将为程序提供内存，否则将报异常提示栈溢
//        出。
//        堆：首先应该知道操作系统有一个记录空闲内存地址的链表，当系统收到程序的申请时，
//        会遍历该链表，寻找第一个空间大于所申请空间的堆结点，然后将该结点从空闲结点链表
//        中删除，并将该结点的空间分配给程序，另外，对于大多数系统，会在这块内存空间中的
//        首地址处记录本次分配的大小，这样，代码中的delete语句才能正确的释放本内存空间。
//        另外，由于找到的堆结点的大小不一定正好等于申请的大小，系统会自动的将多余的那部
//        分重新放入空闲链表中。
    }
}
