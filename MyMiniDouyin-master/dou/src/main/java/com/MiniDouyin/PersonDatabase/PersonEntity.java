package com.MiniDouyin.PersonDatabase;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName="person")
public class PersonEntity {
    @PrimaryKey(autoGenerate=true)

    @ColumnInfo(name="sequence")
    public Long sequence;

    @ColumnInfo(name="id")
    public String id;

    @ColumnInfo(name="name")
    public String name;

//    @ColumnInfo(name="protrait")
//    private Picture protrait;

    public PersonEntity(String id,String name){
        this.id=id;
        this.name=name;
    }

    public String GetId(){return id; }

    public void SetId(String id){this.id=id;}

    public String GetName(){return name; }

    public void SetName(String name){this.name=name;}
}
