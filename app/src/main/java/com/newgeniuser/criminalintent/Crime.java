package com.newgeniuser.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by lenovo on 2016/6/24.
 */
public class Crime {
    private UUID mId;
    private String mTitle;

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    private String mSuspect;

    public boolean ismSolved() {
        return mSolved;
    }

    public void setmSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    private Date mDate;
    private boolean mSolved;

    public Crime(){
/*        mId=UUID.randomUUID();
        mDate=new Date();*/
        this(UUID.randomUUID());
    }

    public Crime(UUID id){
        mId=id;
        mDate=new Date();
    }


    public UUID getmId() {
        return mId;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
