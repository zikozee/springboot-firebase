package com.zikozee.springbootfirebase.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
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

    public static final String DB_NAME="users";
    private Firestore mDbFirestore;

    public String createPatient(Patient patient) throws InterruptedException, ExecutionException {
        firebaseConnector();
        ApiFuture<WriteResult> collectionsApiFuture = mDbFirestore.collection(DB_NAME).document(patient.getName()).set(patient);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    private void firebaseConnector() {
        mDbFirestore = FirestoreClient.getFirestore();
    }

    public Patient getSinglePatientDetails(String name) throws InterruptedException, ExecutionException {
        firebaseConnector();
        DocumentReference documentReference = mDbFirestore.collection(DB_NAME).document(name);
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
        firebaseConnector();
        Iterable<DocumentReference> collections = mDbFirestore.collection(DB_NAME).listDocuments();

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

    @Override
    public List<Patient> getPatientsWhereNameEquals(String value) throws ExecutionException, InterruptedException {
        firebaseConnector();
        CollectionReference patients = mDbFirestore.collection(DB_NAME);

        Query query = patients.whereEqualTo("name", value);

        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<Patient> patientList = new ArrayList<>();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            patientList.add(document.toObject(Patient.class));
        }

        return patientList;
    }

    @Override
    public List<Patient> getPatientWhereAgeEquals(int age) throws ExecutionException, InterruptedException {
        firebaseConnector();
        CollectionReference patients = mDbFirestore.collection(DB_NAME);

        Query query = patients.whereEqualTo("age", age);

        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<Patient> patientList = new ArrayList<>();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            patientList.add(document.toObject(Patient.class));
        }

        return patientList;
    }

    public String updatePatientDetails(Patient person) throws InterruptedException, ExecutionException {
        firebaseConnector();
        ApiFuture<WriteResult> collectionsApiFuture = mDbFirestore.collection(DB_NAME).document(person.getName()).set(person);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String deletePatient(String name) {
        firebaseConnector();
        ApiFuture<WriteResult> writeResult = mDbFirestore.collection(DB_NAME).document(name).delete();
        if(writeResult.isDone()){
            log.info("Patient deleted");
        }
        return "Document with Patient ID "+name+" has been deleted";
    }
}
