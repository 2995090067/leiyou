package math.test;
/**
 * synchronized 自動鎖
 */
public class MathTest {
    public static void main(String[] args) {
        double a=-1.3;
        double b=-1.5;
        double c=1.3;
        double d=1.5;
        double e=-1.7;
        System.out.println("a------->"+Math.round(a));
        System.out.println("b------->"+Math.round(b));
        System.out.println("c------->"+Math.round(c));
        System.out.println("d------->"+Math.round(d));
        System.out.println("d------->"+Math.round(e));
        System.out.println("八大数据类型。byte，char,short,int,long,float,double,boolean");
        StringBuilder sub=new StringBuilder();
        sub.append("abcdefghjklmn");
        System.out.println("通过stringbuilder或stringbuffer的resverse方法来进行字符串反转"+sub.reverse());
        StringBuffer strbff=new StringBuffer();
        strbff.append("123456789");
        System.out.println("通过stringbuilder或stringbuffer的resverse方法来进行字符串反转数字类型"+strbff.reverse());
        System.out.println("stringbuffer的charat方法"+strbff.charAt(7));
        String url="张三";
        String url2="张三";
        String url3="张思";
        System.out.println(url.equals(url2));
        System.out.println(url2.equals(url3));
//        final String str="555";
//        str="4343";
        System.out.println(url3.getBytes());
        String trims2=" string的常用方法trim()去除字符串两端的空格 ";
        System.out.println(trims2);
        System.out.println(trims2.trim());
        String trims7="yfhgdw/fghjudyh/fyiuf/besfis";
        String [] num=trims7.split("/");
        System.out.println(num.length);
        System.out.println(trims2.toUpperCase()+"全部转换为大写");
    }
}
