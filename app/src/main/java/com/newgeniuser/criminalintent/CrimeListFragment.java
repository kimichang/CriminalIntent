package com.newgeniuser.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by lenovo on 2016/6/27.
 */
public class CrimeListFragment extends Fragment {
    private CrimeAdapter mAdapter;
    private RecyclerView mCrimeRecyclerView;
    private static final int REQUEST_CRIME=1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_crime_list,container,false);
        mCrimeRecyclerView=(RecyclerView)view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
//        public TextView mTitleTextView;
        private Crime mCrime;
        public CrimeHolder(View view){
            super(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            mTitleTextView=(TextView)view.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView=(TextView)view.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox=(CheckBox)view.findViewById(R.id.list_item_crime_solved_check_box);

        }
        public void bindCrime(Crime crime){
            mCrime=crime;
            mTitleTextView.setText(mCrime.getmTitle());
            mDateTextView.setText(mCrime.getmDate().toString());
            mSolvedCheckBox.setChecked(mCrime.ismSolved());
        }
        @Override
        public void onClick(View v){
//            Toast.makeText(getActivity(),mCrime.getmTitle()+"clicked!",Toast.LENGTH_SHORT).show();
//            Intent intent=new Intent(getActivity(),CrimeActivity.class);
            Intent intent=CrimeActivity.newIntent(getActivity(),mCrime.getmId());
            startActivityForResult(intent,REQUEST_CRIME);
        }
        @Override
        public boolean onLongClick(View v){
            Intent intent=CrimePagerActivity.newIntent(getActivity(),mCrime.getmId());
            startActivity(intent);
            return true;
        }
    }
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        private List<Crime> mCrimes;
        public CrimeAdapter(List<Crime> crimes){
            mCrimes=crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent,int viewType){
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            View view=layoutInflater.inflate(R.layout.list_item_crime,parent,false);
            return new CrimeHolder(view);
        }
        @Override
        public void onBindViewHolder(CrimeHolder holder,int position){
            Crime crime=mCrimes.get(position);
            //holder.mTitleTextView.setText(crime.getmTitle());
            holder.bindCrime(crime);
        }
        @Override
        public int getItemCount(){
            return mCrimes.size();
        }
    }
    public CrimeListFragment(){}

    private void updateUI(){
        CrimeLab crimeLab=CrimeLab.get(getActivity());
        List<Crime> crimes=crimeLab.getCrimes();
        if(mAdapter==null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void  onResume(){
        super.onResume();
        updateUI();
    }
}
