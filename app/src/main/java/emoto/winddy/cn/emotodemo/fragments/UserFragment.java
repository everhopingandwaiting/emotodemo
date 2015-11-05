package emoto.winddy.cn.emotodemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import emoto.winddy.cn.emotodemo.R;

/**
 * Created by winddy on 2015/10/17.
 */
public class UserFragment extends ActSafeFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        basicLayout = inflater.inflate(R.layout.user_layout, container, false);
        return basicLayout;
    }
}
