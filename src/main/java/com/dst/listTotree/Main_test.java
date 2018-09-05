package com.dst.listTotree;

import net.sf.json.JSONArray;
import com.dst.listTotree.main.TreeImpl;
import com.dst.listTotree.util.TreeUtil;

import java.util.ArrayList;
import java.util.List;

public class Main_test {


    public static void main(String[] args) {

        List list = new ArrayList();
        TreeImpl tree = new TreeImpl("1", "0", "name1", "url1");
        list.add(tree);

        tree = new TreeImpl("2", "0", "name2", "url2");
        list.add(tree);

        tree = new TreeImpl("3", "1", "name3", "url3");
        list.add(tree);

        tree = new TreeImpl("4", "1", "name4", "url4");
        list.add(tree);


        System.out.println(JSONArray.fromObject(TreeUtil.getTree(list)));
    }
}
