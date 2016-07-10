package com.newgeniuser.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.newgeniuser.criminalintent.database.CrimeBaseHelper;
import com.newgeniuser.criminalintent.database.CrimeCursorWrapper;
import com.newgeniuser.criminalintent.database.CrimeDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by lenovo on 2016/6/27.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
//    private List<Crime> mCrimes;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get (Context context){
        if(sCrimeLab==null){
            sCrimeLab=new CrimeLab(context);
        }
        return sCrimeLab;
    }
    private CrimeLab(Context context){
        mContext=context.getApplicationContext();
        mDatabase=new CrimeBaseHelper(mContext).getWritableDatabase();
//        mCrimes=new ArrayList<>();
/*        for(int i=0;i<100;i++){
            Crime crime=new Crime();
            crime.setmTitle("Crime # "+i);
            crime.setmSolved(i%2==0);
            mCrimes.add(crime);
        }*/
    }
    public List<Crime> getCrimes(){

//        return mCrimes;
        return new ArrayList<>();
    }

    public Crime getCrime(UUID id){
/*        for(Crime crime:mCrimes){
            if(crime.getmId().equals(id)){
                return crime;
            }
        }*/
        return null;
    }
    private static ContentValues getContentValues(Crime crime){
        ContentValues values=new ContentValues();
        values.put(CrimeDbSchema.CrimeTable.Cols.UUID,crime.getmId().toString());
        values.put(CrimeDbSchema.CrimeTable.Cols.TITLE,crime.getmTitle());
        values.put(CrimeDbSchema.CrimeTable.Cols.DATE,crime.getmDate().getTime());
        values.put(CrimeDbSchema.CrimeTable.Cols.SOLVED,crime.ismSolved()?1:0);
        values.put(CrimeDbSchema.CrimeTable.Cols.SUSPECT,crime.getSuspect());
        return values;
    }
    public void addCrime(Crime c){
 //       mCrimes.add(c);
        ContentValues values=getContentValues(c);
        mDatabase.insert(CrimeDbSchema.CrimeTable.NAME,null,values);
    }
    public void updateCrime(Crime crime){
        String uuidString=crime.getmId().toString();
        ContentValues values=getContentValues(crime);
        mDatabase.update(CrimeDbSchema.CrimeTable.NAME,values, CrimeDbSchema.CrimeTable.Cols.UUID+"=?",new String[]{ uuidString });
    }
//    private Cursor queryCrimes(String whereClause,String[] whereArgs){
    private CrimeCursorWrapper queryCrimes(String whereClause,String[] whereArgs){
        Cursor cursor=mDatabase.query(CrimeDbSchema.CrimeTable.NAME,null,whereClause,whereArgs,null,null,null);
//        return cursor;
        return new CrimeCursorWrapper(cursor);
    }
}
