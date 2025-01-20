package com.beckytech.milkihealthscience;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.beckytech.milkihealthscience.databinding.ActivityMainBinding;
import com.beckytech.milkihealthscience.fragments.AboutusFragment;
import com.beckytech.milkihealthscience.fragments.BlogFragment;
import com.beckytech.milkihealthscience.fragments.ContactFragment;
import com.beckytech.milkihealthscience.fragments.HomeFragment;
import com.beckytech.milkihealthscience.fragments.ServicesFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            }
            if (item.getItemId() == R.id.services)
                replaceFragment(new ServicesFragment());

            if (item.getItemId() == R.id.blog)
                replaceFragment(new BlogFragment());

            if (item.getItemId() == R.id.contact_us)
                replaceFragment(new ContactFragment());

            if (item.getItemId() == R.id.about_us)
                replaceFragment(new AboutusFragment());

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}