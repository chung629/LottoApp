package org.chunghyun.lottoapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "lotto_Select_database")
public class Lotto_Select_MyEntity {
    @PrimaryKey(autoGenerate =  true)
    int id;
    String round;
    String num1;
    String num2;
    String num3;
    String num4;
    String num5;
    String num6;
    String result;

    public Lotto_Select_MyEntity(String round, String num1, String num2, String num3, String num4, String num5, String num6, String result){
        this.round = round;
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
        this.num4 = num4;
        this.num5 = num5;
        this.num6 = num6;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public String getNum2() {
        return num2;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    public String getNum3() {
        return num3;
    }

    public void setNum3(String num3) {
        this.num3 = num3;
    }

    public String getNum4() {
        return num4;
    }

    public void setNum4(String num4) {
        this.num4 = num4;
    }

    public String getNum5() {
        return num5;
    }

    public void setNum5(String num5) {
        this.num5 = num5;
    }

    public String getNum6() {
        return num6;
    }

    public void setNum6(String num6) {
        this.num6 = num6;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int countCollect(ArrayList<String> numbers){
        int count = 0;
        ArrayList<String> list = new ArrayList<>();
        list.add(num1);
        list.add(num2);
        list.add(num3);
        list.add(num4);
        list.add(num5);
        list.add(num6);
        for(int i=0; i<numbers.size(); i++)
            if(list.contains(numbers.get(i)))
                count++;
        return count;
    }

    public boolean countBonus(String numbers){
        ArrayList<String> list = new ArrayList<>();
        list.add(num1);
        list.add(num2);
        list.add(num3);
        list.add(num4);
        list.add(num5);
        list.add(num6);
        return list.contains(numbers);
    }

    @NonNull
    @Override
    public String toString() {
        return "RecordData{" +
                "id='" + id + '\'' +
                ", round='" + round + '\'' +
                ", num1='" + num1 + '\'' +
                ", num2='" + num2 + '\'' +
                ", num3='" + num3 + '\'' +
                ", num4='" + num4 + '\'' +
                ", num5='" + num5 + '\'' +
                ", num6='" + num6 + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
