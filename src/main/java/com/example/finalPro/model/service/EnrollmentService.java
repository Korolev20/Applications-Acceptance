package com.example.finalPro.model.service;

import com.example.finalPro.model.dao.ApplicantDao;
import com.example.finalPro.model.dao.EnrollmentDao;
import com.example.finalPro.model.dao.connection.ConnectionPool;
import com.example.finalPro.model.dao.exception.ApplicantDaoException;
import com.example.finalPro.model.dao.exception.DaoException;
import com.example.finalPro.model.dao.exception.EnrollmentDaoException;
import com.example.finalPro.model.entity.Applicant;
import com.example.finalPro.model.entity.Enrollment;
import com.example.finalPro.model.entity.Faculty;
import com.example.finalPro.model.service.exception.EnrollmentServiceException;
import com.google.common.annotations.VisibleForTesting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class EnrollmentService {
    private static final EnrollmentService instance = new EnrollmentService();
    private static final Logger logger = LogManager.getLogger(EnrollmentService.class);
    private final ConnectionPool pool=ConnectionPool.getInstance();

    public EnrollmentService() {
    }
    public static EnrollmentService getInstance() {
        return instance;
    }

    public List<Enrollment> getAllClosedEnrollments() throws EnrollmentServiceException {
        try {
            EnrollmentDao enrollmentDao = DaoHolder.getEnrollmentDao();
            return enrollmentDao.getAllClosedEnrollments();
        } catch (EnrollmentDaoException e) {
            logger.error(e.getMessage());
            throw new EnrollmentServiceException(e.getMessage());
        }
    }


    public Enrollment getLatestEnrollment() throws EnrollmentServiceException {
        try {
            EnrollmentDao enrollmentDao=DaoHolder.getEnrollmentDao();
           return enrollmentDao.getLatestEnrollment();

        } catch (EnrollmentDaoException e) {
            logger.error(e.getMessage());
            throw new EnrollmentServiceException(e.getMessage());
        }
    }

    public void openNewEnrollment(Timestamp timestamp) throws EnrollmentServiceException {
        try {
            EnrollmentDao enrollmentDao=DaoHolder.getEnrollmentDao();
            enrollmentDao.openNewEnrollment(timestamp);
        } catch (EnrollmentDaoException e) {
            logger.error(e.getMessage());
            throw new EnrollmentServiceException(e.getMessage());
        }
    }

    public void closeCurrentEnrollment(Timestamp timestamp) throws EnrollmentServiceException {
        Connection connection = pool.getConnection();
        try {
            connection.setAutoCommit(false);
            try {
                EnrollmentDao enrollmentDao=DaoHolder.getEnrollmentDao();
                ApplicantDao applicantDao=DaoHolder.getApplicantDao();
                enrollmentDao.closeCurrentEnrollment(connection,timestamp);
                Map<Faculty, TreeSet<Applicant>> currentApplicants = applicantDao.getCurrentEnrollmentApplicants(connection);
                if (!currentApplicants.isEmpty()) {
                    List<Integer> enrolledApplicantsIdList = calculateEnrolledApplicantsId(currentApplicants);
                    applicantDao.updateEnrolledApplicantsState(connection, enrolledApplicantsIdList);
                    applicantDao.updateNotEnrolledApplicantsState(connection);
                }
                connection.commit();
            } catch (EnrollmentDaoException | ApplicantDaoException e) {
                throw new DaoException(e.getMessage());
            } finally {
                connection.rollback();
                connection.setAutoCommit(true);
            }
        } catch (DaoException | SQLException ex) {
            logger.error(ex.getMessage());
            throw new EnrollmentServiceException(ex.getMessage());
        }
        finally {
            pool.releaseConnection(connection);
        }
    }

    @VisibleForTesting
    List<Integer> calculateEnrolledApplicantsId(Map<Faculty, TreeSet<Applicant>> applicants) {
        List<Integer> enrolledIdList = new ArrayList<>();
        Set<Faculty> faculties = applicants.keySet();
        int count;

        for (Faculty faculty : faculties) {
            count = 0;

            Set<Applicant> currentFacultyApplicants = applicants.get(faculty);

            for (Applicant currentFacultyApplicant : currentFacultyApplicants) {
                enrolledIdList.add(currentFacultyApplicant.getId());
                if (++count == faculty.getCapacity()) {
                    break;
                }
            }
        }
        return enrolledIdList;
    }
}