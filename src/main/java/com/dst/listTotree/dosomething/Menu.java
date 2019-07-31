package com.dst.listTotree.dosomething;

import com.dst.listTotree.model.Tree;


/**
 * 该类为业务类，需要实现tree接口
 * @denny
 */
public class Menu implements Tree {


    private String id;
    private String textContent;
    private String pid;
    private String url;
    private String checked;

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Menu(String id, String pid, String textContent, String url) {
        this.id = id;
        this.textContent = textContent;
        this.pid = pid;
        this.url=url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public String getItemId() {
        return this.id;
    }

    @Override
    public String getParentId() {
        return this.pid;
    }

    @Override
    public String getName() {
        return getTextContent();
    }

    @Override
    public String getCheckedd() {
        return getChecked();
    }


}
