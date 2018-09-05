package com.dst.listTotree.util;

import com.dst.listTotree.model.TreeModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.dst.listTotree.main.Tree;

import java.util.*;
import java.util.List;

public class TreeUtil {
    public static JSONArray getTreeData(JSONArray array) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();


        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                if (array.getJSONObject(i).getString("menuParentId").equals("0")) {
                    jsonObject.put("id", array.getJSONObject(i).getString("menuId"));
                    jsonObject.put("text", array.getJSONObject(i).getString("name"));
                    String itemid = array.getJSONObject(i).getString("menuId");
                    jsonObject.put("nodes", add(array, itemid, array.size()));
                    jsonArray.add(jsonObject);
                }

            }
        } else {
            jsonArray = null;
        }
        return jsonArray;
    }


    private static JSONArray add(JSONArray array, String itemid, int length) {


        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < length; i++) {
            if (itemid.equals(array.getJSONObject(i).getString("menuParentId"))) {
                jsonObject.put("id", array.getJSONObject(i).getString("menuId"));
                jsonObject.put("text", array.getJSONObject(i).getString("name"));
                String cid = array.getJSONObject(i).getString("menuId");
                if (add(array, cid, length).size() != 0) {
                    jsonObject.put("nodes", add(array, cid, length));
                }
                jsonArray.add(jsonObject);
                jsonObject.clear();
            }
        }


        return jsonArray;
    }

    public static List<TreeModel> getTree(List<? extends Tree> list) {

        List<Tree> array = new ArrayList<>(list);
        List<TreeModel> treeModelList = new ArrayList<>();
        for (Tree tree : array) {
            TreeModel treeModel = new TreeModel();
            treeModel.setDetail(tree);
            treeModelList.add(treeModel);
        }
        return getData(treeModelList);

    }


    public static List<TreeModel> getData(List<TreeModel> array) {


        List<TreeModel> list = new ArrayList<>();


        if (array != null) {
            Set<String> set = new HashSet<>();
            Set<String> setparentId = new HashSet<>();

            Map<String, Object> mapChild = new HashMap<>();
            for (TreeModel treeMode : array) {

                set.add(treeMode.getDetail().getParentId());
                mapChild.put(treeMode.getDetail().getItemId(), treeMode);
            }

            for (String pid : set) {
                if (mapChild.get(pid) == null) {
                    setparentId.add(pid);
                }
            }


            for (TreeModel treeModel : array) {
                if (setparentId.contains(treeModel.getDetail().getParentId())) {


                    String itemid = treeModel.getDetail().getItemId();


                    treeModel.setNodes(addList(array, itemid));


                    list.add(treeModel);
                }

            }
        } else {
            list = null;
        }
        return list;
    }

    private static List<TreeModel> addList(List<TreeModel> array, String itemid) {
        List<TreeModel> list = new ArrayList<>();

        for (TreeModel treeModel : array) {
            if (itemid.equals(treeModel.getDetail().getParentId())) {

                String cid = treeModel.getDetail().getItemId();

                if (addList(array, cid).size() != 0) {
                    treeModel.setNodes(addList(array, cid));
                }
                list.add(treeModel);

            }
        }


        return list;
    }




}
