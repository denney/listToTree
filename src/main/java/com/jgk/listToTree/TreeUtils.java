package com.jgk.listToTree;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class TreeUtils {


    /**
     * @param parentIdProperty 业务list 中的对象的pId属性
     * @param idProperty       业务list 中的对象的id属性
     * @param originList       业务list     包含父子级关系
     * @param <T>              业务对象
     * @return
     */
    public static <T> JSONArray listToTreeJsonNew(String parentIdProperty, String idProperty, List<T> originList) {
        JSONObject object = new JSONObject();
        object.put("nodes", new JSONArray());

        try {
            for (T node : originList) {
                String nodeJson = JSONObject.toJSONString(node);
                JSONObject jsonObject = JSONObject.parseObject(nodeJson);
                JSONArray nodes = new JSONArray();
                for (T temp : originList) {
                    //获得temp父节点
                    String tempJson = JSONObject.toJSONString(temp);
                    JSONObject tempJsonObject = JSONObject.parseObject(tempJson);

                    Field fpid = null;
                    fpid = temp.getClass().getDeclaredField(parentIdProperty);
                    fpid.setAccessible(true);
                    String pid;
                    if ("int".equals(fpid.getType().getName())) {
                        pid = fpid.get(temp) + "";
                    } else {
                        pid = (String) fpid.get(temp);
                    }
                    if (StringUtils.isEmpty(pid) || "0".equals(pid) || "-1".equals(pid)) {
                        object.getJSONArray("nodes").add(jsonObject);
                    } else {
                        //获得node节点id
                        //id判断为空
                        Field fid = node.getClass().getDeclaredField(idProperty);
                        fid.setAccessible(true);
                        String id = fid.get(node) + "";
                        if (StringUtils.isNotEmpty(pid) && id.equals(pid)) {
                            nodes.add(tempJsonObject);
                        }
                    }
                }
                jsonObject.put("nodes", nodes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object.getJSONArray("nodes");
    }


    /**
     * @param parentIdProperty 业务list 中的对象的pId属性
     * @param idProperty       业务list 中的对象的id属性
     * @param originList       业务list     包含父子级关系
     * @param <T>              业务对象
     * @return
     */
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
                    String pid = getValueByProperty(parentIdProperty, temp);

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

                        String id = getValueByProperty(idProperty, node);
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

    private static String getValueByProperty(String parentIdProperty, Node temp) throws NoSuchFieldException, IllegalAccessException {
        Field fpid = temp.getTreeObj().getClass().getDeclaredField(parentIdProperty);
        fpid.setAccessible(true);
        String pid;
        if ("int".equals(fpid.getType().getName())) {
            pid = fpid.get(temp.getTreeObj()) + "";
        } else {
            pid = (String) fpid.get(temp.getTreeObj());
        }
        return pid;
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
