package com.mkumarasamy.facultyappraisal.model;

public class AppraisalData {
    // PART A: Academic Performance [40 Marks]
    private double academicResults; // Average percentage
    private boolean hasMinSubjectResult; // true if all subjects >=75%
    private int miniProjectCount; // Per batch/per student
    private double studentFeedback; // Average percentage
    private double hodFeedback; // As per rubrics
    private int innovations; // Number of innovations
    private int mentorshipPoints; // Mentorship points
    private int productDevelopment; // Per product
    private int seminarParticipation; // Participation certificates
    private int seminarPrizes; // Prize winning
    private int workshopParticipation;
    private int workshopPrizes;
    private int projectCompetitionParticipation;
    private int projectCompetitionPrizes;
    private int languageCertifications;
    private int onlineCertifications; // Global/MNC/NPTEL/Others
    private int internships; // With/without stipend
    private int specialAwards;
    private int studentStartups; // Per student
    private int gateRegistrations;
    private int gateClearances;
    private int placementAbove7L;
    private int placement6to7L;
    private int placement5to6L;
    private int placement4to5L;
    private int placement3to4L;
    private int placement2_4to3L;
    private int placement1_8to2_4L;
    private int examinationResults; // 100% or 90%
    private int professionalMemberships;
    private double codingPlatformScore; // Average score
    private boolean isComputingBranch;

    // PART B: Research & Development [40 Marks]
    private int publications; // Total publications
    private int patents; // Total patents
    private int consultancyProjects; // Number of consultancy projects
    private int citations; // Total citations
    private int phdGuidance; // PhD guidance count
    private int bookPublications; // Total book publications

    // PART C: Academic Extension [20 Marks]
    private int guestLecturesDelivered; // Total guest lectures delivered
    private int certifications; // Total certifications / MOOCs
    private int eventsOrganized; // Total events organized
    private boolean industryCollaborations; // 0 or 1 for industry collaborations

    // Getters and Setters
    public double getAcademicResults() { return academicResults; }
    public void setAcademicResults(double academicResults) { this.academicResults = academicResults; }

    public boolean isHasMinSubjectResult() { return hasMinSubjectResult; }
    public void setHasMinSubjectResult(boolean hasMinSubjectResult) { this.hasMinSubjectResult = hasMinSubjectResult; }

    public int getMiniProjectCount() { return miniProjectCount; }
    public void setMiniProjectCount(int miniProjectCount) { this.miniProjectCount = miniProjectCount; }

    public double getStudentFeedback() { return studentFeedback; }
    public void setStudentFeedback(double studentFeedback) { this.studentFeedback = studentFeedback; }

    public double getHodFeedback() { return hodFeedback; }
    public void setHodFeedback(double hodFeedback) { this.hodFeedback = hodFeedback; }

    public int getInnovations() { return innovations; }
    public void setInnovations(int innovations) { this.innovations = innovations; }

    public int getMentorshipPoints() { return mentorshipPoints; }
    public void setMentorshipPoints(int mentorshipPoints) { this.mentorshipPoints = mentorshipPoints; }

    public int getProductDevelopment() { return productDevelopment; }
    public void setProductDevelopment(int productDevelopment) { this.productDevelopment = productDevelopment; }

    public int getSeminarParticipation() { return seminarParticipation; }
    public void setSeminarParticipation(int seminarParticipation) { this.seminarParticipation = seminarParticipation; }

    public int getSeminarPrizes() { return seminarPrizes; }
    public void setSeminarPrizes(int seminarPrizes) { this.seminarPrizes = seminarPrizes; }

    public int getWorkshopParticipation() { return workshopParticipation; }
    public void setWorkshopParticipation(int workshopParticipation) { this.workshopParticipation = workshopParticipation; }

    public int getWorkshopPrizes() { return workshopPrizes; }
    public void setWorkshopPrizes(int workshopPrizes) { this.workshopPrizes = workshopPrizes; }

    public int getProjectCompetitionParticipation() { return projectCompetitionParticipation; }
    public void setProjectCompetitionParticipation(int projectCompetitionParticipation) { this.projectCompetitionParticipation = projectCompetitionParticipation; }

    public int getProjectCompetitionPrizes() { return projectCompetitionPrizes; }
    public void setProjectCompetitionPrizes(int projectCompetitionPrizes) { this.projectCompetitionPrizes = projectCompetitionPrizes; }

