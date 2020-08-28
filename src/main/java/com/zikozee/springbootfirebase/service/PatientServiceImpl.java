package com.zikozee.springbootfirebase.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.zikozee.springbootfirebase.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class PatientServiceImpl implements PatientService{

    public static final String COL_NAME="users";
    private Firestore mDbFirestore;

    public String createPatient(Patient patient) throws InterruptedException, ExecutionException {
        mDbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = mDbFirestore.collection(COL_NAME).document(patient.getName()).set(patient);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Patient getSinglePatientDetails(String name) throws InterruptedException, ExecutionException {
        mDbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = mDbFirestore.collection(COL_NAME).document(name);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        DocumentSnapshot document = future.get();

        Patient patient;

        if(document.exists()) {
            patient = document.toObject(Patient.class);
            return patient;
        }else {
            return null;
        }
    }

    @Override
    public List<Patient> geAllPatients(){
        mDbFirestore = FirestoreClient.getFirestore();
        Iterable<DocumentReference> collections = mDbFirestore.collection("users").listDocuments();


        List<Patient> patients = new ArrayList<>();

        collections.forEach(documentReference -> {
            try {
                DocumentSnapshot document = documentReference.get().get();
                patients.add(document.toObject(Patient.class));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        return patients;
    }

    public String updatePatientDetails(Patient person) throws InterruptedException, ExecutionException {
        mDbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = mDbFirestore.collection(COL_NAME).document(person.getName()).set(person);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String deletePatient(String name) {
        mDbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = mDbFirestore.collection(COL_NAME).document(name).delete();
        if(writeResult.isDone()){
            log.info("Patient deleted");
        }
        return "Document with Patient ID "+name+" has been deleted";
    }
}
