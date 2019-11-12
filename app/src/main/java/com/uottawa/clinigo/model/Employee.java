package com.uottawa.clinigo.model;

public class Employee extends User {

    private ClinicInfo clinicInfo;

    public Employee(String id, String email, String firstName, String lastName) {
        super(id, email, firstName, lastName);
        setRole("Employee");
    }

    public void setClinicInfo(ClinicInfo info){ this.clinicInfo = info;}

    public ClinicInfo getClinicInfo(){return this.clinicInfo;}

}
