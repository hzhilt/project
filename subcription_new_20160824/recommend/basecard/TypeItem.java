package com.meizu.flyme.calendar.subcription_new.recommend.basecard;

public class TypeItem {

    public ItemContent content;
    public String extra;


    public TypeItem() {
    }


    public TypeItem(ItemContent content, String extra) {
        this.extra = extra;
        this.content = content;
    }


    @Override public String toString() {
        return "ItemType {" +
            "content=" + content +
            ", extra='" + extra + '\'' +
            '}';
    }
}
