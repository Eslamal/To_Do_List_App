package com.example.to_do_list_app.database;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM task_table ORDER BY id ASC")
    LiveData<List<Task>> getAllTasks();
    @Query("DELETE FROM task_table")
    void deleteAllTasks();
}

