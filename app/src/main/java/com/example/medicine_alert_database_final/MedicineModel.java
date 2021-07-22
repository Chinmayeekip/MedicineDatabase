package com.example.medicine_alert_database_final;


public class MedicineModel {

    private int id;
    private String name;
    private final String date;
    private String time;

    public MedicineModel(int id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
    //toString
    @Override
    public String toString() {
        return "MedicineModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +  '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }
    public String getName() { return name; }
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }

}
