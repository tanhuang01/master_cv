package com.congguangzi.master_cv;

import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager pager;

    List<PageModel> pageModels = new ArrayList<>();

    {
        pageModels.add(new PageModel(R.layout.l_01_flip_view, R.string.l_01_flip_page));
        pageModels.add(new PageModel(R.layout.l_02_histogram, R.string.l_02_histogram));
        pageModels.add(new PageModel(R.layout.l_03_leaf_loading, R.string.l_03_leaf_loading));
        pageModels.add(new PageModel(R.layout.l_04_progress_fresher, R.string.l_04_progress_fresher));
        pageModels.add(new PageModel(R.layout.l_05_sin_circle, R.string.l_05_sin_cricle));
        pageModels.add(new PageModel(R.layout.l_06_raw_text_view, R.string.l_06_raw_text));
        pageModels.add(new PageModel(R.layout.l_07_receipt_view, R.string.l_07_receipt_view));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                PageModel pageModel = pageModels.get(position);
                return PageFragment.newInstance(pageModel.layoutRes);
            }

            @Override
            public int getCount() {
                return pageModels.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getString(pageModels.get(position).titleRes);
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);
    }


    private class PageModel {
        @LayoutRes
        int layoutRes;
        @StringRes
        int titleRes;

        public PageModel(int layoutRes, int titleRes) {
            this.layoutRes = layoutRes;
            this.titleRes = titleRes;
        }
    }
}
