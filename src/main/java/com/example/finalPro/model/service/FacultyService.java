package com.example.finalPro.model.service;

import com.example.finalPro.model.dao.ApplicantDao;
import com.example.finalPro.model.dao.FacultyDao;
import com.example.finalPro.model.dao.SubjectDao;
import com.example.finalPro.model.dao.connection.ConnectionPool;
import com.example.finalPro.model.dao.exception.ApplicantDaoException;
import com.example.finalPro.model.dao.exception.DaoException;
import com.example.finalPro.model.dao.exception.FacultyDaoException;
import com.example.finalPro.model.dao.exception.SubjectDaoException;
import com.example.finalPro.model.entity.Applicant;
import com.example.finalPro.model.entity.Faculty;
import com.example.finalPro.model.service.exception.FacultyServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FacultyService {
    private static final Logger logger = LogManager.getLogger(FacultyService.class);
    private final ConnectionPool pool = ConnectionPool.getInstance();
    private static final FacultyService instance = new FacultyService();

    public FacultyService() {
    }

    public static FacultyService getInstance() {
        return instance;
    }

    public List<Faculty> getAllFaculties() throws FacultyServiceException {
        try {
            FacultyDao facultyDao = DaoHolder.getFacultyDao();
            return facultyDao.getAllFaculties();
        } catch (FacultyDaoException e) {
            logger.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
    }

    public Faculty getById(int facultyId) throws FacultyServiceException {
        Connection connection = pool.getConnection();
        Faculty faculty;
        try {
            FacultyDao facultyDao = DaoHolder.getFacultyDao();
            SubjectDao subjectDao = DaoHolder.getSubjectDao();
            faculty = facultyDao.getById(connection,facultyId);
            if (faculty != null) {
                faculty.setRequiredSubjects(subjectDao.getRequiredSubjects(connection,faculty.getId()));
            }
            return faculty;
        } catch (FacultyDaoException | SubjectDaoException e) {
            logger.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
        finally {
            pool.releaseConnection(connection);
        }
    }

    public void addFaculty(Faculty faculty) throws FacultyServiceException {
        Connection connection = pool.getConnection();
        try {
            connection.setAutoCommit(false);
            try {
                FacultyDao facultyDao = DaoHolder.getFacultyDao();
                SubjectDao subjectDao = DaoHolder.getSubjectDao();
                int newFacultyId = facultyDao.insert(connection,faculty);
                subjectDao.insertRequiredSubjects(connection,newFacultyId, faculty.getRequiredSubjects());
                connection.commit();
            } catch (FacultyDaoException | SubjectDaoException e) {
                connection.rollback();
                throw new DaoException(e.getMessage());
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (DaoException | SQLException e) {
            logger.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
        finally {
            pool.releaseConnection(connection);
        }
    }

    public void deleteFaculty(int facultyId) throws FacultyServiceException {
        Connection connection = pool.getConnection();
        try {
            connection.setAutoCommit(false);
            try {
                SubjectDao subjectDao = DaoHolder.getSubjectDao();
                FacultyDao facultyDao = DaoHolder.getFacultyDao();
                ApplicantDao applicantDao = DaoHolder.getApplicantDao();
                List<Applicant> applicants = applicantDao.getApplicantsIdByFacultyId(connection,facultyId);
                if (!applicants.isEmpty()) {
                    for (Applicant applicant : applicants) {
                        subjectDao.deleteGradesByApplicantId(connection, applicant.getId());
                        applicantDao.deleteApplicantById(connection,applicant.getId());
                    }
                }
                subjectDao.deleteRequiredSubjectsByFacultyId(connection, facultyId);
                facultyDao.delete(connection, facultyId);
                connection.commit();
            } catch (FacultyDaoException | SubjectDaoException | ApplicantDaoException e) {
                connection.rollback();
                throw new DaoException((e.getMessage()));
            } finally {
                connection.setAutoCommit(true);

            }
        } catch (DaoException | SQLException e) {
            logger.error(e.getMessage());
            throw new FacultyServiceException(e.getMessage());
        }
        finally {
            pool.releaseConnection(connection);
        }
    }
}

