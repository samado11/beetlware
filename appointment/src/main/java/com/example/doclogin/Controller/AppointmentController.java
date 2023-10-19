package com.example.doclogin.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.AttributeNotFoundException;


import com.example.doclogin.criteria.AppointmentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.doclogin.model.Appointment;

import com.example.doclogin.repository.AppointmentRepository;

@RestController
@CrossOrigin(allowedHeaders = "*",origins = "*")
@RequestMapping("/api/v3/")
public class AppointmentController {
	
	@Autowired
	private AppointmentRepository appointmentRepository;


	
	@GetMapping("/appointments")
	public List<Appointment> getAllAppointments(){
		return appointmentRepository.findAll();
	}
	
	@PostMapping("/appointments")
	public Appointment createAppointment(@RequestBody Appointment appointment) {
		return appointmentRepository.save(appointment);
	}
	
	@GetMapping("/appointments/filter")
	public ResponseEntity<List<Appointment>> filter(@RequestParam(required = false) String name, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		

		AppointmentSpecification appointmentSearch = new AppointmentSpecification();
		appointmentSearch = appointmentSearch.search(name, date);
		List<Appointment> appointment = appointmentRepository.findAll(appointmentSearch);
		return ResponseEntity.ok(appointment);
	}
	
	@DeleteMapping("/appointments/{id}")
	public ResponseEntity<Map<String,Boolean>> cancelAppointment(@PathVariable Long id,@RequestParam(required = false) String cancelationReason) throws AttributeNotFoundException{
		
		Appointment appointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new AttributeNotFoundException("ABCD" + id));

		appointment.setCancellationReason(cancelationReason);
		appointmentRepository.save(appointment);
		Map<String, Boolean> response = new HashMap<>();
		response.put("cancelled", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	

}
