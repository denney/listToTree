package com.dst.listTotree.util;

import com.dst.listTotree.model.Tree;
import com.dst.listTotree.model.Model;
import net.sf.json.JSONArray;

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
            treeModel.setState(tree.getCheckedd());
            modelList.add(treeModel);
        }
        return getData(modelList);


    }

    public static List<Model> getTreeState(List<? extends Tree> list, List<? extends Tree> liststate) {


        Set set = new HashSet();
        //根据最末端id，查询该末端id对应的父及id，并设置为true
        List<Tree> treeArraystateList = new ArrayList<>(liststate);
        for (Tree treestate : treeArraystateList) {
//            查询出父亲级id 并保存在set集合中
            if (treestate.getParentId() == null || "".equals(treestate.getParentId()) || "0".equals(treestate.getParentId()) || "-1".equals(treestate.getParentId())) {
                set.add(treestate.getItemId());
            } else {
                set.add(treestate.getItemId());
                set = getSet(list, treestate, set);
            }


        }




        List<Tree> array = new ArrayList<>(list);
        List<Model> modelList = new ArrayList<>();
        for (Tree tree : array) {


            Model treeModel = new Model();


            treeModel.setId(tree.getItemId());
            treeModel.setPid(tree.getParentId());
            treeModel.setName(tree.getName());
            if (set.contains(tree.getItemId())) {
                treeModel.setState("true");
            }
            modelList.add(treeModel);
        }



        return getData(modelList);


    }

    private static Set getSet(List<? extends Tree> list, Tree treestate, Set set) {


//        根据id查找父级，
//        根据菜单id 找到父级id
        for (Tree tree : list) {

//                将末端节点的父级节点找到并存放在set集合里
            if (treestate.getParentId().equals(tree.getItemId())) {
                set.add(tree.getItemId());

                if (tree.getParentId() == null || "".equals(tree.getParentId()) || "0".equals(tree.getParentId()) || "-1".equals(tree.getParentId())) {
                    continue;
                } else {
                    set = getSet(list, tree, set);
                }
            }


        }


        return set;
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
                if (treeModel.getPid() == null || "".equals(treeModel.getPid()) || "0".equals(treeModel.getPid()) || "-1".equals(treeModel.getPid())) {


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
