package cis350.project.favor_app.ui.userFavorHistory;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import cis350.project.favor_app.R;

/*
 * Code adapted from android dev viewpager2 documentation
 * Integrates ViewPager2 with TabLayout to show submitted/undertaken favors
 */

public class UserFavorHistoryActivity extends FragmentActivity {

    private static final int NUM_PAGES = 2;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private String userId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_favors);

        viewPager = findViewById(R.id.user_favors_viewpager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        userId = getIntent().getStringExtra("userId");

        TabLayout tabLayout = findViewById(R.id.user_favors_tabLayout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Submitted");
            } else {
                tab.setText("Undertaken");
            }
        }).attach();
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new UserFavorsFragment(true, userId);
            } else {
                return new UserFavorsFragment(false, userId);
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}

