package com.jgk.listToTree;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class TreeUtils<T> {
    List<T> dataList;
    TreeNode<T> root;

    public TreeUtils(List dataList) {
        this.dataList = dataList;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public void setRoot(TreeNode<T> root) {
        this.root = root;
    }

    public void createTree(String parentIdProperty, String idProperty) throws NoSuchFieldException, IllegalAccessException {
        root = new TreeNode<>();
        List<TreeNode> nodeList = new ArrayList<>();
        nodeList.add(root);
        for (T t : dataList) {
            TreeNode<T> node = new TreeNode<>(t);
            nodeList.add(node);
        }

        for (TreeNode node : nodeList) {
            List<TreeNode> nodeChilds = new ArrayList<>();
            for (TreeNode temp : nodeList) {
                //排除根节点
                if(temp.getTreeObject() == null){
                    continue;
                }
                //获得temp父节点
                Field fpid = temp.getTreeObject().getClass().getDeclaredField(parentIdProperty);
                fpid.setAccessible(true);
                String pid = (String) fpid.get(temp.getTreeObject());

                //node根节点，
                if (node.getTreeObject() == null ) {

                    //如果上级菜单为空字符串，0，-1,
                    //temp 没有父节点 即为一级菜单
                    if ( StringUtils.isEmpty(pid) || "0".equals(pid)) {
                        nodeChilds.add(temp);
                    }
                    //不是根节点
                } else {
                    //获得node节点id
                    //id判断为空
                    Field fid = node.getTreeObject().getClass().getDeclaredField(idProperty);
                    fid.setAccessible(true);
                    String id = fid.get(node.getTreeObject()) + "";

                    //node节点id与temp父节点id相同
                    if (StringUtils.isNotEmpty(pid) && pid.equals(id)){
                        nodeChilds.add(temp);
                    }
                }
            }
            node.setChildList(nodeChilds);
        }
    }

    public void TreeToJson(TreeNode root, JSONArray jsonArray) {
        if (root.getTreeObject() != null) {
            String jsonString = JSON.toJSONString(root.getTreeObject());
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            jsonObject.put("childList", new JSONArray());
            jsonArray.add(jsonObject);
            System.out.println(root.getTreeObject());
            if (root.getChildList() != null) {
                for (Object node : root.getChildList()) {
                    TreeToJson((TreeNode) node, jsonObject.getJSONArray("childList"));
                }
            }
        } else {
            for (Object node : root.getChildList()) {
                TreeToJson((TreeNode) node, jsonArray);
            }
        }
    }

}
