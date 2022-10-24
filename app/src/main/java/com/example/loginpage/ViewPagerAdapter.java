package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.loginpage.ui.main.fragments.CateringFragment;
import com.example.loginpage.ui.main.fragments.RestaurantFragment;
import com.example.loginpage.ui.main.fragments.StreetFoodFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new RestaurantFragment();
            case 1:
                return new CateringFragment();
            case 2:
                return new StreetFoodFragment();
            default:
                return new RestaurantFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 3; // how many fragments/pages are saved
    }
}
