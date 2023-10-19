package com.example.doclogin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.doclogin.model.Appointment;

import java.util.List;


public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

    List<Appointment> findAll(Specification<Appointment> searchCriteria);
}

