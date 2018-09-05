package test1;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Main_test {


    public static void main(String[] args) {

        Map map=new HashMap<>();
        map.put("appKey","97331234452c422097fa4195e117df54");
        map.put("appSecret","7aefdd14d498deeb3c03fe4967edb041");


        String res= null;

        try {
            res = HttpClientUtil.httpPostRequest("https://open.ys7.com/api/lapp/token/get",map);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println(res);
    }
}
