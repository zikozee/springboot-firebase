package com.zikozee.springbootfirebase.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.zikozee.springbootfirebase.model.UserRecordDTO;

public interface AuthService {

    UserRecordDTO createUser(UserRecordDTO userRecordDTO) throws FirebaseAuthException;
}
