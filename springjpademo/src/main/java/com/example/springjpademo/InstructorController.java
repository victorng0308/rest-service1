package com.example.springjpademo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstructorController {
  @Autowired private InstructorRepository instructorRepository;

  @Autowired private InstructorDetailsRepository instructorDetailsRepository;

  
  @GetMapping("/instructors")
  public List<Instructor> getInstructors() {
    return instructorRepository.findAll();
  }

  @GetMapping("/instructors/{id}")
  public ResponseEntity<Optional<Instructor>> getInstructorById(
      @PathVariable(value = "id") Long instructorId) throws Exception {
    Optional<Instructor> user = instructorRepository.findById(instructorId);
    return ResponseEntity.ok().body(user);
  }

  @PostMapping("/instructors")
  public Instructor createUser(@RequestBody Instructor instructor) {
    return instructorRepository.save(instructor);
  }

  @PutMapping("/instructors/{id}")
  public ResponseEntity<Instructor> updateUser(
      @PathVariable(value = "id") Long instructorId, @RequestBody InstructorDetail userDetails)
      throws Exception {
    Optional<Instructor> instructor = instructorRepository.findById(instructorId);
    if (instructor.isPresent()) {
      InstructorDetail _details = new InstructorDetail();
      _details.setYoutubeChannel(userDetails.getYoutubeChannel());
      _details.setHobby(userDetails.getHobby());
      Instructor _instructor = instructor.get();
      _instructor.setInstructorDetail(_details);
      final Instructor updatedUser = instructorRepository.save(_instructor);
      return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/instructors/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable(value = "id") Long instructorId)
      throws Exception {
    Optional<Instructor> instructor = instructorRepository.findById(instructorId);
    if (instructor.isPresent()) {
      Instructor _instructor = instructor.get();
      instructorRepository.delete(_instructor);
      Map<String, Boolean> response = new HashMap<>();
      response.put("deleted", Boolean.TRUE);
      return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  
  @DeleteMapping("/instructors/{id}/details")
  public ResponseEntity<String> deleteInstructorDetails(@PathVariable(value = "id") Long instructorId)
      throws Exception {
    Optional<Instructor> instructor = instructorDetailsRepository.findById(instructorId);
    if (instructor.isPresent()) {	
      Instructor _instructor = instructor.get();
      
      InstructorDetail details = _instructor.getInstructorDetail();
      instructorDetailsRepository.delete(_instructor);
      Map<String, Boolean> response = new HashMap<>();
      response.put("deleted", Boolean.TRUE);
      return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
