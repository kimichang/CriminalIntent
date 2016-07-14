package com.newgeniuser.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment; //key
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CrimeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CrimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrimeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CRIME_ID="crime_id";
    private static final String DIALOG_DATE="DialogDate";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_DATE=0;
    private static final int REQUEST_CONTACT=1;
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private Button mReportButton;
    private Button mSuspectButton;
    private ImageView mPhotoView;
    private ImageButton mPhotoButton;
    private File mPhotoFile;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args=new Bundle();
        args.putSerializable(ARG_CRIME_ID,crimeId);
        CrimeFragment fragment=new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CrimeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrimeFragment newInstance(String param1, String param2) {
        CrimeFragment fragment = new CrimeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//        mCrime=new Crime();
//        UUID crimeId=(UUID)getActivity().getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
        UUID crimeId=(UUID)getArguments().getSerializable(ARG_CRIME_ID);
        mCrime=CrimeLab.get(getActivity()).getCrime(crimeId);
        mPhotoFile=CrimeLab.get(getActivity()).getPhotoFile(mCrime);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_crime, container, false);
 //       mTitleField=(EditText)v.findViewById(R.id.);
        mTitleField=(EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getmTitle());
        mTitleField.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start ,int countn,int after){
                Log.i("Text","before invoke");
            }
            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){
                mCrime.setmTitle(s.toString());
                Log.i("Text",s.toString());
            }
            @Override
            public void afterTextChanged(Editable s){
                Log.i("Text","changed");
            }
        });
        mDateButton =(Button)v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager manager=getFragmentManager();
                DatePickerFragment dialog=DatePickerFragment.newInstance(mCrime.getmDate());
                dialog.setTargetFragment(CrimeFragment.this,REQUEST_DATE);
                dialog.show(manager,DIALOG_DATE);

            }
        });
//        mDateButton.setEnabled(false);
        mSolvedCheckBox=(CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.ismSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
           @Override
            public void onCheckedChanged(CompoundButton buttonView ,boolean isChecked){
               mCrime.setmSolved(isChecked);
           }

        });
        mReportButton=(Button)v.findViewById(R.id.crime_report);
        mReportButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,getCrimeReport());
                i.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.crime_report_subject));
                i=Intent.createChooser(i,getString(R.string.send_report));
                startActivity(i);
            }
        });
        final Intent pickContact=new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        mSuspectButton=(Button)v.findViewById(R.id.crime_suspect);
        mSuspectButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivityForResult(pickContact,REQUEST_CONTACT);
            }
        });
        if(mCrime.getSuspect()!=null){
            mSuspectButton.setText(mCrime.getSuspect());
        }

        mPhotoButton=(ImageButton) v.findViewById(R.id.crime_camera);
        mPhotoView=(ImageView) v.findViewById(R.id.crime_photo);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onPause(){
        super.onPause();
        CrimeLab.get(getActivity()).updateCrime(mCrime);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void returnResult(){
        getActivity().setResult(Activity.RESULT_OK,null);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode!=Activity.RESULT_OK){
            return;
        }
        if(requestCode==REQUEST_DATE){
            Date date=(Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setmDate(date);
            updateDate();
        }else if(requestCode==REQUEST_CONTACT && data!=null){
            Uri contactUri=data.getData();
            String[] queryFields=new String[]{
              ContactsContract.Contacts.DISPLAY_NAME
            };
            Cursor c=getActivity().getContentResolver().query(contactUri,queryFields,null,null,null);
            try{
                c.moveToFirst();
                String suspect=c.getString(0);
                mCrime.setSuspect(suspect);
                mSuspectButton.setText(suspect);
            }finally{
                c.close();
            }
        }
    }

    private void updateDate() {
        mDateButton.setText(mCrime.getmDate().toString());
    }
    private String getCrimeReport(){
        String solvedString=null;
        if(mCrime.ismSolved()){
            solvedString=getString(R.string.crime_report_solved);
        }else{
            solvedString=getString(R.string.crime_report_unsolved);
        }
        String dateFormat="EEE,MMM dd";
        String dateString= DateFormat.format(dateFormat,mCrime.getmDate()).toString();
        String suspect=mCrime.getSuspect();
        if(suspect==null){
            suspect=getString(R.string.crime_report_no_suspect);
        }else{
            suspect=getString(R.string.crime_report_suspect,suspect);
        }
        String report=getString(R.string.crime_report,mCrime.getmTitle(),dateString,solvedString,suspect);
        return report;
    }
}
