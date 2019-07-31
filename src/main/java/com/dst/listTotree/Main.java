package com.dst.listTotree;

import net.sf.json.JSONArray;
import com.dst.listTotree.dosomething.Menu;
import com.dst.listTotree.util.TreeUtil;

import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {

        List list = new ArrayList();
        List list1 = new ArrayList();
        Menu menu = new Menu("1", null, "name1", "url1");
        list.add(menu);

        menu = new Menu("2", null, "name2", "url2");
        list.add(menu);

        menu = new Menu("3", "1", "name3", "url3");
        list.add(menu);


        menu = new Menu("4", "1", "name4", "url4");
        list.add(menu);
        menu = new Menu("11", "4", "name4", "url4");
        list.add(menu);

        menu = new Menu("12", "11", "name4", "url4");
        list.add(menu);

        list1.add(menu);
        menu = new Menu("5", "0", "name4", "url4");
        list.add(menu);

        menu = new Menu("6", "-1", "name4", "url4");
        list.add(menu);
        menu = new Menu("7", "0", "name4", "url4");
        list.add(menu);




//        System.out.println(JSONArray.fromObject(TreeUtil.getTree(list)));
        System.out.println(JSONArray.fromObject(TreeUtil.getTreeState(list,list1)));




    }
}
