package com.bean;

import java.util.List;

public class Course {
    String message;
    int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<CourseInfo> getObject() {
        return object;
    }

    public void setObject(List<CourseInfo> object) {
        this.object = object;
    }

    List<CourseInfo> object;



    public static class CourseInfo{
        private int course_id,allow_join,course_credit,course_description;
        private String class_name,course_name,department_name,school_name;
        private String semester;

        public int getCourse_id() {
            return course_id;
        }

        public void setCourse_id(int course_id) {
            this.course_id = course_id;
        }

        public int getAllow_join() {
            return allow_join;
        }

        public void setAllow_join(int allow_join) {
            this.allow_join = allow_join;
        }

        public int getCourse_credit() {
            return course_credit;
        }

        public void setCourse_credit(int course_credit) {
            this.course_credit = course_credit;
        }

        public int getCourse_description() {
            return course_description;
        }

        public void setCourse_description(int course_description) {
            this.course_description = course_description;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getCourse_name() {
            return course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }

        public String getDepartment_name() {
            return department_name;
        }

        public void setDepartment_name(String department_name) {
            this.department_name = department_name;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getSemester() {
            return semester;
        }

        public void setSemester(String semester) {
            this.semester = semester;
        }
    }
}
