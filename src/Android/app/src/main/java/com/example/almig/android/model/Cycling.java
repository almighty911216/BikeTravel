package com.example.almig.android.model;

import android.location.Location;
import android.util.Log;

/**
 * Created by ailab on 2017-06-03.
 */

/*
    이 Cycling Class 는 시속 60km 까지 고려하여 만들어졌음.
*/

//RPM 계산 방법 : 자전거 바퀴 둘레를 km 로 구하고 총 Distance 에서 나눈 총 Cycling 한 시간을 분 단위로 하여 나누면 된다.

public class Cycling {
    private static final String TAG = "Cycling";
    private float[] distance;
    private double velocities;
    private static final double bikewheel_size = 0.00207;//자전거 26인치 바퀴의 지름을 km 로 나타낸 것 (바퀴 둘레를 km 로 환산)
    private static final double VELOCITY_THRESHOLD = 10;
    private int index = 0;
    private double current_velocity = 0;
    private long start_time = 0;
    private double total_distance = 0;

    public double getCurrent_velocity() {
        return current_velocity;
    }

    public Cycling(){
        distance = new float[2];
        this.start_time = System.currentTimeMillis();
    }


    public void UpdateVelocity(double last_latitude, double last_longitude, double latitude, double longitude, double last_update){
        double velocity = CalculateVelocity(last_latitude,last_longitude, latitude, longitude, last_update);
        Log.d(TAG, String.valueOf(velocity));
        if(Math.abs(current_velocity - velocity) <= VELOCITY_THRESHOLD) {
            if (index >= 1) {
                current_velocity = (velocities + velocity) / 2.0;
                if(current_velocity < 0.1){
                    current_velocity = 0;
                }
                index = 0;
            } else {
                velocities = velocity;
                index++;
            }
        }
    }

    public double getAvg_velocity(){
        return total_distance / (System.currentTimeMillis() - start_time) * 3600;
    }

    public int getRPM(){
        return (int)(total_distance / (System.currentTimeMillis() - start_time) * 60 / bikewheel_size);
    }

    public String getTime(){
        String str_time = "";
        long time = (System.currentTimeMillis() - start_time)/1000;
        long second = time % 60;
        time = time / 60;
        str_time = String.valueOf(time) + ":" + String.valueOf(second);
        return str_time;
    }

    public double getTotal_distance(){
        return total_distance;
    }

    public double CalculateVelocity(double last_latitude, double last_longitude, double latitude, double longitude, double last_update){
        Location.distanceBetween(last_latitude, last_longitude, latitude, longitude, distance);
        if(distance[0] > 1000 || distance[0] < 1){
            return 0.0;
        }
        total_distance = total_distance + distance[0];
        Log.d(TAG,String.valueOf(total_distance));
        return distance[0] / (System.currentTimeMillis() - last_update) * 3600;
    }

    private double CaloriesConstant(){
        double average_velocity = getAvg_velocity();
        if(average_velocity <= 0){
            return 0;
        }else if(average_velocity < 16){
            return 0.0783;
        }else if(average_velocity < 19){
            return 0.0939;
        }else if(average_velocity < 22){
            return 0.113;
        }else if(average_velocity < 24){
            return 0.124;
        }else if(average_velocity < 26){
            return 0.136;
        }else if(average_velocity < 27){
            return 0.149;
        }else if(average_velocity < 29){
            return 0.163;
        }else if(average_velocity < 31){
            return 0.179;
        }else if(average_velocity < 32){
            return 0.196;
        }else if(average_velocity < 34){
            return 0.0215;
        }else if(average_velocity < 37){
            return 0.259;
        }
        return 0.311;
    }

    public double CalcualteCalories(){
        Log.d(TAG, "Start Time : " + String.valueOf((System.currentTimeMillis() - start_time) / 60000));
        Log.d(TAG, "Calories Constant : " + String.valueOf(CaloriesConstant()));
        return roundup(68 * CaloriesConstant() * (System.currentTimeMillis() - start_time) / 60000);

    }

    public double roundup(double value){
        return (double)Math.round(value * 10) / 10;
    }

}
