package lucky.luckytime.RipPages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lucky.luckytime.R;
import lucky.luckytime.Utils.Sections_Pager_Adapter;

public class RipFragment extends Fragment {

    private Context mContext;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.center_rip, container, false);
        mContext = getActivity();
        setupViewPager(view);
        return view;
    }

    private void setupViewPager(View view){
        Sections_Pager_Adapter adapter = new Sections_Pager_Adapter(getChildFragmentManager());
        adapter.addFragment(new PoolFragment());
        adapter.addFragment(new StoreFragment());
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.rip_container);
        viewPager.clearOnPageChangeListeners();
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.rip_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText(mContext.getString(R.string.fragmenttahmin));
        tabLayout.getTabAt(1).setText(mContext.getString(R.string.fragmentmagaza));
    }

}
