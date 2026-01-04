package com.mkumarasamy.facultyappraisal.model;

public class Faculty {
    private String name;
    private FacultyLevel level;
    private boolean isPhdHolder;
    private String email;
    private String department;
    private String phone;
    private String dateOfJoining;
    private String employeeId;

    public Faculty() {}

    public Faculty(String name, FacultyLevel level, boolean isPhdHolder) {
        this.name = name;
        this.level = level;
        this.isPhdHolder = isPhdHolder;
    }

    public Faculty(String name, FacultyLevel level, boolean isPhdHolder, String email,
                   String department, String phone, String dateOfJoining, String employeeId) {
        this.name = name;
        this.level = level;
        this.isPhdHolder = isPhdHolder;
        this.email = email;
        this.department = department;
        this.phone = phone;
        this.dateOfJoining = dateOfJoining;
        this.employeeId = employeeId;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public FacultyLevel getLevel() { return level; }
    public void setLevel(FacultyLevel level) { this.level = level; }

    public boolean isPhdHolder() { return isPhdHolder; }
    public void setPhdHolder(boolean phdHolder) { isPhdHolder = phdHolder; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDateOfJoining() { return dateOfJoining; }
    public void setDateOfJoining(String dateOfJoining) { this.dateOfJoining = dateOfJoining; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
}
