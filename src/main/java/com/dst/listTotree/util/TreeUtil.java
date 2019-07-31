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

        List<Model> modelList = getModels(list);
        return getData(modelList);


    }

    /**
     * 将传入的集合转化为接口集合
     *
     * @param list
     * @return
     */
    private static List<Model> getModels(List<? extends Tree> list) {
        //将传入的集合转化为接口集合
        List<Tree> array = new ArrayList<>(list);
        List<Model> modelList = getModelist(array);
        return modelList;
    }


    /**
     * 将接口集合转化为算法使用的对象集合
     * 注意：经常再此处定制一些前端需要的字段
     *
     * @param array
     * @return
     */
    private static List<Model> getModelist(List<Tree> array) {
        //将接口集合转化为算法使用的对象集合
        List<Model> modelList = new ArrayList<>();
        for (Tree tree : array) {
            Model treeModel = new Model();
            treeModel.setId(tree.getItemId());
            treeModel.setPid(tree.getParentId());
            treeModel.setName(tree.getName());
            treeModel.setState(tree.getCheckedd());
            modelList.add(treeModel);
        }
        return modelList;
    }


    /**
     * 获取最末端节点包含设备项的tree 与原生tree 的关系    显示与隐藏包含tree的项和不包含tree的项
     * @param list
     * @param listState
     * @return
     */
    public static List<Model> getTreeState(List<? extends Tree> list, List<? extends Tree> listState) {

        //获取包含设备的 所有tree 项
        Set set = getSet(list, listState);

        //获取所有tree,用true状态表示 含有设备的tree
        List<Model> modelList = getModels(list, set);


        //将list转化为tree
        return getData(modelList);


    }

    private static List<Model> getModels(List<? extends Tree> list, Set set) {
        List<Tree> array = new ArrayList<>(list);
        List<Model> modelList = new ArrayList<>();
        for (Tree tree : array) {


            Model treeModel = new Model();


            treeModel.setId(tree.getItemId());
            treeModel.setPid(tree.getParentId());
            treeModel.setName(tree.getName());


            //这个if是个开关，可以决定给前端返回什么样的tree
            if (set.contains(tree.getItemId())) {
                treeModel.setState("true");
            }
            modelList.add(treeModel);
        }
        return modelList;
    }

    private static Set getSet(List<? extends Tree> list, List<? extends Tree> listState) {
        Set set = new HashSet();
        //根据最末端id，查询该末端id对应的父及id，并设置为true
        List<Tree> treeArraystateList = new ArrayList<>(listState);
        for (Tree treestate : treeArraystateList) {
//            查询出父亲级id 并保存在set集合中
            if (isaBoolean(treestate.getParentId())) {
                set.add(treestate.getItemId());
            } else {
                set.add(treestate.getItemId());
                set = getSet(list, treestate, set);
            }


        }
        return set;
    }


    /**
     * 迭代遍历获取tree结构中最末端节点包含设备的 tree关联项
     * @param list
     * @param treeState
     * @param set
     * @return
     */
    private static Set getSet(List<? extends Tree> list, Tree treeState, Set set) {



        for (Tree tree : list) {

//                将末端节点的父级节点找到并存放在set集合里
            if (treeState.getParentId().equals(tree.getItemId())) {
                set.add(tree.getItemId());

                if (isaBoolean(tree.getParentId())) {
                    continue;
                } else {
                    set = getSet(list, tree, set);
                }
            }


        }


        return set;
    }

    /**
     * 将list转化为tree 外壳
     *
     * @param modelList
     * @return
     */
    public static List<Model> getData(List<Model> modelList) {


        List<Model> list = new ArrayList<>();


        if (modelList != null) {


            for (Model model : modelList) {

//                判断是否为根节点(root)
                if (isaBoolean(model.getPid())) {

                    //设置根节点(root)下的子节点集合
                    model.setNodes(addList(modelList, model.getId()));

                    //将不同根节点(root)的tree结构放在list中
                    list.add(model);
                }

            }
        } else {
            list = null;
        }
        return list;
    }

    private static boolean isaBoolean(String pid) {
        return pid == null || "".equals(pid) || "0".equals(pid) || "-1".equals(pid);
    }


    /**
     * 核心工具类
     * 说明：获取根节点下的子节点集合
     *
     * @param modelList
     * @param id
     * @return
     */
    private static List<Model> addList(List<Model> modelList, String id) {
        List<Model> list = new ArrayList<>();

        for (Model model : modelList) {

            //如果寻找到当前tree结构下的子节点A，将其添加在当前tree结构的子节点集合中
            if (id.equals(model.getPid())) {

//                根据A的id寻找A的下级节点
                    model.setNodes(addList(modelList, model.getId()));

//                将A添加到子节点集合中
                list.add(model);

            }
        }


        return list;
    }


}
