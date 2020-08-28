package com.zikozee.springbootfirebase.controller;


import com.zikozee.springbootfirebase.model.Patient;
import com.zikozee.springbootfirebase.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;


    @GetMapping("/getSinglePatient")
    public Patient getPatient(@RequestParam String name ) throws InterruptedException, ExecutionException {
        return patientService.getSinglePatientDetails(name);
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

}
