package com.example.account.Entity;

import java.io.Serializable;

public class ItemCrash implements Serializable {
    public float crash_in;
    public float crash_out;
    public float crash_all;

    public float getCrash_all() {
        return crash_all;
    }

    public float getCrash_in() {
        return crash_in;
    }

    public float getCrash_out() {
        return crash_out;
    }

    public void setCrash_all(float crash_all) {
        this.crash_all = crash_all;
    }

    public void setCrash_in(float crash_in) {
        this.crash_in = crash_in;
    }

    public void setCrash_out(float crash_out) {
        this.crash_out = crash_out;
    }
}
