package com.example.doclogin.criteria;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.example.doclogin.model.Appointment;
import org.springframework.data.jpa.domain.Specification;



public class AppointmentSpecification implements Specification<Appointment> {

    private String name;

    private Date date;



    @Override
    public Predicate toPredicate(Root<Appointment> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Predicate> predicates = new ArrayList<>();
        query.distinct(true);

        if(getName().isPresent()) {
            predicates.add(builder.equal(root.get("name"), name));
        }


        if(getDate().isPresent()) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            System.out.println(calendar.getTime());
            predicates.add(builder.lessThanOrEqualTo(root.get("date").as(Date.class), calendar.getTime()));
            predicates.add(builder.greaterThanOrEqualTo(root.get("date").as(Date.class), date));
        }



        Predicate[] predicatesArray = new Predicate[predicates.size()];
        return builder.and(predicates.toArray(predicatesArray));
    }

    public AppointmentSpecification search(
            String name,Date date) {

        this.name = name;
        this.date = date;


        return this;
    }



    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<Date> getDate() {
        return Optional.ofNullable(date);
    }
}
