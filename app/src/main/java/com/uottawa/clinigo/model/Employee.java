package com.uottawa.clinigo.model;

public class Employee extends User {

    private ClinicInfo clinicInfo;
    private WorkingHours workingHours;

    public Employee(String id, String email, String firstName, String lastName) {
        super(id, email, firstName, lastName);
        setRole("Employee");
        //this.workingHours = new WorkingHours();//Initialize Working hours as the default in the beginning
    }

    public void setWorkingHours(WorkingHours workingHours) {this.workingHours = workingHours;}

    public WorkingHours getWorkingHours() {return  this.workingHours;}

    public void setClinicInfo(ClinicInfo info){ this.clinicInfo = info;}

    public ClinicInfo getClinicInfo(){return this.clinicInfo;}

}
