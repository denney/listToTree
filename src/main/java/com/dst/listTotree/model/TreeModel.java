package com.dst.listTotree.model;


import com.dst.listTotree.main.Tree;

import java.util.List;

public class TreeModel {


    private List<TreeModel> nodes;
    private Tree detail;

    public Tree getDetail() {
        return detail;
    }

    public void setDetail(Tree detail) {
        this.detail = detail;
    }

    public TreeModel() {
    }

    public List<TreeModel> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeModel> nodes) {
        this.nodes = nodes;
    }


}
