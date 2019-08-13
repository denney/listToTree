package com.dst.listTotree.model;


import java.util.List;

/**
 * tree结构模型，可以通过添加属性进行定制
 *
 * @denny
 */
public class Model {


    private List<Model> nodes;

    private Tree detail;

    public Tree getDetail() {
        return detail;
    }

    public void setDetail(Tree detail) {
        this.detail = detail;
    }

    public Model() {
    }

    public List<Model> getNodes() {
        return nodes;
    }

    public void setNodes(List<Model> nodes) {
        this.nodes = nodes;
    }


}
