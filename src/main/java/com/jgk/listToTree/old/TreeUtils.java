package com.jgk.listToTree.old;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jgk.listToTree.Node;
import com.jgk.listToTree.TreeDepthFunc;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class TreeUtils {


    /**
     * @param parentIdProperty 业务list 中的对象的pId属性
     * @param idProperty       业务list 中的对象的id属性
     *                         //     * @param originList       业务list     包含父子级关系
     * @param <T>              业务对象
     * @return
     */

    public static <T> JSONArray listToTreeJsonV1(String parentIdProperty, String idProperty, List<T> nodeList) {
        return getJsonTree(parentIdProperty, idProperty, nodeList, null);
    }

    public static <T> JSONArray getJsonTree(String parentIdProperty, String idProperty, List<T> nodeList, String pid) {
        JSONArray jsonArray = new JSONArray();
        for (T t : nodeList) {
            String realPid = getValueByProperty(parentIdProperty, t);
            //是否是父级节点，是的话递归去看孩子，不是返回此时的jsonArray
            if (StringUtils.isEmpty(realPid) && StringUtils.isEmpty(pid) || StringUtils.isNotEmpty(realPid) && realPid.equals(pid)) {
                String id = getValueByProperty(idProperty, t);
                JSONArray childArray = getJsonTree(parentIdProperty, idProperty, nodeList, id);
                JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(t));
                object.put("nodes", childArray);
                jsonArray.add(object);
            }
        }
        return jsonArray;
    }


    //根节点为空的树
    public static <T> Node<T> listToTree(String parentIdProperty, String idProperty, List<T> originList) {
        List<Node> nodeList = new ArrayList<>();
        //业务集合转化为模型集合
        for (T t : originList) {
            Node<T> node = new Node();
            node.setTreeObj(t);
            nodeList.add(node);
        }
        Node root = new Node();
        root.setNodeList(getTree(parentIdProperty, idProperty, nodeList, null));
        return root;
    }

    //递归得到树(多棵树)
    public static List<Node> getTree(String parentIdProperty, String idProperty, List<Node> nodeList, String pid) {
        List<Node> list = new ArrayList<>();
        for (Node node : nodeList) {
            String realPid = getValueByProperty(parentIdProperty, node.getTreeObj());
            if (StringUtils.isEmpty(realPid) && StringUtils.isEmpty(pid) || StringUtils.isNotEmpty(realPid) && realPid.equals(pid)) {
                String id = getValueByProperty(idProperty, node.getTreeObj());
                List<Node> nodes = getTree(parentIdProperty, idProperty, nodeList, id);
                node.setNodeList(nodes);
                list.add(node);
            }
        }
        return list;
    }



    public  static JSONArray treeToJson2(Node root){
        JSONArray jsonArray = new JSONArray();
        for(Object node:root.getNodeList()){
            jsonArray.add(getJson((Node) node));
        }
        return jsonArray;
    }
    public static JSONObject getJson(Node node) {
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(node.getTreeObj()));
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("childList", jsonArray);
        if (node.getNodeList() != null) {
            for (Object n : node.getNodeList()) {
                JSONObject object = getJson((Node) n);
                jsonArray.add(object);
            }
        }
        return jsonObject;
    }

    public  static <T> JSONArray treeToJson3(List<Node<T>> list){
        JSONArray jsonArray = new JSONArray();
        for (Node<T> n: list) {
            JSONObject object = JSONObject.parseObject(JSON.toJSONString(n.getTreeObj()));
            object.put("nodes", jsonArray);
            object.put("nodes",treeToJson3(n.getNodeList()));
            jsonArray.add(object);
        }
        return jsonArray;
    }


    public static <T> JSONArray listToTreeJson(String parentIdProperty, String idProperty, List<T> originList) {
        Node<T> root = new Node<>();
        JSONArray json = new JSONArray();
        try {
            List<Node> nodeList = new ArrayList<>();
            //添加空集合
            nodeList.add(root);
//            业务集合转化为模型集合
            for (T t : originList) {
                Node<T> node = new Node();
                node.setTreeObj(t);
                nodeList.add(node);
            }
            //遍历模型集合
            for (Node node : nodeList) {

                List<Node> nodeChilds = new ArrayList<>();

                for (Node temp : nodeList) {
                    //排除根节点
                    if (temp.getTreeObj() == null) {
                        continue;
                    }
                    //获得temp父节点
                    String pid = getValueByProperty(parentIdProperty, temp.getTreeObj());

//                    pid可能为null     空字符串     0   -1    或者写成通用的

                    //node根节点，
                    if (node.getTreeObj() == null) {
                        //如果上级菜单为空字符串，0，-1,
                        //temp 没有父节点 即为一级菜单
                        if (StringUtils.isEmpty(pid) || "0".equals(pid) || "-1".equals(pid)) {
                            nodeChilds.add(temp);
                        }
                        //不是根节点
                    } else {
                        //获得node节点id
                        //id判断为空

                        String id = getValueByProperty(idProperty, node.getTreeObj());
                        //node节点id与temp父节点id相同
                        if (StringUtils.isNotEmpty(pid) && pid.equals(id)) {
                            nodeChilds.add(temp);
                        }
                    }
                }
                node.setNodeList(nodeChilds);
            }
            treeToJson(root, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
    public static void treeToJson(Node root, JSONArray jsonArray) {
        if (root.getTreeObj() != null) {

            String jsonString = JSON.toJSONString(root.getTreeObj());
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            jsonObject.put("childList", new JSONArray());
            jsonArray.add(jsonObject);

            if (root.getNodeList() != null) {
                for (Object node : root.getNodeList()) {
                    treeToJson((Node) node, jsonObject.getJSONArray("childList"));
                }
            }
        } else {
            for (Object node : root.getNodeList()) {
                treeToJson((Node) node, jsonArray);
            }
        }
    }

    private static <T> String getValueByProperty(String parentIdProperty, T node) {
        Field field = null;
        try {
            field = node.getClass().getDeclaredField(parentIdProperty);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        field.setAccessible(true);
        String id = null;
        try {
            if ("int".equals(field.getType().getName())) {
                id = field.get(node) + "";
            } else {
                id = (String) field.get(node);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return id;
    }


    public void DepthOrder(TreeDepthFunc depthFunc, Node root, String parentIdProperty) throws NoSuchFieldException, IllegalAccessException {
        if (root.getTreeObj() != null) {
            Field fpid = null;

            fpid = root.getTreeObj().getClass().getDeclaredField(parentIdProperty);
            fpid.setAccessible(true);
            String pid = null;
            pid = fpid.get(root.getTreeObj()) + "";

            depthFunc.run(pid);
            System.out.println(root.getTreeObj());
            if (root.getNodeList() != null) {
                for (Object node : root.getNodeList()) {
                    DepthOrder(depthFunc, (Node) node, parentIdProperty);
                }
            }
        } else {
            for (Object node : root.getNodeList()) {
                DepthOrder(depthFunc, (Node) node, parentIdProperty);
            }
        }
    }
}
