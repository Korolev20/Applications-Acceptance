package com.example.finalPro.model.service;

import com.example.finalPro.model.dao.implementation.*;

public final class DaoHolder {
    private static final ApplicantDaoImpl applicantDao;
    private static final EnrollmentDaoImpl enrollmentDao;
    private static final FacultyDaoImpl facultyDao;
    private static final UserDaoImpl userDao;
    private static final SubjectDaoImpl subjectDao;

    static {
        applicantDao = new ApplicantDaoImpl();
        enrollmentDao = new EnrollmentDaoImpl();
        facultyDao=new FacultyDaoImpl();
        userDao=new UserDaoImpl();
        subjectDao=new SubjectDaoImpl();

    }
    private DaoHolder() {
    }

    public static ApplicantDaoImpl getApplicantDao() {
        return applicantDao;
    }

    public static EnrollmentDaoImpl getEnrollmentDao() {
        return enrollmentDao;
    }

    public static FacultyDaoImpl getFacultyDao() {
        return facultyDao;
    }

    public static UserDaoImpl getUserDao() {
        return userDao;
    }

    public static SubjectDaoImpl getSubjectDao() {
        return subjectDao;
    }
}
