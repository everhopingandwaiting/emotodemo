package emoto.winddy.cn.emotodemo.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by winddy on 2015/10/17.
 */
public class ActSafeFragment extends Fragment{
    protected Activity mActivity;
    protected View basicLayout;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
}
