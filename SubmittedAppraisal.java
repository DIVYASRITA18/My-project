package com.mkumarasamy.facultyappraisal.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SubmittedAppraisal {
    private Faculty faculty;
    private AppraisalData appraisalData;
    private double academicScore;
    private double researchScore;
    private double extensionScore;
    private double totalScore;
    private String eligibility;
    private String status; // "submitted", "reviewed"
    private String submissionDate;
    private String adminComments;
    private String adminActionDate;

    public SubmittedAppraisal() {}

    public SubmittedAppraisal(Faculty faculty, AppraisalData appraisalData, double academicScore, double researchScore, double extensionScore, double totalScore, String eligibility) {
        this.faculty = faculty;
        this.appraisalData = appraisalData;
        this.academicScore = academicScore;
        this.researchScore = researchScore;
        this.extensionScore = extensionScore;
        this.totalScore = totalScore;
        this.eligibility = eligibility;
        this.status = "submitted";
        this.submissionDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Getters and Setters
    public Faculty getFaculty() { return faculty; }
    public void setFaculty(Faculty faculty) { this.faculty = faculty; }

    public AppraisalData getAppraisalData() { return appraisalData; }
    public void setAppraisalData(AppraisalData appraisalData) { this.appraisalData = appraisalData; }

    public double getAcademicScore() { return academicScore; }
    public void setAcademicScore(double academicScore) { this.academicScore = academicScore; }

    public double getResearchScore() { return researchScore; }
    public void setResearchScore(double researchScore) { this.researchScore = researchScore; }

    public double getExtensionScore() { return extensionScore; }
    public void setExtensionScore(double extensionScore) { this.extensionScore = extensionScore; }

    public double getTotalScore() { return totalScore; }
    public void setTotalScore(double totalScore) { this.totalScore = totalScore; }

    public String getEligibility() { return eligibility; }
    public void setEligibility(String eligibility) { this.eligibility = eligibility; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(String submissionDate) { this.submissionDate = submissionDate; }

    public String getAdminComments() { return adminComments != null ? adminComments : "No comments yet"; }
    public void setAdminComments(String adminComments) { this.adminComments = adminComments; }

    public String getAdminActionDate() { return adminActionDate != null ? adminActionDate : "Not set"; }
    public void setAdminActionDate(String adminActionDate) { this.adminActionDate = adminActionDate; }
}
