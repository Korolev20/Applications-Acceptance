package com.example.finalPro.model.service;

import com.example.finalPro.model.dao.SubjectDao;
import com.example.finalPro.model.dao.connection.ConnectionPool;
import com.example.finalPro.model.dao.exception.SubjectDaoException;
import com.example.finalPro.model.entity.Subject;
import com.example.finalPro.model.service.exception.SubjectServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SubjectService {

    private static final Logger logger = LogManager.getLogger(SubjectService.class);
    private static final SubjectService instance = new SubjectService();

    public SubjectService() {
    }

    public static SubjectService getInstance() {
        return instance;
    }

    public List<Subject> getAllSubjects() throws SubjectServiceException {
        try {
            SubjectDao subjectDao = DaoHolder.getSubjectDao();
            return subjectDao.getAllSubjects();
        } catch (SubjectDaoException e) {
            logger.error(e.getMessage());
            throw new SubjectServiceException(e.getMessage());
        }
    }

    public boolean insert(Subject subject) throws SubjectServiceException {
        try {
            SubjectDao subjectDao = DaoHolder.getSubjectDao();
            return subjectDao.insert(subject);
        } catch (SubjectDaoException e) {
            logger.error(e.getMessage());
            throw new SubjectServiceException(e.getMessage());
        }
    }

    public boolean deleteSubject(int subjectId) throws SubjectServiceException {
        try {
            SubjectDao subjectDao = DaoHolder.getSubjectDao();
            return subjectDao.delete(subjectId);
        } catch (SubjectDaoException e) {
            logger.error(e.getMessage());
            throw new SubjectServiceException(e.getMessage());
        }
    }
}