package com.zikozee.springbootfirebase.service;

import com.zikozee.springbootfirebase.model.Patient;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PatientService {

    String createPatient(Patient patient) throws InterruptedException, ExecutionException;

    Patient getSinglePatientDetails(String name) throws InterruptedException, ExecutionException;

    List<Patient> geAllPatients() throws ExecutionException, InterruptedException;

    String updatePatientDetails(Patient person) throws InterruptedException, ExecutionException;

    String deletePatient(String name);

}
