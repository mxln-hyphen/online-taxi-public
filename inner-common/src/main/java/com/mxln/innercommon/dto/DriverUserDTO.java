package com.mxln.innercommon.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("driver")
public class DriverUserDTO {

    private Integer id;

    private String address;

    private String driverName;

    private String driverPhone;

    private Byte driverGender;

    private LocalDate driverBirthday;

    private String driverNation;

    private String driverContactAddress;

    private String licenseId;

    private LocalDate getDriverLicenseDate;

    private LocalDate driverLicenseOn;

    private LocalDate driverLicenseOff;

    private Byte taxiDriver;

    private String certificateNo;

    private String networkCarIssueOrganization;

    private LocalDate networkCarIssueDate;

    private LocalDate getNetworkCarProofDate;

    private LocalDate networkCarProofOn;

    private LocalDate networkCarProofOff;

    private LocalDate registerDate;

    private Byte commercialType;

    private String contractCompany;

    private LocalDate contractOn;

    private LocalDate contractOff;

    private Byte state;

    private Byte flag;

    private LocalDate updateTime;


}
