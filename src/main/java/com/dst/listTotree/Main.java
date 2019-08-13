package com.dst.listTotree;

import com.dst.listTotree.dosomething.Menu;
import com.dst.listTotree.util.TreeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {


    public static void main(String[] args) {

        List<Menu> list = new ArrayList();
        List list1 = new ArrayList();

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
        list1.add(menu);
        menu = new Menu("11", "9", "name4", "url4");
        list.add(menu);
        menu = new Menu("12", "9", "name4", "url4");
        list.add(menu);
        menu = new Menu("13", "10", "name4", "url4");
        list.add(menu);
        menu = new Menu("14", "10", "name4", "url4");
        list.add(menu);
        list1.add(menu);


//        System.out.println(TreeUtil.getTreeBy(list));
//        System.out.println(TreeUtil.getBeautifulTreeJsonBy(TreeUtil.getTreeBy(list)));

        Set set = TreeUtil.getSet(list, list1);


        for (Menu o : list) {
            if (set.contains(o.getId())) {
                o.setChecked(true + "");
            }
        }

        System.out.println(TreeUtil.getTreeBy(list, ""));
    }
}
