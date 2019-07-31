package com.dst.listTotree.util;

import com.dst.listTotree.model.Tree;
import com.dst.listTotree.model.TreeModel;

import java.util.*;

/**
 * list转tree工具类
 * @denny
 */
public class TreeUtil {


    /**
     * 获取业务类数据
     * @param list
     * @return
     */
    public static List<TreeModel> getTree(List<? extends Tree> list) {

        List<Tree> array = new ArrayList<>(list);
        List<TreeModel> modelList = new ArrayList<>();
        for (Tree tree : array) {
            TreeModel treeModel = new TreeModel();
            treeModel.setId(tree.getItemId());
            treeModel.setPid(tree.getParentId());
            treeModel.setName(tree.getName());

            modelList.add(treeModel);
        }
        return getData(modelList);


    }

    /**
     * 寻找顶级父节点
     * @param array
     * @return
     */
    public static List<TreeModel> getData(List<TreeModel> array) {


        List<TreeModel> list = new ArrayList<>();


        if (array != null) {
            Set<String> set = new HashSet<>();
            Set<String> setparentId = new HashSet<>();

            Map<String, Object> mapChild = new HashMap<>();
            for (TreeModel treeMode : array) {

                set.add(treeMode.getPid());
                mapChild.put(treeMode.getId(), treeMode);
            }

            for (String pid : set) {
                if (mapChild.get(pid) == null) {
                    setparentId.add(pid);
                }
            }


            for (TreeModel treeModel : array) {
                if (setparentId.contains(treeModel.getPid())) {


                    String itemid = treeModel.getId();


                    treeModel.setNodes(addList(array, itemid));


                    list.add(treeModel);
                }

            }
        } else {
            list = null;
        }
        return list;
    }


    /**
     * 核心工具类
     * @param array
     * @param itemid
     * @return
     */
    private static List<TreeModel> addList(List<TreeModel> array, String itemid) {
        List<TreeModel> list = new ArrayList<>();

        for (TreeModel treeModel : array) {
            if (itemid.equals(treeModel.getPid())) {

                String cid = treeModel.getId();

                if (addList(array, cid).size() != 0) {
                    treeModel.setNodes(addList(array, cid));
                }
                list.add(treeModel);

            }
        }


        return list;
    }


}
