package com.ysered.beamsample.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;


@Entity(tableName = "task")
public class TaskEntity implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private boolean isDone;
    private String summary;

    public TaskEntity() {
    }

    private TaskEntity(Parcel in) {
        id = in.readInt();
        isDone = in.readByte() != 0;
        summary = in.readString();
    }

    public static final Creator<TaskEntity> CREATOR = new Creator<TaskEntity>() {
        @Override
        public TaskEntity createFromParcel(Parcel in) {
            return new TaskEntity(in);
        }

        @Override
        public TaskEntity[] newArray(int size) {
            return new TaskEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeByte((byte) (isDone ? 1 : 0));
        parcel.writeString(summary);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return String.format("TaskEntity(id=%s, isDone=%s, summary=%s)", id, isDone, summary);
    }
}
