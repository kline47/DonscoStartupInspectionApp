package com.example.startupinspection;

import android.provider.BaseColumns;

public final class InspectionContract {

    public static class userTable implements BaseColumns {
        public static final String TABLE_NAME = "user_info";
        public static final String USER_NAME = "name";
        public static final String USER_DATE = "date";
        public static final String USER_TRUCK_NUM = "truck_num";
        public static final String USER_DEPT = "department";
    }

    private InspectionContract() {}

    public static class QuestionTable implements BaseColumns {
        public static final String TABLE_NAME = "inspection_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_ANS_NUM = "answer_number";
        //public static final String COLUMN_COMMENT = "comment";
    }
}
