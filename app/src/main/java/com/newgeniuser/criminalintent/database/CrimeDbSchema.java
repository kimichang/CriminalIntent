package com.newgeniuser.criminalintent.database;

/**
 * Created by KimiChang on 2016/7/6.
 */
public class CrimeDbSchema {
    public static final class CrimeTable{
        public static final String NAME="crime";
        public static final class Cols{
            public static final String UUID="uuid";
            public static final String TITLE="title";
            public static final String DATE="date";
            public static final String SOLVED="solved";
            public static final String SUSPECT="suspect";
        }
    }
}
