package com.example.kaiwan_expense_tracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "exp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateTable = "CREATE TABLE EXPENSE(EMP_NAME VARCHAR(50), EXPENSE_NAME VARCHAR(50), EXPENSE FLOAT(10))";
        db.execSQL(CreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS EXPENSE");
        onCreate(db);
    }

    public Boolean insertData(String emp, String exp_nm, float exp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("EMP_NAME", emp);
        cv.put("EXPENSE_NAME", exp_nm);
        cv.put("EXPENSE", exp);
        long result = db.insert("EXPENSE", null, cv);
        return result != -1;
    }

    public Boolean removeData(String emp, String exp_nm, float exp) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM EXPENSE WHERE EMP_NAME=? AND EXPENSE_NAME=? AND EXPENSE=?", new String[]{emp, exp_nm, String.valueOf(exp)});
        if (cursor.getCount() > 0) {
            long result = db.delete("EXPENSE", "EMP_NAME=? AND EXPENSE_NAME=? AND EXPENSE=?", new String[]{emp, exp_nm, String.valueOf(exp)});
            return result != -1;
        } else {
            return false;
        }
    }

    public float getTotalExpenseForEmployee(String empName) {
        float totalExpense = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(EXPENSE) FROM EXPENSE WHERE EMP_NAME=?", new String[]{empName});

        if (cursor.moveToFirst()) {
            totalExpense = cursor.getFloat(0);
        }

        cursor.close();
        return totalExpense;
    }

    public float getTotalExpenseForALLEmployees() {
        float totalExpense = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(EXPENSE) FROM EXPENSE", null);

        if (cursor.moveToFirst()) {
            totalExpense = cursor.getFloat(0);
        }

        cursor.close();
        return totalExpense;
    }

    public ArrayList<String> getAllExpensesForEmployee(String empName) {
        ArrayList<String> receipts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT EXPENSE_NAME, EXPENSE FROM EXPENSE WHERE EMP_NAME=?", new String[]{empName});

        while (cursor.moveToNext()) {
            String expenseName = cursor.getString(0);
            float expenseValue = cursor.getFloat(1);
            receipts.add(expenseName + ": " + expenseValue);
        }

        cursor.close();
        return receipts;
    }

    public ArrayList<String> getAllExpensesForAllEmployees() {
        ArrayList<String> allReceipts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT EMP_NAME, EXPENSE_NAME, EXPENSE FROM EXPENSE", null);

        while (cursor.moveToNext()) {
            String empName = cursor.getString(0);
            String expenseName = cursor.getString(1);
            float expenseValue = cursor.getFloat(2);
            allReceipts.add(empName + ": " + expenseName + ": " + expenseValue);
        }

        cursor.close();
        return allReceipts;
    }
}
