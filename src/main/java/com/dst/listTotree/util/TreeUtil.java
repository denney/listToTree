package com.dst.listTotree.util;


import com.dst.listTotree.model.Tree;
import com.dst.listTotree.model.Model;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
     * @param originList
     * @return
     */
    public static JSONArray getTreeBy(List<? extends Tree> originList) {




        List<Model> modelList = getModels(originList);


        List<Model> list1 = getData(modelList,originList);


        JSONArray jsonArray = getBeautifulTreeJsonBy(list1);


        return jsonArray;


    }

    public static JSONArray getBeautifulTreeJsonBy(List<Model> list1) {
        JSONArray jsonArray = new JSONArray();


        for (Model model : list1) {

            JSONObject object = JSONObject.fromObject(model.getDetail());
            pushChilds(model, object);

            jsonArray.add(object);
        }
        return jsonArray;
    }

    private static boolean isNoPid(List<? extends Tree> originList, String pid) {
        List<Tree> trees = new ArrayList<>(originList);
        return isPid(trees,pid);
    }

    private static boolean isPid( List<Tree> trees,String pid) {
        Set<String> ids = new HashSet();
        for (Tree tree : trees) {
            ids.add(tree.getItemId());
        }
        if (!ids.contains(pid)) {
            return true;
        }


        return false;
    }

    private static void pushChilds(Model model, JSONObject object) {
        JSONArray array = new JSONArray();
        for (Model m : model.getNodes()) {


            JSONObject o = JSONObject.fromObject(m.getDetail());

            pushChilds(m, o);

            array.add(o);
        }
        object.put("child", array);
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
            treeModel.setDetail(tree);

            modelList.add(treeModel);
        }
        return modelList;
    }





    public static Set getSet(List<? extends Tree> list, List<? extends Tree> statesList) {
        Set set = new HashSet();
        //根据最末端id，查询该末端id对应的父及id，并设置为true
        List<Tree> stateList = new ArrayList<>(statesList);
        for (Tree state : stateList) {
//            查询出父亲级id 并保存在set集合中

            //如果状态项的父及为根节点（root）直接将此项放入set集合中
            if (isNoPid(list,state.getParentId())) {
                set.add(state.getItemId());
            } else {
                //否则，先将状态项放入set,
                set.add(state.getItemId());

                //然后求其父节点项的父节点项
                set = getSet(list, state, set);
            }


        }
        return set;
    }


    /**
     * 迭代遍历获取tree结构中最末端节点包含设备的 tree关联项
     *
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

                if (isNoPid(list,tree.getParentId())) {
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
     * @param originList
     * @return
     */
    public static List<Model> getData(List<Model> modelList, List<? extends Tree> originList) {


        List<Model> list = new ArrayList<>();


        if (modelList != null) {


            for (Model model : modelList) {

//                判断是否为根节点(root)
                if (isNoPid(originList,model.getDetail().getParentId())) {

                    //设置根节点(root)下的子节点集合
                    model.setNodes(addList(modelList, model.getDetail().getItemId()));

                    //将不同根节点(root)的tree结构放在list中
                    list.add(model);
                }

            }
        } else {
            list = null;
        }
        return list;
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
            if (id.equals(model.getDetail().getParentId())) {

//                根据A的id寻找A的下级节点
                model.setNodes(addList(modelList, model.getDetail().getItemId()));

//                将A添加到子节点集合中
                list.add(model);

            }
        }


        return list;
    }


}
