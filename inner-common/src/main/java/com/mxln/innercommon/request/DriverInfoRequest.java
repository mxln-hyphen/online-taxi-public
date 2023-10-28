package com.mxln.innercommon.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DriverInfoRequest {
    private String id;

    private String address;

    private String driverName;

    private String driverPhone;

    private String driverGender;

    private String driverBirthday;

    private String driverNation;

    private String driverContactAddress;

    private String licenseId;

    private String getDriverLicenseDate;

    private String driverLicenseOn;

    private String driverLicenseOff;

    private String taxiDriver;

    private String certificateNo;

    private String networkCarIssueOrganization;

    private String networkCarIssueDate;

    private String getNetworkCarProofDate;

    private String networkCarProofOn;

    private String networkCarProofOff;

    private String registerDate;

    private String commercialType;

    private String contractCompany;

    private String contractOn;

    private String contractOff;

    private String state;

    private String flag;

    private String updateTime;
}
