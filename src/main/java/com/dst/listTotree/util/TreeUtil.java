package com.dst.listTotree.util;

import com.dst.listTotree.model.Tree;
import com.dst.listTotree.model.Model;

import java.util.*;

/**
 * list转tree工具类
 *
 * @denny
 */
public class TreeUtil {


    /**
     * 获取业务类数据
     *
     * @param list
     * @return
     */
    public static List<Model> getTree(List<? extends Tree> list) {

        List<Tree> array = new ArrayList<>(list);
        List<Model> modelList = new ArrayList<>();
        for (Tree tree : array) {
            Model treeModel = new Model();
            treeModel.setId(tree.getItemId());
            treeModel.setPid(tree.getParentId());
            treeModel.setName(tree.getName());

            modelList.add(treeModel);
        }
        return getData(modelList);


    }

    /**
     * 寻找顶级父节点
     *
     * @param modelList
     * @return
     */
    public static List<Model> getData(List<Model> modelList) {


        List<Model> list = new ArrayList<>();


        if (modelList != null) {


            for (Model treeModel : modelList) {
                if (treeModel.getPid() == null || "".equals(treeModel.getPid()) || "0".equals(treeModel.getPid())|| "-1".equals(treeModel.getPid())) {


                    String itemid = treeModel.getId();


                    treeModel.setNodes(addList(modelList, itemid));


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
     *
     * @param array
     * @param itemid
     * @return
     */
    private static List<Model> addList(List<Model> array, String itemid) {
        List<Model> list = new ArrayList<>();

        for (Model model : array) {
            if (itemid.equals(model.getPid())) {

                String cid = model.getId();

                if (addList(array, cid).size() != 0) {
                    model.setNodes(addList(array, cid));
                }
                list.add(model);

            }
        }


        return list;
    }


}
