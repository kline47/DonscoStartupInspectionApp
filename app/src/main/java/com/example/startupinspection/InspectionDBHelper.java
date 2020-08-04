package com.example.startupinspection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.startupinspection.InspectionContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class InspectionDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "StartupInspection.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public InspectionDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.db = sqLiteDatabase;

        String SQL_CREATE_USER_INFO_TABLE = "Create Table " + userTable.TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " name TEXT, date TEXT, truck_num TEXT, department TEXT)";



        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionTable.TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                //QuestionTable.COLUMN_COMMENT + " TEXT, " +
                QuestionTable.COLUMN_ANS_NUM + " INTEGER " + ")";

        db.execSQL(SQL_CREATE_USER_INFO_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + userTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);
    }

    public void addData(String name, String date, String truck, String dept) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(userTable.USER_NAME, name);
        contentValues.put(userTable.USER_DATE, date);
        contentValues.put(userTable.USER_TRUCK_NUM, truck);
        contentValues.put(userTable.USER_DEPT, dept);
        db.insert(userTable.TABLE_NAME, null, contentValues);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Obvious Damage or Loose Parts?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q1);
        Question q2 = new Question("Overhead Guard?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q2);
        Question q3 = new Question("Mast/Carriage?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q3);
        Question q4 = new Question("Forks and Locking Pins?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q4);
        Question q5 = new Question("Chains and Hydraulic Hoses?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q5);
        Question q6 = new Question("Data Plate?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q6);
        Question q7 = new Question("Trash or Scrap Accumulations?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q7);
        Question q8 = new Question("Leaks?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q8);
        Question q9 = new Question("Seat Belt?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q9);
        Question q10 = new Question("Steering?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q10);
        Question q11 = new Question("Controls?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q11);
        Question q12 = new Question("Gauges?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q12);
        Question q13 = new Question("Operation of Lift?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q13);
        Question q14 = new Question("Unusual Noises?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q14);
        Question q15 = new Question("Operational Brakes?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q15);
        Question q16 = new Question("Parking Brake?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q16);
        Question q17 = new Question("Head and Tail Lights?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q17);
        Question q18 = new Question("Horn?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q18);
        Question q19 = new Question("Back Up Alarm?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q19);
        Question q20 = new Question("Warning Lights?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q20);
        Question q21 = new Question("Tire Condition?", "Satisfactory", "Unsatisfactory", 1);
        addQuestion(q21);
    }

    private void addQuestion (Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionTable.COLUMN_ANS_NUM, question.getAnsNum());
        //cv.put(QuestionTable.COLUMN_COMMENT, question.getComment());
        db.insert(QuestionTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setAnsNum(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_ANS_NUM)));
                //question.setComment(c.getString(c.getColumnIndex((QuestionTable.COLUMN_COMMENT))));
                questionList.add(question);
            } while(c.moveToNext());
        }
        c.close();
        return questionList;
    }

}
