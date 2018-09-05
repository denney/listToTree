package test1.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import test1.main.Tree;
import test1.main.TreeImpl;
import test1.model.TreeModel;

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
            TreeModel treeModel = new TreeModel(tree.getItemId(), tree.getParentId(), tree.getItemName());
            treeModel.setTree(tree);
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

                set.add(treeMode.getParentId());
                mapChild.put(treeMode.getItemId(), treeMode);
            }

            for (String pid : set) {
                if (mapChild.get(pid) == null) {
                    setparentId.add(pid);
                }
            }


            for (TreeModel treeModel : array) {
                if (setparentId.contains(treeModel.getParentId())) {


                    String itemid = treeModel.getItemId();


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
            if (itemid.equals(treeModel.getParentId())) {

                String cid = treeModel.getItemId();

                if (addList(array, cid).size() != 0) {
                    treeModel.setNodes(addList(array, cid));
                }
                list.add(treeModel);

            }
        }


        return list;
    }


//    public static void main(String[] args) {
//
//
////        private String menuId;
////        private String menuParentId;
////        private String name;
//
//        List list = new ArrayList();
//        TreeModel treeModel = new TreeModel("1", "0", "name1");
//        list.add(treeModel);
//
//        treeModel = new TreeModel("2", "0", "name2");
//        list.add(treeModel);
//
//        treeModel = new TreeModel("3", "1", "name3");
//        list.add(treeModel);
//
//        treeModel = new TreeModel("4", "1", "name4");
//        list.add(treeModel);
//
////        System.out.println(getTreeData(JSONArray.fromObject(list)));
//
//        System.out.println(JSONArray.fromObject(getData(list)));
//
//    }


    public static void main(String[] args) {


//        private String menuId;
//        private String menuParentId;
//        private String name;

        List list = new ArrayList();
        TreeImpl tree = new TreeImpl("1", "0", "name1", "url1");
        list.add(tree);

        tree = new TreeImpl("2", "0", "name2", "url2");
        list.add(tree);

        tree = new TreeImpl("3", "1", "name3", "url3");
        list.add(tree);

        tree = new TreeImpl("4", "1", "name4", "url4");
        list.add(tree);


        System.out.println(JSONArray.fromObject(getTree(list)));
//
    }

}
