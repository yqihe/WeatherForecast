package com.weatherforecast.android.ui.course;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.weatherforecast.android.logic.model.course.Course;
import com.weatherforecast.android.logic.CourseAppDatabase;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private final CourseAppDatabase database;

    public CourseViewModel(Application application) {
        super(application);
        database = CourseAppDatabase.getDatabase(application);
    }

    // 添加课程
    public void insertCourse(Course course) {
        database.insertCourse(course);
    }

    // 删除课程
    public void deleteCourse(Course course) {
        database.deleteCourse(course);
    }

    // 更新课程
    public void updateCourse(Course course) {
        database.updateCourse(course);
    }

    // 获取特定日期的所有课程
    public LiveData<List<Course>> getCoursesByDate(String date) {
        return database.courseDao().getCoursesByDate(date);
    }
}
