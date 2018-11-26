package backTrack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个只包含数字的字符串，复原它并返回所有可能的 IP 地址格式。
 *
 * 示例:
 *
 * 输入: "25525511135"
 * 输出: ["255.255.11.135", "255.255.111.35"]
 *
 */
public class Solution93 {
    private List<String> res=new ArrayList<>();
    public List<String> restoreIpAddresses(String s) {
        if (s.length() < 4 || s.length() > 12){
            return res;
        }
        generateIpAddress(s,0,"");
        return res;

    }
    //生成ip地址的函数，s：待解析的字符串，count：当前已经解析的数字次数（ip为4个0-255是数字组成）
    //ip:前一次搜索得到的待完成的ip
    private void generateIpAddress(String s,int count,String ip){
        //当已经解析了3位数字，判断当前的s是否满足作为最后一位数字的条件，若满足则加入结果集合
        if (count==3&&isValid(s)){
            System.out.println("complete ip ");
            res.add(ip+s);
        }
        //每次最多只取三位数
        for (int i = 1; i < Math.min(4,s.length()); i++) {
            String curStr = s.substring(0, i);
            if (isValid(curStr)){
                System.out.println("CurStr:"+curStr+"  ip:"+ip);
                //从s[i...n]中寻找
                generateIpAddress(s.substring(i),count+1,ip+curStr+".");
            }

        }
    }

    //判断这个数字是否符合0-255之间
    private boolean isValid(String s){
        if (s.charAt(0)=='0'){
            return s.equals("0");
        }
        int i = Integer.parseInt(s);
        if (i>=0&&i<=255){
            return true;
        }else {
            return false;
        }
    }

    @Test
    public void test(){
        String s="25525511135";
        List<String> r = restoreIpAddresses(s);
        System.out.println(r);
    }
}