package com.meizu.flyme.calendar.subcription_new.recommend.basecard;

import static java.util.Objects.requireNonNull;

public class TypeItemFactory {

    private final String extra;


    private TypeItemFactory(String extra) {
        this.extra = extra;
    }


    public TypeItem newItem(ItemContent content) {
        return new TypeItem(content, extra);
    }


    public static class Builder {

        private String extra;


        /* optional */
        public Builder setExtra(String extra) {
            this.extra = requireNonNull(extra);
            return this;
        }


        public TypeItemFactory build() {
            return new TypeItemFactory(extra);
        }
    }
}
