package com.example.k_pt_watch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.k_pt_watch.databinding.ActivityStartBinding;

public class StartActivity extends Activity {

    private TextView mTextView;
    private ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}