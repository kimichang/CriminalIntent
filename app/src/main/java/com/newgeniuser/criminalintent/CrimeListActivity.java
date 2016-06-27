package com.newgeniuser.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by lenovo on 2016/6/27.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new CrimeListFragment();
    }

}
