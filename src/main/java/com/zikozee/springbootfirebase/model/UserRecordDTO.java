package com.zikozee.springbootfirebase.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRecordDTO {


//    private String uid;
    private String email;
    private String displayName;
    private String phoneNUmber;
    private String photoUrl;
}
