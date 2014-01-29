package org.rubychina.app.model;

/**
 * Created by mac on 14-1-27.
 */
public enum Category {
    popular("热门讨论"), nodes("节点分类"), coolsite("酷站导航"), active("活跃会员"), mypage("我的主页"), wiki("RCWiki");

    private String mDisplayName;

    Category(String displayName) {
        mDisplayName = displayName;
    }

    public String getDisplayName() {
        return mDisplayName;
    }
}
