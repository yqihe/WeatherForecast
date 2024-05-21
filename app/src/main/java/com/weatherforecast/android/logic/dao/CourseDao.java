package com.weatherforecast.android.logic.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.weatherforecast.android.logic.model.course.Course;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert
    void insertCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Update
    void updateCourse(Course course);

    @Query("SELECT * FROM Course WHERE date = :date")
    LiveData<List<Course>> getCoursesByDate(String date);
}
