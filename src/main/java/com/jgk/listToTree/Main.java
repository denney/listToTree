package com.jgk.listToTree;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dst.listTotree.dosomething.Menu;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Menu> list = new ArrayList();

        Menu menu = new Menu("1", null, "name1", "url1");
        list.add(menu);

        menu = new Menu("2", "1", "name2", "url2");
        list.add(menu);

        menu = new Menu("3", "1", "name3", "url3");
        list.add(menu);


        menu = new Menu("4", "2", "name4", "url4");
        list.add(menu);
        menu = new Menu("5", "2", "name4", "url4");
        list.add(menu);

        menu = new Menu("6", "3", "name4", "url4");
        list.add(menu);

        menu = new Menu("7", "3", "name4", "url4");
        list.add(menu);

        menu = new Menu("8", null, "name4", "url4");
        list.add(menu);

        menu = new Menu("9", "8", "name4", "url4");
        list.add(menu);

        menu = new Menu("10", "8", "name4", "url4");
        list.add(menu);
        menu = new Menu("11", "9", "name4", "url4");
        list.add(menu);
        menu = new Menu("12", "9", "name4", "url4");
        list.add(menu);
        menu = new Menu("13", "10", "name4", "url4");
        list.add(menu);
        menu = new Menu("14", "10", "name4", "url4");
        list.add(menu);


        Node<Menu> root = TreeUtils.listToTree("pid", "id", list);
        JSONArray jsonArray1 = TreeUtils.treeToJsonArray(root.getNodeList());
        System.out.println(jsonArray1);

        JSONArray jsonArray2 = TreeUtils.listToJsonArray("pid", "id", list);
        System.out.println(jsonArray2);

//        treeUtils.DepthOrder((str)-> System.out.println(str),treeUtils.getRoot(),"pid");

//        treeUtils.DepthOrder(new TreeDepthFunc() {
//            @Override
//            public void run(String str) {
//                System.out.println(str);
//            }
//        },treeUtils.getRoot(),"pid");
    }
}
