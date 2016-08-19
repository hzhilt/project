package com.meizu.realm;

import io.realm.RealmObject;

/**
 * Created by huangzhihao on 16-2-19.
 */
public class Note extends RealmObject {

    private String title;
    private String content;
    private String createTime;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
