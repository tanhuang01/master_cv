package com.congguangzi.master_cv;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

/**
 * @author congguangzi (congspark@163.com) 2017/10/9.
 */

public class PageFragment extends Fragment {

    private static final String LAYOUT_RES = "layout_res";

    @LayoutRes
    int layoutRes;

    public static PageFragment newInstance(@LayoutRes int layoutRes) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES, layoutRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        ViewStub viewStub = (ViewStub) view.findViewById(R.id.view_stub);
        viewStub.setLayoutResource(layoutRes);
        viewStub.inflate();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            layoutRes = args.getInt(LAYOUT_RES);
        }
    }
}
