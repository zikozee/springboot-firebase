package com.zikozee.springbootfirebase.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.zikozee.springbootfirebase.model.UserRecordDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService{

    private FirebaseAuth mAuth;

    private void fireBaseConnector(){
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public UserRecordDTO createUser(UserRecordDTO userRecordDTO) throws FirebaseAuthException {
        fireBaseConnector();
        UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest();
        createRequest.setEmail(userRecordDTO.getEmail());
        createRequest.setPassword("qwerty123");
        createRequest.setPhoneNumber(userRecordDTO.getPhoneNUmber());
        createRequest.setDisplayName(userRecordDTO.getDisplayName());
        createRequest.setPhotoUrl(userRecordDTO.getPhotoUrl());

        return buildUSerRecord(mAuth.createUser(createRequest));

    }

    private UserRecordDTO buildUSerRecord(UserRecord userRecord){
        return UserRecordDTO.builder()
                .email(userRecord.getEmail())
                .displayName(userRecord.getDisplayName())
                .phoneNUmber(userRecord.getPhoneNumber())
                .photoUrl(userRecord.getPhotoUrl())
                .build();
    }
}
