package com.dst.listTotree;

import com.dst.listTotree.model.Model;
import net.sf.json.JSONArray;
import com.dst.listTotree.dosomething.Menu;
import com.dst.listTotree.util.TreeUtil;

import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {

        List list = new ArrayList();

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






        System.out.println(TreeUtil.getTree(list));




    }
}
