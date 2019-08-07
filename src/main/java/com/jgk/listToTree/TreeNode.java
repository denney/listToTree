package com.jgk.listToTree;

import java.util.List;

public class TreeNode<T> {
    private T treeObject;
    private List<TreeNode> childList;

    public TreeNode() {
    }

    public TreeNode(T value) {
        this.treeObject = value;
    }

    public T getTreeObject() {
        return treeObject;
    }

    public void setTreeObject(T treeObject) {
        this.treeObject = treeObject;
    }

    public List<TreeNode> getChildList() {
        return childList;
    }

    public void setChildList(List<TreeNode> childList) {
        this.childList = childList;
    }



}
