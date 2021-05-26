package com.example.k_pt_watch;

import android.util.Log;

public final class Dbg {
    private Dbg (){}

    public static void out (Object msg){
        Log.i ("info", msg.toString ());
    }
}

