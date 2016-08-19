package com.meizu.flyme.calendar.subcription_new.recommend.basecard;

public interface Savable {

    void init(byte[] data);

    byte[] toBytes();
}
