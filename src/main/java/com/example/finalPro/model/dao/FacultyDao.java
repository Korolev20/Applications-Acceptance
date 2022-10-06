package com.example.finalPro.model.dao;


import com.example.finalPro.model.dao.exception.FacultyDaoException;

import com.example.finalPro.model.entity.Faculty;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
public interface FacultyDao {

    List<Faculty> getAllFaculties() throws FacultyDaoException;
    Faculty getById(Connection connection, int facultyId) throws FacultyDaoException;

    int insert(Connection connection, Faculty faculty) throws FacultyDaoException;
    boolean delete(Connection connection, int facultyId) throws FacultyDaoException;
}
