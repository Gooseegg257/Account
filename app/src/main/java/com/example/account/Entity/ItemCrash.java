package com.example.account.Entity;

import java.io.Serializable;

public class ItemCrash implements Serializable {
    public double crash_in;
    public double crash_out;
    public double crash_all;

    public double getCrash_all() {
        return crash_all;
    }

    public double getCrash_in() {
        return crash_in;
    }

    public double getCrash_out() {
        return crash_out;
    }

    public void setCrash_all(double crash_all) {
        this.crash_all = crash_all;
    }

    public void setCrash_in(double crash_in) {
        this.crash_in = crash_in;
    }

    public void setCrash_out(double crash_out) {
        this.crash_out = crash_out;
    }
}
