package emoto.winddy.cn.emotodemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import emoto.winddy.cn.emotodemo.R;
import emoto.winddy.cn.emotodemo.fragments.BikeStatusFragment;
import emoto.winddy.cn.emotodemo.fragments.LockCarFragment;
import emoto.winddy.cn.emotodemo.fragments.MainPageFragment;
import emoto.winddy.cn.emotodemo.fragments.ServiceFragment;
import emoto.winddy.cn.emotodemo.fragments.UserFragment;

/**
 * Created by winddy on 2015/10/17.
 */
public class TabActivity extends AppCompatActivity {
    private FragmentTabHost mTabHost;
    private Class<?> mTabFragmentArray[] = {MainPageFragment.class ,
            BikeStatusFragment.class,
            LockCarFragment.class,
            ServiceFragment.class,
            UserFragment.class};
    private String[] mTabViewNamesArray = {"1", "2", "3", "4", "5"};
    private int[] defaultTabRes = {R.drawable.home_selector,
            R.drawable.car_status_selector,
            R.drawable.lock_selector,
            R.drawable.service_selecotr,
            R.drawable.user_selector};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_layout);
        init();
    }

    void init() {
        mTabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.mainContent);

        int count = mTabFragmentArray.length;

        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTabViewNamesArray[i])
                    .setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, mTabFragmentArray[i], null);
        }
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                FragmentManager manager = getSupportFragmentManager();
                Fragment fr = manager.findFragmentByTag(tabId);
                if (fr != null) {
                    FragmentTransaction ft = manager.beginTransaction();
                    ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    ft.commit();
                }
            }
        });
        mTabHost.setCurrentTab(2);
    }

    private View getTabItemView(int index) {
        int[] tabRes = defaultTabRes;
        View view = getLayoutInflater().inflate(R.layout.tab_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.navigationIcon);
        imageView.setImageResource(tabRes[index]);
        return view;
    }
}