    public int getLanguageCertifications() { return languageCertifications; }
    public void setLanguageCertifications(int languageCertifications) { this.languageCertifications = languageCertifications; }

    public int getOnlineCertifications() { return onlineCertifications; }
    public void setOnlineCertifications(int onlineCertifications) { this.onlineCertifications = onlineCertifications; }

    public int getInternships() { return internships; }
    public void setInternships(int internships) { this.internships = internships; }

    public int getSpecialAwards() { return specialAwards; }
    public void setSpecialAwards(int specialAwards) { this.specialAwards = specialAwards; }

    public int getStudentStartups() { return studentStartups; }
    public void setStudentStartups(int studentStartups) { this.studentStartups = studentStartups; }

    public int getGateRegistrations() { return gateRegistrations; }
    public void setGateRegistrations(int gateRegistrations) { this.gateRegistrations = gateRegistrations; }

    public int getGateClearances() { return gateClearances; }
    public void setGateClearances(int gateClearances) { this.gateClearances = gateClearances; }

    public int getPlacementAbove7L() { return placementAbove7L; }
    public void setPlacementAbove7L(int placementAbove7L) { this.placementAbove7L = placementAbove7L; }

    public int getPlacement6to7L() { return placement6to7L; }
    public void setPlacement6to7L(int placement6to7L) { this.placement6to7L = placement6to7L; }

    public int getPlacement5to6L() { return placement5to6L; }
    public void setPlacement5to6L(int placement5to6L) { this.placement5to6L = placement5to6L; }

    public int getPlacement4to5L() { return placement4to5L; }
    public void setPlacement4to5L(int placement4to5L) { this.placement4to5L = placement4to5L; }

    public int getPlacement3to4L() { return placement3to4L; }
    public void setPlacement3to4L(int placement3to4L) { this.placement3to4L = placement3to4L; }

    public int getPlacement2_4to3L() { return placement2_4to3L; }
    public void setPlacement2_4to3L(int placement2_4to3L) { this.placement2_4to3L = placement2_4to3L; }

    public int getPlacement1_8to2_4L() { return placement1_8to2_4L; }
    public void setPlacement1_8to2_4L(int placement1_8to2_4L) { this.placement1_8to2_4L = placement1_8to2_4L; }

    public int getExaminationResults() { return examinationResults; }
    public void setExaminationResults(int examinationResults) { this.examinationResults = examinationResults; }

    public int getProfessionalMemberships() { return professionalMemberships; }
    public void setProfessionalMemberships(int professionalMemberships) { this.professionalMemberships = professionalMemberships; }

    public double getCodingPlatformScore() { return codingPlatformScore; }
    public void setCodingPlatformScore(double codingPlatformScore) { this.codingPlatformScore = codingPlatformScore; }

    public boolean isComputingBranch() { return isComputingBranch; }
    public void setComputingBranch(boolean computingBranch) { isComputingBranch = computingBranch; }

    public int getPublications() { return publications; }
    public void setPublications(int publications) { this.publications = publications; }

    public int getPatents() { return patents; }
    public void setPatents(int patents) { this.patents = patents; }

    public int getConsultancyProjects() { return consultancyProjects; }
    public void setConsultancyProjects(int consultancyProjects) { this.consultancyProjects = consultancyProjects; }

    public int getCitations() { return citations; }
    public void setCitations(int citations) { this.citations = citations; }

    public int getPhdGuidance() { return phdGuidance; }
    public void setPhdGuidance(int phdGuidance) { this.phdGuidance = phdGuidance; }

    public int getBookPublications() { return bookPublications; }
    public void setBookPublications(int bookPublications) { this.bookPublications = bookPublications; }

    public int getGuestLecturesDelivered() { return guestLecturesDelivered; }
    public void setGuestLecturesDelivered(int guestLecturesDelivered) { this.guestLecturesDelivered = guestLecturesDelivered; }

    public int getCertifications() { return certifications; }
    public void setCertifications(int certifications) { this.certifications = certifications; }

    public int getEventsOrganized() { return eventsOrganized; }
    public void setEventsOrganized(int eventsOrganized) { this.eventsOrganized = eventsOrganized; }

    public boolean isIndustryCollaborations() { return industryCollaborations; }
    public void setIndustryCollaborations(boolean industryCollaborations) { this.industryCollaborations = industryCollaborations; }
}
