package com.zikozee.springbootfirebase.controller;


import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.zikozee.springbootfirebase.model.Patient;
import com.zikozee.springbootfirebase.model.UserRecordDTO;
import com.zikozee.springbootfirebase.service.AuthService;
import com.zikozee.springbootfirebase.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final AuthService authService;


    @GetMapping("/getSinglePatient")
    public Patient getPatient(@RequestParam String name) throws InterruptedException, ExecutionException {
        return patientService.getSinglePatientDetails(name);
    }

    @GetMapping("/getSinglePatient/name")
    public List<Patient> getPatientWhereNameEquals(@RequestParam String name) throws InterruptedException, ExecutionException {
        return patientService.getPatientsWhereNameEquals(name);
    }

    @GetMapping("/getSinglePatient/age")
    public List<Patient> getPatient(@RequestParam int age) throws InterruptedException, ExecutionException {
        return patientService.getPatientWhereAgeEquals(age);
    }

    @GetMapping("/patients")
    public List<Patient> patients() throws ExecutionException, InterruptedException {
        return patientService.geAllPatients();
    }

    @PostMapping("/createPatient")
    public String createPatient(@RequestBody Patient patient ) throws InterruptedException, ExecutionException {
        return patientService.createPatient(patient);
    }

    @PutMapping("/updatePatient")
    public String updatePatient(@RequestBody Patient patient  ) throws InterruptedException, ExecutionException {
        return patientService.updatePatientDetails(patient);
    }

    @DeleteMapping("/deletePatient")
    public String deletePatient(@RequestParam String name){
        return patientService.deletePatient(name);
    }

    @PostMapping("/createUser")
    public UserRecordDTO createUser(@RequestBody UserRecordDTO userRecordDTO) throws FirebaseAuthException {
        return authService.createUser(userRecordDTO);
    }

}
