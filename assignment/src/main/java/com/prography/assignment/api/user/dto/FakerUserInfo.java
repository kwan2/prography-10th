package com.prography.assignment.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FakerUserInfo {
    private final Integer id;
    private final String uuid;
    private final String firstname;
    private final String lastname;
    private final String username;
    private final String email;
    private final String ip;
    private final String macAddress;
    private final String website;
    private final String image;

}
