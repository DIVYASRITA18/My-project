package com.mkumarasamy.facultyappraisal;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mkumarasamy.facultyappraisal.model.AppraisalData;
import com.mkumarasamy.facultyappraisal.model.Faculty;
import com.mkumarasamy.facultyappraisal.model.FacultyLevel;
import com.mkumarasamy.facultyappraisal.model.SubmittedAppraisal;
import com.mkumarasamy.facultyappraisal.model.User;
import com.mkumarasamy.facultyappraisal.service.AppraisalService;

public class FacultyAppraisalAWT extends Frame implements ActionListener {
    private AppraisalService appraisalService = new AppraisalService();
    private List<User> users = new ArrayList<>();
    private List<SubmittedAppraisal> submittedAppraisals = new ArrayList<>();
    private Map<String, Faculty> facultyProfiles = new HashMap<>();
    private User currentUser;
    private Faculty currentFaculty;
    private AppraisalData currentAppraisalData;

    // Login components
    private TextField usernameField;
    private TextField passwordField;
    private Choice roleChoice;
    private Button loginButton;
    private Button registerButton;

    // Faculty Details
    private TextField nameField;
    private Choice levelChoice;
    private Checkbox phdCheckbox;

    // Academic Performance Fields (PART A)
    private TextField academicResultsField;
    private TextField miniProjectCountField;
    private TextField studentFeedbackField;
    private TextField hodFeedbackField;
    private TextField innovationsField;
    private TextField mentorshipPointsField;

    // Research & Development Fields (PART B)
    private TextField publicationsField;
    private TextField patentsField;
    private TextField consultancyProjectsField;
    private TextField citationsField;
    private TextField phdGuidanceField;
    private TextField bookPublicationsField;

    // Academic Extension Fields (PART C)
    private TextField guestLecturesDeliveredField;
    private TextField certificationsField;
    private TextField eventsOrganizedField;
    private Checkbox industryCollaborationsCheckbox;

    private Button calculateButton;

    public FacultyAppraisalAWT() {
        initializeUsers();
        // Reset user state on application startup
        currentUser = null;
        currentFaculty = null;
        facultyProfiles = new HashMap<>();
        showLoginScreen();
    }

    private void initializeUsers() {
        // Only add the admin user by default; faculty users must register
        users.add(new User("admin", "admin", "admin"));
    }

    private void showLoginScreen() {
        setTitle("Faculty Appraisal System - Login");
        setSize(400, 300);
        setLayout(new GridBagLayout());
        setBackground(new Color(102, 126, 234)); // Start of gradient
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(new Label("Faculty Appraisal System Login"), gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        add(new Label("Username:"), gbc);
        usernameField = new TextField(20);
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new Label("Password:"), gbc);
        passwordField = new TextField(20);
        passwordField.setEchoChar('*');
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new Label("Role:"), gbc);
        roleChoice = new Choice();
        roleChoice.add("faculty");
        roleChoice.add("admin");
        gbc.gridx = 1;
        add(roleChoice, gbc);

        loginButton = new Button("Login");
        loginButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(loginButton, gbc);

        registerButton = new Button("Register");
        registerButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(registerButton, gbc);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

    private void showFacultyForm() {
        removeAll();
        setTitle("Faculty Appraisal Dashboard");
        setSize(1200, 800);
        setLayout(new BorderLayout());

        // Modern gradient background
        setBackground(new Color(102, 126, 234));

        // Header Panel with modern styling
        Panel headerPanel = new Panel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(255, 255, 255, 240)); // Semi-transparent white
        headerPanel.setPreferredSize(new Dimension(1200, 80));

        Label titleLabel = new Label("Faculty Appraisal System", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(102, 126, 234));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);



        // Main Content Panel with centered dashboard cards
        Panel mainPanel = new Panel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        // Center panel for single square box
        Panel centerPanel = new Panel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(new Color(240, 248, 255));

        // Single large square dashboard box
        Panel mainBox = createSingleSquareBox();
        centerPanel.add(mainBox);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(mainPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Footer with Logout
        Panel footerPanel = new Panel();
        footerPanel.setLayout(new FlowLayout());
        footerPanel.setBackground(new Color(255, 255, 255, 240));
        Button logoutButton = new Button("Logout");
        logoutButton.addActionListener(this);
        logoutButton.setBackground(new Color(105, 105, 105));
        logoutButton.setForeground(Color.WHITE);
        footerPanel.add(logoutButton);
        add(footerPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private Panel createCard(String title, Color headerColor) {
        Panel card = new Panel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);

        // Header panel with improved styling
        Panel headerPanel = new Panel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(headerColor);
        headerPanel.setPreferredSize(new Dimension(1200, 30));
        Label titleLabel = new Label(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        card.add(headerPanel, BorderLayout.NORTH);

        // Content panel with padding
        Panel contentPanel = new Panel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(new Color(250, 250, 250)); // Light gray background
        card.add(contentPanel, BorderLayout.CENTER);

        return card;
    }

    private Panel createCompactCard(String title, Color headerColor) {
        Panel card = new Panel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);

        // Compact header panel
        Panel headerPanel = new Panel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(headerColor);
        headerPanel.setPreferredSize(new Dimension(1200, 25)); // Reduced height
        Label titleLabel = new Label(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Smaller font
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        card.add(headerPanel, BorderLayout.NORTH);

        // Compact content panel
        Panel contentPanel = new Panel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(new Color(250, 250, 250));
        card.add(contentPanel, BorderLayout.CENTER);

        return card;
    }

    private Panel createWebStyleSection(String title, Color headerColor) {
        Panel section = new Panel();
        section.setLayout(new BorderLayout());
        section.setBackground(Color.WHITE);

        // Modern section header with emoji and styling
        Panel headerPanel = new Panel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(headerColor);
        headerPanel.setPreferredSize(new Dimension(1100, 45));
        Label titleLabel = new Label(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        section.add(headerPanel, BorderLayout.NORTH);

        // Content panel with website-style padding
        Panel contentPanel = new Panel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(new Color(248, 249, 250)); // Light gray like websites
        section.add(contentPanel, BorderLayout.CENTER);

        return section;
    }

    private void addWebField(Panel section, GridBagConstraints gbc, String label, Component component, int row) {
        Panel contentPanel = (Panel) section.getComponent(1); // Get content panel
        if (contentPanel.getLayout() == null) {
            contentPanel.setLayout(new GridBagLayout());
        }

        // Label with modern styling
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        Label fieldLabel = new Label(label);
        fieldLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPanel.add(fieldLabel, gbc);

        // Input field with modern styling
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        if (component instanceof TextField) {
            ((TextField) component).setFont(new Font("Arial", Font.PLAIN, 14));
            ((TextField) component).setBackground(Color.WHITE);
        } else if (component instanceof Choice) {
            ((Choice) component).setFont(new Font("Arial", Font.PLAIN, 14));
            ((Choice) component).setBackground(Color.WHITE);
        } else if (component instanceof Checkbox) {
            ((Checkbox) component).setFont(new Font("Arial", Font.PLAIN, 14));
        }
        contentPanel.add(component, gbc);
    }

    private Panel createModernCard(String title, Color headerColor) {
        Panel card = new Panel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);

        // Modern header with gradient effect
        Panel header = new Panel();
        header.setBackground(headerColor);
        header.setPreferredSize(new Dimension(300, 40));
        Label titleLabel = new Label(title, Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);
        header.add(titleLabel);
        card.add(header, BorderLayout.NORTH);

        // Content area with padding
        Panel content = new Panel();
        content.setBackground(new Color(248, 249, 250));
        card.add(content, BorderLayout.CENTER);

        return card;
    }

    private void addFieldToCard(Panel card, GridBagConstraints gbc, String label, Component component, int row) {
        Panel contentPanel = (Panel) card.getComponent(1); // Get the content panel
        if (contentPanel.getLayout() == null) {
            contentPanel.setLayout(new GridBagLayout());
        }
        gbc.gridx = 0;
        gbc.gridy = row;
        contentPanel.add(new Label(label), gbc);
        gbc.gridx = 1;
        contentPanel.add(component, gbc);
    }

    private Panel createSingleSquareBox() {
        Panel mainBox = new Panel();
        mainBox.setLayout(new BorderLayout());
        mainBox.setBackground(Color.WHITE);

        // Create a large square box with border
        Panel borderPanel = new Panel();
        borderPanel.setLayout(new BorderLayout());
        borderPanel.setBackground(Color.BLACK);
        borderPanel.setPreferredSize(new Dimension(600, 600));

        Panel innerPanel = new Panel();
        innerPanel.setLayout(new BorderLayout());
        innerPanel.setBackground(Color.WHITE);
        innerPanel.setPreferredSize(new Dimension(580, 580));

        // Header with title
        Panel headerPanel = new Panel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(580, 80));

        Label titleLabel = new Label("Faculty Appraisal Dashboard", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        innerPanel.add(headerPanel, BorderLayout.NORTH);

        // Content area with buttons
        Panel contentPanel = new Panel();
        contentPanel.setLayout(new GridLayout(4, 1, 20, 20));
        contentPanel.setBackground(new Color(248, 249, 250));

        // Create/Update Profile Button
        Button profileBtn = new Button("👤 Create/Update Profile");
        profileBtn.setBackground(new Color(52, 152, 219));
        profileBtn.setForeground(Color.WHITE);
        profileBtn.setFont(new Font("Arial", Font.BOLD, 16));
        profileBtn.addActionListener(e -> {
            if (currentFaculty == null) {
                showCreateProfileDialog();
            } else {
                showProfilePanel();
            }
        });
        contentPanel.add(profileBtn);

        // Submit Appraisal Button
        Button submitBtn = new Button("📝 Submit Appraisal");
        submitBtn.setBackground(new Color(155, 89, 182));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        submitBtn.addActionListener(e -> {
            if (currentFaculty == null) {
                Dialog warningDialog = new Dialog(this, "Profile Required", true);
                warningDialog.setLayout(new FlowLayout());
                warningDialog.add(new Label("Please create your profile first before submitting appraisal."));
                Button okBtn = new Button("OK");
                okBtn.addActionListener(ae -> warningDialog.setVisible(false));
                warningDialog.add(okBtn);
                warningDialog.setSize(300, 100);
                warningDialog.setVisible(true);
            } else {
                showAppraisalForm();
            }
        });
        contentPanel.add(submitBtn);

        // View Progress Button
        Button progressBtn = new Button("📊 View Progress");
        progressBtn.setBackground(new Color(46, 204, 113));
        progressBtn.setForeground(Color.WHITE);
        progressBtn.setFont(new Font("Arial", Font.BOLD, 16));
        progressBtn.addActionListener(e -> showProgressBoard());
        contentPanel.add(progressBtn);

        // Admin Review Status Button
        Button adminStatusBtn = new Button("👨‍💼 Admin Review Status");
        adminStatusBtn.setBackground(new Color(231, 76, 60));
        adminStatusBtn.setForeground(Color.WHITE);
        adminStatusBtn.setFont(new Font("Arial", Font.BOLD, 16));
        adminStatusBtn.addActionListener(e -> showAdminReviewStatusDialog());
        contentPanel.add(adminStatusBtn);

        innerPanel.add(contentPanel, BorderLayout.CENTER);

        borderPanel.add(innerPanel, BorderLayout.CENTER);
        mainBox.add(borderPanel, BorderLayout.CENTER);

        return mainBox;
    }

    private Panel createDashboardCard(String title, String description, Color headerColor) {
        Panel card = new Panel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);

        // Website-like card with subtle shadow effect
        Panel shadowPanel = new Panel();
        shadowPanel.setLayout(new BorderLayout());
        shadowPanel.setBackground(new Color(220, 220, 220)); // Light shadow
        shadowPanel.setPreferredSize(new Dimension(420, 320));

        Panel mainCardPanel = new Panel();
        mainCardPanel.setLayout(new BorderLayout());
        mainCardPanel.setBackground(Color.WHITE);
        mainCardPanel.setPreferredSize(new Dimension(400, 300));

        // Header panel with title - website style
        Panel headerPanel = new Panel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(headerColor);
        headerPanel.setPreferredSize(new Dimension(400, 80));

        Label titleLabel = new Label(title, Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        mainCardPanel.add(headerPanel, BorderLayout.NORTH);

        // Content panel with description - website style
        Panel contentPanel = new Panel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(248, 249, 250));

        Label descLabel = new Label("<html><center>" + description + "</center></html>", Label.CENTER);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descLabel.setForeground(new Color(108, 117, 125));
        contentPanel.add(descLabel, BorderLayout.CENTER);

        mainCardPanel.add(contentPanel, BorderLayout.CENTER);

        shadowPanel.add(mainCardPanel, BorderLayout.CENTER);
        card.add(shadowPanel, BorderLayout.CENTER);

        return card;
    }

    private void showAppraisalForm() {
        removeAll();
        setTitle("Submit Appraisal - Faculty Appraisal System");
        setSize(1200, 800);
        setLayout(new BorderLayout());

        // Modern website-style header
        Panel headerPanel = new Panel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219)); // Modern blue
        headerPanel.setPreferredSize(new Dimension(1200, 70));

        Label titleLabel = new Label("Faculty Appraisal System", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Main content with website-like form layout
        Panel mainPanel = new Panel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Form container with padding
        Panel formContainer = new Panel();
        formContainer.setLayout(new GridBagLayout());
        formContainer.setBackground(Color.WHITE);
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.insets = new Insets(20, 40, 20, 40); // Website-style padding
        mainGbc.fill = GridBagConstraints.HORIZONTAL;
        mainGbc.weightx = 1.0;

        // Section 1: Faculty Details
        Panel facultySection = createWebStyleSection("👤 Faculty Details", new Color(52, 152, 219));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;
        addWebField(facultySection, gbc, "Full Name:", nameField = new TextField(25), row++);
        addWebField(facultySection, gbc, "Faculty Level:", levelChoice = new Choice(), row++);
        levelChoice.add("PROFESSOR");
        levelChoice.add("ASSOCIATE_PROFESSOR");
        levelChoice.add("ASSISTANT_PROFESSOR_PHD");
        levelChoice.add("ASSISTANT_PROFESSOR_NON_PHD");
        addWebField(facultySection, gbc, "PhD Holder:", phdCheckbox = new Checkbox("Yes"), row++);
        mainGbc.gridy = 0;
        formContainer.add(facultySection, mainGbc);

        // Section 2: Academic Performance
        Panel academicSection = createWebStyleSection("📚 PART A: Academic Performance [40 Marks]", new Color(46, 204, 113));
        row = 0;
        addWebField(academicSection, gbc, "Academic Results (%):", academicResultsField = new TextField(20), row++);
        addWebField(academicSection, gbc, "Mini Projects Guided:", miniProjectCountField = new TextField(20), row++);
        addWebField(academicSection, gbc, "Student Feedback (%):", studentFeedbackField = new TextField(20), row++);
        addWebField(academicSection, gbc, "HoD Feedback (0-5):", hodFeedbackField = new TextField(20), row++);
        addWebField(academicSection, gbc, "Innovations Count:", innovationsField = new TextField(20), row++);
        addWebField(academicSection, gbc, "Mentorship Points:", mentorshipPointsField = new TextField(20), row++);
        mainGbc.gridy = 1;
        formContainer.add(academicSection, mainGbc);

        // Section 3: Research & Development
        Panel researchSection = createWebStyleSection("🔬 PART B: Research & Development [40 Marks]", new Color(155, 89, 182));
        row = 0;
        addWebField(researchSection, gbc, "Publications:", publicationsField = new TextField(20), row++);
        addWebField(researchSection, gbc, "Patents:", patentsField = new TextField(20), row++);
        addWebField(researchSection, gbc, "Consultancy Projects:", consultancyProjectsField = new TextField(20), row++);
        addWebField(researchSection, gbc, "Citations:", citationsField = new TextField(20), row++);
        addWebField(researchSection, gbc, "Ph.D. Guidance:", phdGuidanceField = new TextField(20), row++);
        addWebField(researchSection, gbc, "Book Publications:", bookPublicationsField = new TextField(20), row++);
        mainGbc.gridy = 2;
        formContainer.add(researchSection, mainGbc);

        // Section 4: Academic Extension
        Panel extensionSection = createWebStyleSection("🌟 PART C: Academic Extension [20 Marks]", new Color(230, 126, 34));
        row = 0;
        addWebField(extensionSection, gbc, "Guest Lectures Delivered:", guestLecturesDeliveredField = new TextField(20), row++);
        addWebField(extensionSection, gbc, "Certifications/MOOCs:", certificationsField = new TextField(20), row++);
        addWebField(extensionSection, gbc, "Events Organized:", eventsOrganizedField = new TextField(20), row++);
        addWebField(extensionSection, gbc, "Industry Collaborations:", industryCollaborationsCheckbox = new Checkbox("Yes"), row++);
        mainGbc.gridy = 3;
        formContainer.add(extensionSection, mainGbc);

        // Action buttons section
        Panel actionsSection = new Panel();
        actionsSection.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        actionsSection.setBackground(Color.WHITE);

        calculateButton = new Button("📊 Calculate Appraisal");
        calculateButton.addActionListener(this);
        calculateButton.setBackground(new Color(34, 139, 34));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
        actionsSection.add(calculateButton);

        Button backButton = new Button("🏠 Back to Dashboard");
        backButton.addActionListener(e -> showFacultyForm());
        backButton.setBackground(new Color(108, 117, 125));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        actionsSection.add(backButton);

        mainGbc.gridy = 4;
        formContainer.add(actionsSection, mainGbc);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(formContainer);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void showAdminPanel() {
        removeAll();
        setTitle("Admin Panel - Faculty Appraisal System");
        setSize(1200, 700);
        setLayout(new BorderLayout());
        setBackground(new Color(102, 126, 234)); // Start of gradient

        // Header Panel
        Panel mainHeaderPanel = new Panel();
        mainHeaderPanel.setLayout(new BorderLayout());
        mainHeaderPanel.setBackground(new Color(52, 152, 219));
        mainHeaderPanel.setPreferredSize(new Dimension(1200, 60));

        Label titleLabel = new Label("Admin Review Panel - Faculty Appraisals", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        mainHeaderPanel.add(titleLabel, BorderLayout.CENTER);
        add(mainHeaderPanel, BorderLayout.NORTH);

        // Main content panel
        Panel contentPanel = new Panel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        if (submittedAppraisals.isEmpty()) {
            Label noDataLabel = new Label("No appraisals submitted yet.", Label.CENTER);
            noDataLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            contentPanel.add(noDataLabel, BorderLayout.CENTER);
        } else {
            // Create a panel for the table with headers and data
            Panel tablePanel = new Panel();
            tablePanel.setLayout(new BorderLayout());
            tablePanel.setBackground(Color.white);

            // Create table header
            Panel tableHeaderPanel = new Panel();
            tableHeaderPanel.setLayout(new GridBagLayout());
            tableHeaderPanel.setBackground(new Color(52, 152, 219));
            GridBagConstraints headerGbc = new GridBagConstraints();
            headerGbc.fill = GridBagConstraints.BOTH;
            headerGbc.insets = new Insets(5, 5, 5, 5);

            // Header labels
            headerGbc.gridx = 0;
            headerGbc.weightx = 0.25;
            Label facultyHeader = new Label("👤 Faculty");
            facultyHeader.setFont(new Font("Arial", Font.BOLD, 12));
            facultyHeader.setForeground(Color.WHITE);
            tableHeaderPanel.add(facultyHeader, headerGbc);

            headerGbc.gridx = 1;
            headerGbc.weightx = 0.12;
            Label partAHeader = new Label("📚 Academic");
            partAHeader.setFont(new Font("Arial", Font.BOLD, 12));
            partAHeader.setForeground(Color.WHITE);
            tableHeaderPanel.add(partAHeader, headerGbc);

            headerGbc.gridx = 2;
            headerGbc.weightx = 0.12;
            Label partBHeader = new Label("🔬 Research");
            partBHeader.setFont(new Font("Arial", Font.BOLD, 12));
            partBHeader.setForeground(Color.WHITE);
            tableHeaderPanel.add(partBHeader, headerGbc);

            headerGbc.gridx = 3;
            headerGbc.weightx = 0.12;
            Label partCHeader = new Label("🌟 Extension");
            partCHeader.setFont(new Font("Arial", Font.BOLD, 12));
            partCHeader.setForeground(Color.WHITE);
            tableHeaderPanel.add(partCHeader, headerGbc);

            headerGbc.gridx = 4;
            headerGbc.weightx = 0.12;
            Label totalHeader = new Label("🎯 Total");
            totalHeader.setFont(new Font("Arial", Font.BOLD, 12));
            totalHeader.setForeground(Color.WHITE);
            tableHeaderPanel.add(totalHeader, headerGbc);

            headerGbc.gridx = 5;
            headerGbc.weightx = 0.15;
            Label statusHeader = new Label("📋 Status");
            statusHeader.setFont(new Font("Arial", Font.BOLD, 12));
            statusHeader.setForeground(Color.WHITE);
            tableHeaderPanel.add(statusHeader, headerGbc);

            headerGbc.gridx = 6;
            headerGbc.weightx = 0.22;
            Label actionsHeader = new Label("⚡ Actions");
            actionsHeader.setFont(new Font("Arial", Font.BOLD, 12));
            actionsHeader.setForeground(Color.WHITE);
            tableHeaderPanel.add(actionsHeader, headerGbc);

            tablePanel.add(tableHeaderPanel, BorderLayout.NORTH);

            // Create data rows
            Panel dataPanel = new Panel();
            dataPanel.setLayout(new GridLayout(submittedAppraisals.size(), 1, 0, 1));
            dataPanel.setBackground(Color.white);

            for (SubmittedAppraisal appraisal : submittedAppraisals) {
                Panel appraisalRow = createAppraisalRow(appraisal);
                dataPanel.add(appraisalRow);
            }

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.add(dataPanel);
            tablePanel.add(scrollPane, BorderLayout.CENTER);

            contentPanel.add(tablePanel, BorderLayout.CENTER);
        }

        add(contentPanel, BorderLayout.CENTER);

        // Footer with logout and scoreboard
        Panel footerPanel = new Panel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        footerPanel.setBackground(new Color(248, 249, 250));

        Button scoreboardButton = new Button("📊 Scoreboard");
        scoreboardButton.addActionListener(e -> showAdminScoreboard());
        scoreboardButton.setBackground(new Color(52, 152, 219));
        scoreboardButton.setForeground(Color.WHITE);
        scoreboardButton.setFont(new Font("Arial", Font.BOLD, 14));
        footerPanel.add(scoreboardButton);

        Button logoutButton = new Button("Logout");
        logoutButton.addActionListener(this);
        logoutButton.setBackground(new Color(108, 117, 125));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        footerPanel.add(logoutButton);

        add(footerPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private Panel createAppraisalRow(SubmittedAppraisal appraisal) {
        Panel rowPanel = new Panel();
        rowPanel.setLayout(new GridBagLayout());
        rowPanel.setBackground(new Color(248, 249, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Faculty (wider column)
        gbc.gridx = 0;
        gbc.weightx = 0.25;
        Label facultyLabel = new Label("👤 " + appraisal.getFaculty().getName());
        facultyLabel.setFont(new Font("Arial", Font.BOLD, 12));
        rowPanel.add(facultyLabel, gbc);

        // Part A (Academic Score)
        gbc.gridx = 1;
        gbc.weightx = 0.12;
        Label partALabel = new Label("📚 " + String.format("%.1f", appraisal.getAcademicScore()));
        partALabel.setFont(new Font("Arial", Font.PLAIN, 12));
        rowPanel.add(partALabel, gbc);

        // Part B (Research Score)
        gbc.gridx = 2;
        gbc.weightx = 0.12;
        Label partBLabel = new Label("🔬 " + String.format("%.1f", appraisal.getResearchScore()));
        partBLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        rowPanel.add(partBLabel, gbc);

        // Part C (Extension Score)
        gbc.gridx = 3;
        gbc.weightx = 0.12;
        Label partCLabel = new Label("🌟 " + String.format("%.1f", appraisal.getExtensionScore()));
        partCLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        rowPanel.add(partCLabel, gbc);

        // Total
        gbc.gridx = 4;
        gbc.weightx = 0.12;
        Label totalLabel = new Label("🎯 " + appraisal.getTotalScore());
        totalLabel.setFont(new Font("Arial", Font.BOLD, 12));
        rowPanel.add(totalLabel, gbc);

        // Status
        gbc.gridx = 5;
        gbc.weightx = 0.15;
        Label statusLabel = new Label("📋 " + getReviewStatus(appraisal));
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        statusLabel.setForeground(getStatusColor(appraisal));
        rowPanel.add(statusLabel, gbc);

        // Actions (buttons in a sub-panel, wider column)
        gbc.gridx = 6;
        gbc.weightx = 0.22;
        Panel actionsPanel = new Panel();
        actionsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 0));
        actionsPanel.setBackground(new Color(248, 249, 250));

        // View Details button
        Button detailsBtn = new Button("📄 View");
        detailsBtn.setBackground(new Color(52, 152, 219));
        detailsBtn.setForeground(Color.WHITE);
        detailsBtn.setFont(new Font("Arial", Font.PLAIN, 10));
        detailsBtn.addActionListener(e -> showAppraisalDetails(appraisal));
        actionsPanel.add(detailsBtn);

        // Comment button
        Button commentBtn = new Button("💬 Comment");
        commentBtn.setBackground(new Color(255, 193, 7));
        commentBtn.setForeground(Color.BLACK);
        commentBtn.setFont(new Font("Arial", Font.PLAIN, 10));
        commentBtn.addActionListener(e -> showAddCommentDialog(appraisal));
        actionsPanel.add(commentBtn);

        // Approve button
        Button approveBtn = new Button("✅ Approve");
        approveBtn.setBackground(new Color(34, 139, 34));
        approveBtn.setForeground(Color.WHITE);
        approveBtn.setFont(new Font("Arial", Font.PLAIN, 10));
        approveBtn.addActionListener(e -> {
            setReviewStatus(appraisal, "Approved");
            showAdminPanel(); // Refresh the panel
        });
        actionsPanel.add(approveBtn);

        // Reject button
        Button rejectBtn = new Button("❌ Reject");
        rejectBtn.setBackground(new Color(220, 53, 69));
        rejectBtn.setForeground(Color.WHITE);
        rejectBtn.setFont(new Font("Arial", Font.PLAIN, 10));
        rejectBtn.addActionListener(e -> {
            setReviewStatus(appraisal, "Rejected");
            showAdminPanel(); // Refresh the panel
        });
        actionsPanel.add(rejectBtn);

        rowPanel.add(actionsPanel, gbc);

        return rowPanel;
    }

    private Color getStatusColor(SubmittedAppraisal appraisal) {
        String status = getReviewStatus(appraisal);
        if ("Approved".equals(status)) return new Color(34, 139, 34);
        if ("Rejected".equals(status)) return new Color(220, 53, 69);
        return new Color(255, 193, 7); // Pending - orange
    }

    private void showAppraisalDetails(SubmittedAppraisal appraisal) {
        Dialog detailsDialog = new Dialog(this, "Appraisal Details - " + appraisal.getFaculty().getName(), true);
        detailsDialog.setLayout(new BorderLayout());
        detailsDialog.setSize(900, 600);
        detailsDialog.setBackground(Color.WHITE);

        // Header
        Panel headerPanel = new Panel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(900, 50));

        Label titleLabel = new Label("📄 Detailed Appraisal Submission", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        detailsDialog.add(headerPanel, BorderLayout.NORTH);

        // Content
        Panel contentPanel = new Panel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;

        // Faculty Information
        addDetailSection(contentPanel, gbc, "👤 Faculty Information", row++);
        addDetailField(contentPanel, gbc, "Name:", appraisal.getFaculty().getName(), row++);
        addDetailField(contentPanel, gbc, "Level:", appraisal.getFaculty().getLevel().toString(), row++);
        addDetailField(contentPanel, gbc, "PhD Holder:", appraisal.getFaculty().isPhdHolder() ? "Yes" : "No", row++);

        // Scores Summary
        addDetailSection(contentPanel, gbc, "📊 Scores Summary", row++);
        addDetailField(contentPanel, gbc, "Academic Performance:", String.format("%.1f", appraisal.getAcademicScore()), row++);
        addDetailField(contentPanel, gbc, "Research & Development:", String.format("%.1f", appraisal.getResearchScore()), row++);
        addDetailField(contentPanel, gbc, "Academic Extension:", String.format("%.1f", appraisal.getExtensionScore()), row++);
        addDetailField(contentPanel, gbc, "Total Score:", String.format("%.1f", appraisal.getTotalScore()), row++);
        addDetailField(contentPanel, gbc, "Eligibility:", appraisal.getEligibility(), row++);

        // Detailed Data (if available)
        AppraisalData data = appraisal.getAppraisalData();
        if (data != null) {
            addDetailSection(contentPanel, gbc, "📋 Detailed Academic Performance", row++);
            addDetailField(contentPanel, gbc, "Academic Results (%):", String.valueOf(data.getAcademicResults()), row++);
            addDetailField(contentPanel, gbc, "Mini Projects Guided:", String.valueOf(data.getMiniProjectCount()), row++);
            addDetailField(contentPanel, gbc, "Student Feedback (%):", String.valueOf(data.getStudentFeedback()), row++);
            addDetailField(contentPanel, gbc, "HoD Feedback:", String.valueOf(data.getHodFeedback()), row++);
            addDetailField(contentPanel, gbc, "Innovations:", String.valueOf(data.getInnovations()), row++);
            addDetailField(contentPanel, gbc, "Mentorship Points:", String.valueOf(data.getMentorshipPoints()), row++);

            addDetailSection(contentPanel, gbc, "🔬 Research & Development Details", row++);
            addDetailField(contentPanel, gbc, "Publications:", String.valueOf(data.getPublications()), row++);
            addDetailField(contentPanel, gbc, "Patents:", String.valueOf(data.getPatents()), row++);
            addDetailField(contentPanel, gbc, "Consultancy Projects:", String.valueOf(data.getConsultancyProjects()), row++);
            addDetailField(contentPanel, gbc, "Citations:", String.valueOf(data.getCitations()), row++);
            addDetailField(contentPanel, gbc, "Ph.D. Guidance:", String.valueOf(data.getPhdGuidance()), row++);
            addDetailField(contentPanel, gbc, "Book Publications:", String.valueOf(data.getBookPublications()), row++);

            addDetailSection(contentPanel, gbc, "🌟 Academic Extension Details", row++);
            addDetailField(contentPanel, gbc, "Guest Lectures:", String.valueOf(data.getGuestLecturesDelivered()), row++);
            addDetailField(contentPanel, gbc, "Certifications:", String.valueOf(data.getCertifications()), row++);
            addDetailField(contentPanel, gbc, "Events Organized:", String.valueOf(data.getEventsOrganized()), row++);
            addDetailField(contentPanel, gbc, "Industry Collaborations:", data.isIndustryCollaborations() ? "Yes" : "No", row++);
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(contentPanel);
        detailsDialog.add(scrollPane, BorderLayout.CENTER);

        // Footer
        Panel footerPanel = new Panel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(248, 249, 250));

        Button closeBtn = new Button("Close");
        closeBtn.addActionListener(e -> detailsDialog.setVisible(false));
        closeBtn.setBackground(new Color(108, 117, 125));
        closeBtn.setForeground(Color.WHITE);
        footerPanel.add(closeBtn);

        detailsDialog.add(footerPanel, BorderLayout.SOUTH);
        detailsDialog.setVisible(true);
    }

    private void addDetailSection(Panel panel, GridBagConstraints gbc, String title, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        Label sectionLabel = new Label(title);
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        sectionLabel.setForeground(new Color(52, 152, 219));
        panel.add(sectionLabel, gbc);
    }

    private void addDetailField(Panel panel, GridBagConstraints gbc, String label, String value, int row) {
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = row;
        Label fieldLabel = new Label(label);
        fieldLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(fieldLabel, gbc);

        gbc.gridx = 1;
        Label valueLabel = new Label(value);
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(valueLabel, gbc);
    }

    private String getReviewStatus(SubmittedAppraisal appraisal) {
        // For demo purposes, we'll use a simple approach
        // In a real app, this would be stored in the model
        return appraisal.getStatus().equals("Approved") ? "Approved" :
               appraisal.getStatus().equals("Rejected") ? "Rejected" : "Pending Review";
    }

    private void setReviewStatus(SubmittedAppraisal appraisal, String status) {
        // Update the appraisal status
        appraisal.setStatus(status);

        // Show confirmation dialog
        Dialog confirmDialog = new Dialog(this, "Action Confirmed", true);
        confirmDialog.setLayout(new FlowLayout());
        confirmDialog.add(new Label("Appraisal for " + appraisal.getFaculty().getName() + " has been " + status.toLowerCase() + "."));
        Button okBtn = new Button("OK");
        okBtn.addActionListener(e -> confirmDialog.setVisible(false));
        confirmDialog.add(okBtn);
        confirmDialog.setSize(300, 100);
        confirmDialog.setVisible(true);
    }

    private void showRegistrationDialog() {
        Dialog registerDialog = new Dialog(this, "Register New User", true);
        registerDialog.setLayout(new GridBagLayout());
        registerDialog.setBackground(new Color(102, 126, 234)); // Start of gradient
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        registerDialog.add(new Label("User Registration"), gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        registerDialog.add(new Label("Username:"), gbc);
        TextField regUsernameField = new TextField(20);
        gbc.gridx = 1;
        registerDialog.add(regUsernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        registerDialog.add(new Label("Password:"), gbc);
        TextField regPasswordField = new TextField(20);
        regPasswordField.setEchoChar('*');
        gbc.gridx = 1;
        registerDialog.add(regPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        registerDialog.add(new Label("Confirm Password:"), gbc);
        TextField regConfirmPasswordField = new TextField(20);
        regConfirmPasswordField.setEchoChar('*');
        gbc.gridx = 1;
        registerDialog.add(regConfirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        registerDialog.add(new Label("Role:"), gbc);
        Choice regRoleChoice = new Choice();
        regRoleChoice.add("faculty");
        regRoleChoice.add("admin");
        gbc.gridx = 1;
        registerDialog.add(regRoleChoice, gbc);

        Button regSubmitButton = new Button("Register");
        regSubmitButton.addActionListener(ae -> {
            String username = regUsernameField.getText().trim();
            String password = regPasswordField.getText().trim();
            String confirmPassword = regConfirmPasswordField.getText().trim();
            String role = regRoleChoice.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                Dialog errorDialog = new Dialog(registerDialog, "Error", true);
                errorDialog.setLayout(new FlowLayout());
                errorDialog.add(new Label("Username and password cannot be empty"));
                Button okButton = new Button("OK");
                okButton.addActionListener(ae2 -> errorDialog.setVisible(false));
                errorDialog.add(okButton);
                errorDialog.setSize(250, 100);
                errorDialog.setVisible(true);
                return;
            }

            if (!password.equals(confirmPassword)) {
                Dialog errorDialog = new Dialog(registerDialog, "Error", true);
                errorDialog.setLayout(new FlowLayout());
                errorDialog.add(new Label("Passwords do not match"));
                Button okButton = new Button("OK");
                okButton.addActionListener(ae2 -> errorDialog.setVisible(false));
                errorDialog.add(okButton);
                errorDialog.setSize(200, 100);
                errorDialog.setVisible(true);
                return;
            }

            // Check if username already exists
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    Dialog errorDialog = new Dialog(registerDialog, "Error", true);
                    errorDialog.setLayout(new FlowLayout());
                    errorDialog.add(new Label("Username already exists"));
                    Button okButton = new Button("OK");
                    okButton.addActionListener(ae2 -> errorDialog.setVisible(false));
                    errorDialog.add(okButton);
                    errorDialog.setSize(200, 100);
                    errorDialog.setVisible(true);
                    return;
                }
            }

            // Check if trying to register another admin (only one admin allowed)
            if ("admin".equals(role)) {
                boolean adminExists = users.stream().anyMatch(u -> "admin".equals(u.getRole()));
                if (adminExists) {
                    Dialog errorDialog = new Dialog(registerDialog, "Error", true);
                    errorDialog.setLayout(new FlowLayout());
                    errorDialog.add(new Label("Only one admin account is allowed"));
                    Button okButton = new Button("OK");
                    okButton.addActionListener(ae2 -> errorDialog.setVisible(false));
                    errorDialog.add(okButton);
                    errorDialog.setSize(250, 100);
                    errorDialog.setVisible(true);
                    return;
                }
            }

            // Register new user
            users.add(new User(username, password, role));
            // Initialize empty faculty profile for faculty users
            if ("faculty".equals(role)) {
                facultyProfiles.put(username, null); // Will be created when they first create profile
            }
            Dialog successDialog = new Dialog(registerDialog, "Success", true);
            successDialog.setLayout(new FlowLayout());
            successDialog.add(new Label("Registration successful! You can now login."));
            Button okButton = new Button("OK");
            okButton.addActionListener(ae2 -> successDialog.setVisible(false));
            successDialog.add(okButton);
            successDialog.setSize(300, 100);
            successDialog.setVisible(true);
            registerDialog.setVisible(false);
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        registerDialog.add(regSubmitButton, gbc);

        registerDialog.setSize(400, 300);
        registerDialog.setVisible(true);
    }

    private void addField(Panel panel, GridBagConstraints gbc, String label, Component component, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new Label(label), gbc);
        gbc.gridx = 1;
        panel.add(component, gbc);
    }

    private void showProfilePanel() {
        Dialog profileDialog = new Dialog(this, "👤 Faculty Profile - Complete Details", true);
        profileDialog.setLayout(new BorderLayout());
        profileDialog.setSize(1000, 800);
        profileDialog.setBackground(Color.WHITE);

        // Modern header
        Panel headerPanel = new Panel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(1000, 60));

        Label titleLabel = new Label("👤 Faculty Profile Information", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        profileDialog.add(headerPanel, BorderLayout.NORTH);

        // Main content panel
        Panel mainPanel = new Panel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Form container with website-style layout
        Panel formContainer = new Panel();
        formContainer.setLayout(new GridBagLayout());
        formContainer.setBackground(Color.WHITE);
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.insets = new Insets(20, 30, 20, 30);
        mainGbc.fill = GridBagConstraints.HORIZONTAL;
        mainGbc.weightx = 1.0;

        // Personal Information Section
        Panel personalSection = createWebStyleSection("👨‍🏫 Personal Information", new Color(52, 152, 219));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;

        addProfileField(personalSection, gbc, "Full Name:", currentFaculty != null ? currentFaculty.getName() : "Not set", row++);
        addProfileField(personalSection, gbc, "Employee ID:", currentFaculty != null ? currentFaculty.getEmployeeId() : "Not set", row++);
        addProfileField(personalSection, gbc, "Email Address:", currentFaculty != null ? currentFaculty.getEmail() : "Not set", row++);
        addProfileField(personalSection, gbc, "Phone Number:", currentFaculty != null ? currentFaculty.getPhone() : "Not set", row++);
        addProfileField(personalSection, gbc, "Department:", currentFaculty != null ? currentFaculty.getDepartment() : "Not set", row++);
        addProfileField(personalSection, gbc, "Date of Joining:", currentFaculty != null ? currentFaculty.getDateOfJoining() : "Not set", row++);
        mainGbc.gridy = 0;
        formContainer.add(personalSection, mainGbc);

        // Academic Information Section
        Panel academicSection = createWebStyleSection("🎓 Academic Information", new Color(46, 204, 113));
        row = 0;
        addProfileField(academicSection, gbc, "Faculty Level:", currentFaculty != null ? currentFaculty.getLevel().toString() : "Not set", row++);
        addProfileField(academicSection, gbc, "PhD Holder:", currentFaculty != null ? (currentFaculty.isPhdHolder() ? "Yes" : "No") : "Not set", row++);
        mainGbc.gridy = 1;
        formContainer.add(academicSection, mainGbc);



        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(formContainer);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        profileDialog.add(mainPanel, BorderLayout.CENTER);

        // Action buttons
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(Color.WHITE);

        Button editBtn = new Button("✏️ Edit Profile");
        editBtn.setBackground(new Color(52, 152, 219));
        editBtn.setForeground(Color.WHITE);
        editBtn.setFont(new Font("Arial", Font.BOLD, 14));
        editBtn.addActionListener(e -> showEditProfileDialog());
        buttonPanel.add(editBtn);

        Button closeBtn = new Button("❌ Close");
        closeBtn.setBackground(new Color(108, 117, 125));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFont(new Font("Arial", Font.BOLD, 14));
        closeBtn.addActionListener(e -> profileDialog.setVisible(false));
        buttonPanel.add(closeBtn);

        profileDialog.add(buttonPanel, BorderLayout.SOUTH);

        profileDialog.setVisible(true);
    }

    private void addProfileField(Panel section, GridBagConstraints gbc, String label, String value, int row) {
        Panel contentPanel = (Panel) section.getComponent(1);
        if (contentPanel.getLayout() == null) {
            contentPanel.setLayout(new GridBagLayout());
        }

        // Label with modern styling
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        Label fieldLabel = new Label(label);
        fieldLabel.setFont(new Font("Arial", Font.BOLD, 14));
        fieldLabel.setForeground(new Color(52, 73, 94));
        contentPanel.add(fieldLabel, gbc);

        // Value with modern styling
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        Label valueLabel = new Label(value);
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        valueLabel.setForeground(new Color(44, 62, 80));
        valueLabel.setBackground(new Color(248, 249, 250));
        contentPanel.add(valueLabel, gbc);
    }

    private void showEditProfileDialog() {
        Dialog editDialog = new Dialog(this, "✏️ Edit Faculty Profile", true);
        editDialog.setLayout(new BorderLayout());
        editDialog.setSize(800, 600);
        editDialog.setBackground(Color.WHITE);

        // Header
        Panel headerPanel = new Panel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(800, 50));

        Label titleLabel = new Label("Edit Profile Information", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        editDialog.add(headerPanel, BorderLayout.NORTH);

        // Form panel
        Panel formPanel = new Panel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;

        // Create input fields
        TextField nameField = new TextField(currentFaculty != null ? currentFaculty.getName() : "", 25);
        TextField employeeIdField = new TextField(currentFaculty != null ? currentFaculty.getEmployeeId() : "", 25);
        TextField emailField = new TextField(currentFaculty != null ? currentFaculty.getEmail() : "", 25);
        TextField phoneField = new TextField(currentFaculty != null ? currentFaculty.getPhone() : "", 25);
        TextField departmentField = new TextField(currentFaculty != null ? currentFaculty.getDepartment() : "", 25);
        TextField dateOfJoiningField = new TextField(currentFaculty != null ? currentFaculty.getDateOfJoining() : "", 25);

        addEditField(formPanel, gbc, "Full Name:", nameField, row++);
        addEditField(formPanel, gbc, "Employee ID:", employeeIdField, row++);
        addEditField(formPanel, gbc, "Email:", emailField, row++);
        addEditField(formPanel, gbc, "Phone:", phoneField, row++);
        addEditField(formPanel, gbc, "Department:", departmentField, row++);
        addEditField(formPanel, gbc, "Date of Joining (DD/MM/YYYY):", dateOfJoiningField, row++);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(formPanel);
        editDialog.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        Button saveBtn = new Button("💾 Save Changes");
        saveBtn.setBackground(new Color(34, 139, 34));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 14));
        saveBtn.addActionListener(e -> {
            if (currentFaculty != null) {
                currentFaculty.setName(nameField.getText());
                currentFaculty.setEmployeeId(employeeIdField.getText());
                currentFaculty.setEmail(emailField.getText());
                currentFaculty.setPhone(phoneField.getText());
                currentFaculty.setDepartment(departmentField.getText());
                currentFaculty.setDateOfJoining(dateOfJoiningField.getText());

                // Update the faculty profile in the map
                facultyProfiles.put(currentUser.getUsername(), currentFaculty);

                Dialog successDialog = new Dialog(editDialog, "Success", true);
                successDialog.setLayout(new FlowLayout());
                successDialog.add(new Label("Profile updated successfully!"));
                Button okBtn = new Button("OK");
                okBtn.addActionListener(ae -> successDialog.setVisible(false));
                successDialog.add(okBtn);
                successDialog.setSize(250, 100);
                successDialog.setVisible(true);
            }
            editDialog.setVisible(false);
        });
        buttonPanel.add(saveBtn);

        Button cancelBtn = new Button("❌ Cancel");
        cancelBtn.setBackground(new Color(108, 117, 125));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 14));
        cancelBtn.addActionListener(e -> editDialog.setVisible(false));
        buttonPanel.add(cancelBtn);

        editDialog.add(buttonPanel, BorderLayout.SOUTH);
        editDialog.setVisible(true);
    }

    private void addEditField(Panel panel, GridBagConstraints gbc, String label, TextField field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        Label fieldLabel = new Label(label);
        fieldLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(fieldLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(field, gbc);
    }

    private void showCreateProfileDialog() {
        Dialog createDialog = new Dialog(this, "👤 Create Faculty Profile", true);
        createDialog.setLayout(new BorderLayout());
        createDialog.setSize(800, 600);
        createDialog.setBackground(Color.WHITE);

        // Header
        Panel headerPanel = new Panel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(800, 50));

        Label titleLabel = new Label("Create Your Faculty Profile", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        createDialog.add(headerPanel, BorderLayout.NORTH);

        // Form panel
        Panel formPanel = new Panel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;

        // Create input fields
        TextField nameField = new TextField(25);
        TextField employeeIdField = new TextField(25);
        TextField emailField = new TextField(25);
        TextField phoneField = new TextField(25);
        TextField departmentField = new TextField(25);
        TextField dateOfJoiningField = new TextField(25);
        Choice levelChoice = new Choice();
        levelChoice.add("PROFESSOR");
        levelChoice.add("ASSOCIATE_PROFESSOR");
        levelChoice.add("ASSISTANT_PROFESSOR_PHD");
        levelChoice.add("ASSISTANT_PROFESSOR_NON_PHD");
        Checkbox phdCheckbox = new Checkbox("PhD Holder");

        addEditField(formPanel, gbc, "Full Name:", nameField, row++);
        addEditField(formPanel, gbc, "Employee ID:", employeeIdField, row++);
        addEditField(formPanel, gbc, "Email:", emailField, row++);
        addEditField(formPanel, gbc, "Phone:", phoneField, row++);
        addEditField(formPanel, gbc, "Department:", departmentField, row++);
        addEditField(formPanel, gbc, "Date of Joining (DD/MM/YYYY):", dateOfJoiningField, row++);

        // Faculty level and PhD
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new Label("Faculty Level:"), gbc);
        gbc.gridx = 1;
        levelChoice.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(levelChoice, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        phdCheckbox.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(phdCheckbox, gbc);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(formPanel);
        createDialog.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        Button saveBtn = new Button("💾 Create Profile");
        saveBtn.setBackground(new Color(34, 139, 34));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 14));
        saveBtn.addActionListener(e -> {
            if (nameField.getText().trim().isEmpty() || employeeIdField.getText().trim().isEmpty()) {
                Dialog errorDialog = new Dialog(createDialog, "Error", true);
                errorDialog.setLayout(new FlowLayout());
                errorDialog.add(new Label("Name and Employee ID are required!"));
                Button okBtn = new Button("OK");
                okBtn.addActionListener(ae -> errorDialog.setVisible(false));
                errorDialog.add(okBtn);
                errorDialog.setSize(250, 100);
                errorDialog.setVisible(true);
                return;
            }

            currentFaculty = new Faculty(
                nameField.getText(),
                FacultyLevel.valueOf(levelChoice.getSelectedItem()),
                phdCheckbox.getState(),
                emailField.getText(),
                departmentField.getText(),
                phoneField.getText(),
                dateOfJoiningField.getText(),
                employeeIdField.getText()
            );

            // Save the faculty profile for this user
            facultyProfiles.put(currentUser.getUsername(), currentFaculty);

            Dialog successDialog = new Dialog(createDialog, "Success", true);
            successDialog.setLayout(new FlowLayout());
            successDialog.add(new Label("Profile created successfully!"));
            Button okBtn = new Button("OK");
            okBtn.addActionListener(ae -> successDialog.setVisible(false));
            successDialog.add(okBtn);
            successDialog.setSize(250, 100);
            successDialog.setVisible(true);
            createDialog.setVisible(false);
        });
        buttonPanel.add(saveBtn);

        Button cancelBtn = new Button("❌ Cancel");
        cancelBtn.setBackground(new Color(108, 117, 125));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 14));
        cancelBtn.addActionListener(e -> createDialog.setVisible(false));
        buttonPanel.add(cancelBtn);

        createDialog.add(buttonPanel, BorderLayout.SOUTH);
        createDialog.setVisible(true);
    }

    private void showAdminReviewStatusDialog() {
        Dialog adminDialog = new Dialog(this, "👨‍💼 Admin Review Status", true);
        adminDialog.setLayout(new BorderLayout());
        adminDialog.setSize(800, 500);
        adminDialog.setBackground(Color.WHITE);

        // Header
        Panel headerPanel = new Panel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(231, 76, 60));
        headerPanel.setPreferredSize(new Dimension(800, 50));

        Label titleLabel = new Label("👨‍💼 Admin Review Status", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        adminDialog.add(headerPanel, BorderLayout.NORTH);

        // Content
        Panel contentPanel = new Panel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        if (currentFaculty == null) {
            Label noProfileLabel = new Label("Please create your profile first to view admin review status.", Label.CENTER);
            noProfileLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            contentPanel.add(noProfileLabel, BorderLayout.CENTER);
        } else {
            // Find the current faculty's appraisal
            SubmittedAppraisal facultyAppraisal = null;
            for (SubmittedAppraisal appraisal : submittedAppraisals) {
                if (appraisal.getFaculty().getName().equals(currentFaculty.getName())) {
                    facultyAppraisal = appraisal;
                    break;
                }
            }

            if (facultyAppraisal == null) {
                Label noAppraisalLabel = new Label("No appraisals submitted yet. Please submit an appraisal first.", Label.CENTER);
                noAppraisalLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                contentPanel.add(noAppraisalLabel, BorderLayout.CENTER);
            } else {
                // Display admin review status
                Panel statusPanel = new Panel();
                statusPanel.setLayout(new GridBagLayout());
                statusPanel.setBackground(Color.WHITE);
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(15, 20, 15, 20);
                gbc.anchor = GridBagConstraints.WEST;
                int row = 0;

                // Submission Date
                addDetailField(statusPanel, gbc, "Submission Date:", facultyAppraisal.getSubmissionDate(), row++);

                // Scores
                addDetailField(statusPanel, gbc, "Academic Score (Part A):", String.format("%.1f", facultyAppraisal.getAcademicScore()), row++);
                addDetailField(statusPanel, gbc, "Research Score (Part B):", String.format("%.1f", facultyAppraisal.getResearchScore()), row++);
                addDetailField(statusPanel, gbc, "Extension Score (Part C):", String.format("%.1f", facultyAppraisal.getExtensionScore()), row++);
                addDetailField(statusPanel, gbc, "Total Score:", String.format("%.1f", facultyAppraisal.getTotalScore()), row++);

                // Eligibility
                addDetailField(statusPanel, gbc, "Eligibility:", facultyAppraisal.getEligibility(), row++);

                // Admin Review Status
                String reviewStatus = getReviewStatus(facultyAppraisal);
                addDetailField(statusPanel, gbc, "Admin Review Status:", reviewStatus, row++);

                // Admin Comments - Faculty can view, only admins can add
                addDetailField(statusPanel, gbc, "Admin Comments:", getAdminComments(facultyAppraisal), row++);

                if (currentUser.getRole().equals("admin")) {
                    // Admin can add comments
                    Panel commentPanel = new Panel();
                    commentPanel.setLayout(new BorderLayout());
                    commentPanel.setBackground(Color.WHITE);
                    Label commentLabel = new Label("💬 Add Comment:");
                    commentLabel.setFont(new Font("Arial", Font.BOLD, 12));
                    TextField commentField = new TextField(30);
                    commentField.setFont(new Font("Arial", Font.PLAIN, 12));
                    Button submitCommentBtn = new Button("Submit Comment");
                    submitCommentBtn.setBackground(new Color(52, 152, 219));
                    submitCommentBtn.setForeground(Color.WHITE);
                    submitCommentBtn.setFont(new Font("Arial", Font.BOLD, 12));
                    final SubmittedAppraisal finalFacultyAppraisal = facultyAppraisal;
                    submitCommentBtn.addActionListener(e -> {
                        String comment = commentField.getText().trim();
                        if (!comment.isEmpty()) {
                            // Add comment to appraisal
                            finalFacultyAppraisal.setAdminComments(comment);
                            finalFacultyAppraisal.setAdminActionDate(new java.util.Date().toString());
                            Dialog commentDialog = new Dialog(this, "Comment Added", true);
                            commentDialog.setLayout(new FlowLayout());
                            commentDialog.add(new Label("Comment added successfully."));
                            Button okBtn = new Button("OK");
                            okBtn.addActionListener(ae -> commentDialog.setVisible(false));
                            commentDialog.add(okBtn);
                            commentDialog.setSize(300, 100);
                            commentDialog.setVisible(true);
                            commentField.setText("");
                            // Refresh the dialog
                            showAdminReviewStatusDialog();
                        }
                    });

                    Panel commentInputPanel = new Panel();
                    commentInputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                    commentInputPanel.add(commentLabel);
                    commentInputPanel.add(commentField);
                    commentInputPanel.add(submitCommentBtn);
                    commentPanel.add(commentInputPanel, BorderLayout.CENTER);

                    gbc.gridx = 0;
                    gbc.gridy = row++;
                    gbc.gridwidth = 2;
                    statusPanel.add(commentPanel, gbc);
                }

                // Action Date
                addDetailField(statusPanel, gbc, "Action Date:", getAdminActionDate(facultyAppraisal), row++);

                ScrollPane scrollPane = new ScrollPane();
                scrollPane.add(statusPanel);
                contentPanel.add(scrollPane, BorderLayout.CENTER);
            }
        }

        adminDialog.add(contentPanel, BorderLayout.CENTER);

        // Footer
        Panel footerPanel = new Panel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(248, 249, 250));

        Button closeBtn = new Button("Close");
        closeBtn.setBackground(new Color(108, 117, 125));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.addActionListener(e -> adminDialog.setVisible(false));
        footerPanel.add(closeBtn);

        adminDialog.add(footerPanel, BorderLayout.SOUTH);
        adminDialog.setVisible(true);
    }

    private void showAddCommentDialog(SubmittedAppraisal appraisal) {
        Dialog commentDialog = new Dialog(this, "💬 Add Comment - " + appraisal.getFaculty().getName(), true);
        commentDialog.setLayout(new BorderLayout());
        commentDialog.setSize(600, 300);
        commentDialog.setBackground(Color.WHITE);

        // Header
        Panel headerPanel = new Panel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(255, 193, 7));
        headerPanel.setPreferredSize(new Dimension(600, 50));

        Label titleLabel = new Label("💬 Add Admin Comment", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        commentDialog.add(headerPanel, BorderLayout.NORTH);

        // Content
        Panel contentPanel = new Panel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        Panel inputPanel = new Panel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Label commentLabel = new Label("Comment:");
        commentLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(commentLabel, gbc);

        TextField commentField = new TextField(50);
        commentField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(commentField, gbc);

        contentPanel.add(inputPanel, BorderLayout.CENTER);
        commentDialog.add(contentPanel, BorderLayout.CENTER);

        // Footer
        Panel footerPanel = new Panel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        footerPanel.setBackground(new Color(248, 249, 250));

        Button submitBtn = new Button("💾 Submit Comment");
        submitBtn.setBackground(new Color(34, 139, 34));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        submitBtn.addActionListener(e -> {
            String comment = commentField.getText().trim();
            if (!comment.isEmpty()) {
                appraisal.setAdminComments(comment);
                appraisal.setAdminActionDate(new java.util.Date().toString());
                Dialog successDialog = new Dialog(this, "Comment Added", true);
                successDialog.setLayout(new FlowLayout());
                successDialog.add(new Label("Comment added successfully."));
                Button okBtn = new Button("OK");
                okBtn.addActionListener(ae -> successDialog.setVisible(false));
                successDialog.add(okBtn);
                successDialog.setSize(300, 100);
                successDialog.setVisible(true);
                commentDialog.setVisible(false);
                showAdminPanel(); // Refresh the admin panel
            } else {
                Dialog errorDialog = new Dialog(this, "Error", true);
                errorDialog.setLayout(new FlowLayout());
                errorDialog.add(new Label("Comment cannot be empty."));
                Button okBtn = new Button("OK");
                okBtn.addActionListener(ae -> errorDialog.setVisible(false));
                errorDialog.add(okBtn);
                errorDialog.setSize(250, 100);
                errorDialog.setVisible(true);
            }
        });
        footerPanel.add(submitBtn);

        Button cancelBtn = new Button("❌ Cancel");
        cancelBtn.setBackground(new Color(108, 117, 125));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 14));
        cancelBtn.addActionListener(e -> commentDialog.setVisible(false));
        footerPanel.add(cancelBtn);

        commentDialog.add(footerPanel, BorderLayout.SOUTH);
        commentDialog.setVisible(true);
    }



    private void showAdminScoreboard() {
        Dialog scoreboardDialog = new Dialog(this, "📊 Admin Scoreboard", true);
        scoreboardDialog.setLayout(new BorderLayout());
        scoreboardDialog.setSize(1000, 700);
        scoreboardDialog.setBackground(Color.WHITE);

        // Header
        Panel headerPanel = new Panel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(1000, 60));

        Label titleLabel = new Label("📊 Faculty Appraisal Scoreboard", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        scoreboardDialog.add(headerPanel, BorderLayout.NORTH);

        // Main content
        Panel mainPanel = new Panel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Statistics section
        Panel statsPanel = new Panel();
        statsPanel.setLayout(new GridBagLayout());
        statsPanel.setBackground(new Color(248, 249, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;

        // Total appraisals
        addDetailField(statsPanel, gbc, "Total Appraisals Submitted:", String.valueOf(submittedAppraisals.size()), row++);

        // Approved/Rejected counts
        int approvedCount = 0;
        int rejectedCount = 0;
        int pendingCount = 0;
        for (SubmittedAppraisal appraisal : submittedAppraisals) {
            String status = getReviewStatus(appraisal);
            if ("Approved".equals(status)) approvedCount++;
            else if ("Rejected".equals(status)) rejectedCount++;
            else pendingCount++;
        }

        addDetailField(statsPanel, gbc, "Approved Appraisals:", String.valueOf(approvedCount), row++);
        addDetailField(statsPanel, gbc, "Rejected Appraisals:", String.valueOf(rejectedCount), row++);
        addDetailField(statsPanel, gbc, "Pending Review:", String.valueOf(pendingCount), row++);

        // Average scores
        if (!submittedAppraisals.isEmpty()) {
            double avgAcademic = submittedAppraisals.stream().mapToDouble(SubmittedAppraisal::getAcademicScore).average().orElse(0);
            double avgResearch = submittedAppraisals.stream().mapToDouble(SubmittedAppraisal::getResearchScore).average().orElse(0);
            double avgExtension = submittedAppraisals.stream().mapToDouble(SubmittedAppraisal::getExtensionScore).average().orElse(0);
            double avgTotal = submittedAppraisals.stream().mapToDouble(SubmittedAppraisal::getTotalScore).average().orElse(0);

            addDetailField(statsPanel, gbc, "Average Academic Score:", String.format("%.2f", avgAcademic), row++);
            addDetailField(statsPanel, gbc, "Average Research Score:", String.format("%.2f", avgResearch), row++);
            addDetailField(statsPanel, gbc, "Average Extension Score:", String.format("%.2f", avgExtension), row++);
            addDetailField(statsPanel, gbc, "Average Total Score:", String.format("%.2f", avgTotal), row++);
        }

        mainPanel.add(statsPanel, BorderLayout.NORTH);

        // Scoreboard table
        Panel tablePanel = new Panel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(Color.white);

        if (submittedAppraisals.isEmpty()) {
            Label noDataLabel = new Label("No appraisals submitted yet.", Label.CENTER);
            noDataLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            tablePanel.add(noDataLabel, BorderLayout.CENTER);
        } else {
            // Sort appraisals by total score descending
            submittedAppraisals.sort((a, b) -> Double.compare(b.getTotalScore(), a.getTotalScore()));

            // Create table header
            Panel tableHeaderPanel = new Panel();
            tableHeaderPanel.setLayout(new GridBagLayout());
            tableHeaderPanel.setBackground(new Color(52, 152, 219));
            GridBagConstraints headerGbc = new GridBagConstraints();
            headerGbc.fill = GridBagConstraints.BOTH;
            headerGbc.insets = new Insets(5, 5, 5, 5);

            // Header labels
            headerGbc.gridx = 0;
            headerGbc.weightx = 0.05;
            Label rankHeader = new Label("🏆 Rank");
            rankHeader.setFont(new Font("Arial", Font.BOLD, 12));
            rankHeader.setForeground(Color.WHITE);
            tableHeaderPanel.add(rankHeader, headerGbc);

            headerGbc.gridx = 1;
            headerGbc.weightx = 0.25;
            Label facultyHeader = new Label("👤 Faculty");
            facultyHeader.setFont(new Font("Arial", Font.BOLD, 12));
            facultyHeader.setForeground(Color.WHITE);
            tableHeaderPanel.add(facultyHeader, headerGbc);

            headerGbc.gridx = 2;
            headerGbc.weightx = 0.12;
            Label academicHeader = new Label("📚 Academic");
            academicHeader.setFont(new Font("Arial", Font.BOLD, 12));
            academicHeader.setForeground(Color.WHITE);
            tableHeaderPanel.add(academicHeader, headerGbc);

            headerGbc.gridx = 3;
            headerGbc.weightx = 0.12;
            Label researchHeader = new Label("🔬 Research");
            researchHeader.setFont(new Font("Arial", Font.BOLD, 12));
            researchHeader.setForeground(Color.WHITE);
            tableHeaderPanel.add(researchHeader, headerGbc);

            headerGbc.gridx = 4;
            headerGbc.weightx = 0.12;
            Label extensionHeader = new Label("🌟 Extension");
            extensionHeader.setFont(new Font("Arial", Font.BOLD, 12));
            extensionHeader.setForeground(Color.WHITE);
            tableHeaderPanel.add(extensionHeader, headerGbc);

            headerGbc.gridx = 5;
            headerGbc.weightx = 0.12;
            Label totalHeader = new Label("🎯 Total");
            totalHeader.setFont(new Font("Arial", Font.BOLD, 12));
            totalHeader.setForeground(Color.WHITE);
            tableHeaderPanel.add(totalHeader, headerGbc);

            headerGbc.gridx = 6;
            headerGbc.weightx = 0.15;
            Label statusHeader = new Label("📋 Status");
            statusHeader.setFont(new Font("Arial", Font.BOLD, 12));
            statusHeader.setForeground(Color.WHITE);
            tableHeaderPanel.add(statusHeader, headerGbc);

            tablePanel.add(tableHeaderPanel, BorderLayout.NORTH);

            // Create data rows
            Panel dataPanel = new Panel();
            dataPanel.setLayout(new GridLayout(submittedAppraisals.size(), 1, 0, 1));
            dataPanel.setBackground(Color.white);

            int rank = 1;
            for (SubmittedAppraisal appraisal : submittedAppraisals) {
                Panel rowPanel = new Panel();
                rowPanel.setLayout(new GridBagLayout());
                rowPanel.setBackground(rank % 2 == 0 ? new Color(248, 249, 250) : Color.white);
                GridBagConstraints rowGbc = new GridBagConstraints();
                rowGbc.fill = GridBagConstraints.BOTH;
                rowGbc.insets = new Insets(5, 5, 5, 5);

                // Rank
                rowGbc.gridx = 0;
                rowGbc.weightx = 0.05;
                Label rankLabel = new Label("🏆 " + rank);
                rankLabel.setFont(new Font("Arial", Font.BOLD, 12));
                rowPanel.add(rankLabel, rowGbc);

                // Faculty
                rowGbc.gridx = 1;
                rowGbc.weightx = 0.25;
                Label facultyLabel = new Label("👤 " + appraisal.getFaculty().getName());
                facultyLabel.setFont(new Font("Arial", Font.BOLD, 12));
                rowPanel.add(facultyLabel, rowGbc);

                // Academic Score
                rowGbc.gridx = 2;
                rowGbc.weightx = 0.12;
                Label academicLabel = new Label("📚 " + String.format("%.1f", appraisal.getAcademicScore()));
                academicLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                rowPanel.add(academicLabel, rowGbc);

                // Research Score
                rowGbc.gridx = 3;
                rowGbc.weightx = 0.12;
                Label researchLabel = new Label("🔬 " + String.format("%.1f", appraisal.getResearchScore()));
                researchLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                rowPanel.add(researchLabel, rowGbc);

                // Extension Score
                rowGbc.gridx = 4;
                rowGbc.weightx = 0.12;
                Label extensionLabel = new Label("🌟 " + String.format("%.1f", appraisal.getExtensionScore()));
                extensionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                rowPanel.add(extensionLabel, rowGbc);

                // Total Score
                rowGbc.gridx = 5;
                rowGbc.weightx = 0.12;
                Label totalLabel = new Label("🎯 " + appraisal.getTotalScore());
                totalLabel.setFont(new Font("Arial", Font.BOLD, 12));
                rowPanel.add(totalLabel, rowGbc);

                // Status
                rowGbc.gridx = 6;
                rowGbc.weightx = 0.15;
                Label statusLabel = new Label("📋 " + getReviewStatus(appraisal));
                statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
                statusLabel.setForeground(getStatusColor(appraisal));
                rowPanel.add(statusLabel, rowGbc);

                dataPanel.add(rowPanel);
                rank++;
            }

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.add(dataPanel);
            tablePanel.add(scrollPane, BorderLayout.CENTER);
        }

        mainPanel.add(tablePanel, BorderLayout.CENTER);
        scoreboardDialog.add(mainPanel, BorderLayout.CENTER);

        // Footer
        Panel footerPanel = new Panel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(248, 249, 250));

        Button closeBtn = new Button("Close");
        closeBtn.setBackground(new Color(108, 117, 125));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.addActionListener(e -> scoreboardDialog.setVisible(false));
        footerPanel.add(closeBtn);

        scoreboardDialog.add(footerPanel, BorderLayout.SOUTH);
        scoreboardDialog.setVisible(true);
    }

    private void showProgressBoard() {
        Dialog progressDialog = new Dialog(this, "📊 Faculty Progress Board", true);
        progressDialog.setLayout(new BorderLayout());
        progressDialog.setSize(1000, 700);
        progressDialog.setBackground(Color.WHITE);

        // Header
        Panel headerPanel = new Panel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(1000, 60));

        Label titleLabel = new Label("📊 Faculty Progress Dashboard", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        progressDialog.add(headerPanel, BorderLayout.NORTH);

        // Main content
        Panel mainPanel = new Panel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Progress sections
        Panel contentPanel = new Panel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.insets = new Insets(20, 30, 20, 30);
        mainGbc.fill = GridBagConstraints.HORIZONTAL;
        mainGbc.weightx = 1.0;

        // Profile Status Section
        Panel profileSection = createWebStyleSection("👤 Profile Status", new Color(52, 152, 219));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;

        String profileStatus = currentFaculty != null ? "✅ Profile Created" : "❌ Profile Not Created";
        addProgressField(profileSection, gbc, "Profile Status:", profileStatus, row++);
        if (currentFaculty != null) {
            addProgressField(profileSection, gbc, "Name:", currentFaculty.getName(), row++);
            addProgressField(profileSection, gbc, "Employee ID:", currentFaculty.getEmployeeId(), row++);
            addProgressField(profileSection, gbc, "Department:", currentFaculty.getDepartment(), row++);
        }
        mainGbc.gridy = 0;
        contentPanel.add(profileSection, mainGbc);

        // Appraisal Status Section
        Panel appraisalSection = createWebStyleSection("📝 Appraisal Status", new Color(155, 89, 182));
        row = 0;
        String appraisalStatus = submittedAppraisals.isEmpty() ? "❌ No Appraisals Submitted" : "✅ " + submittedAppraisals.size() + " Appraisal(s) Submitted";
        addProgressField(appraisalSection, gbc, "Submission Status:", appraisalStatus, row++);
        if (!submittedAppraisals.isEmpty()) {
            SubmittedAppraisal latest = submittedAppraisals.get(submittedAppraisals.size() - 1);
            addProgressField(appraisalSection, gbc, "Latest Score:", String.valueOf(latest.getTotalScore()), row++);
            addProgressField(appraisalSection, gbc, "Eligibility:", latest.getEligibility(), row++);
            addProgressField(appraisalSection, gbc, "Admin Review Status:", getReviewStatus(latest), row++);
        }
        mainGbc.gridy = 1;
        contentPanel.add(appraisalSection, mainGbc);

        // Progress Indicators Section
        Panel progressSection = createWebStyleSection("🎯 Progress Indicators", new Color(46, 204, 113));
        row = 0;
        String overallProgress = (currentFaculty != null && !submittedAppraisals.isEmpty()) ? "🎉 Complete Profile & Appraisal" :
                                (currentFaculty != null) ? "📝 Profile Complete - Submit Appraisal" : "👤 Create Profile First";
        addProgressField(progressSection, gbc, "Overall Progress:", overallProgress, row++);

        // Progress bars simulation with text
        addProgressField(progressSection, gbc, "Profile Completion:", currentFaculty != null ? "███████████████ 100%" : "░░░░░░░░░░░░░░░ 0%", row++);
        addProgressField(progressSection, gbc, "Appraisal Completion:", submittedAppraisals.isEmpty() ? "░░░░░░░░░░░░░░░ 0%" : "███████████████ 100%", row++);
        mainGbc.gridy = 2;
        contentPanel.add(progressSection, mainGbc);

        // Graph Status Section
        Panel graphSection = createWebStyleSection("📈 Graph Status", new Color(230, 126, 34));
        row = 0;
        addProgressField(graphSection, gbc, "Score Visualization:", "", row++);
        // Add simple graphical representation
        addGraphToSection(graphSection, gbc, row++);
        mainGbc.gridy = 3;
        contentPanel.add(graphSection, mainGbc);



        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(contentPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        progressDialog.add(mainPanel, BorderLayout.CENTER);

        // Action buttons
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(Color.WHITE);

        Button closeBtn = new Button("❌ Close");
        closeBtn.setBackground(new Color(108, 117, 125));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFont(new Font("Arial", Font.BOLD, 14));
        closeBtn.addActionListener(e -> progressDialog.setVisible(false));
        buttonPanel.add(closeBtn);

        progressDialog.add(buttonPanel, BorderLayout.SOUTH);
        progressDialog.setVisible(true);
    }

    private void addProgressField(Panel section, GridBagConstraints gbc, String label, String value, int row) {
        Panel contentPanel = (Panel) section.getComponent(1);
        if (contentPanel.getLayout() == null) {
            contentPanel.setLayout(new GridBagLayout());
        }

        // Label with modern styling
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        Label fieldLabel = new Label(label);
        fieldLabel.setFont(new Font("Arial", Font.BOLD, 14));
        fieldLabel.setForeground(new Color(52, 73, 94));
        contentPanel.add(fieldLabel, gbc);

        // Value with modern styling
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        Label valueLabel = new Label(value);
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        valueLabel.setForeground(new Color(44, 62, 80));
        valueLabel.setBackground(new Color(248, 249, 250));
        contentPanel.add(valueLabel, gbc);
    }

    private void addGraphToSection(Panel section, GridBagConstraints gbc, int row) {
        Panel contentPanel = (Panel) section.getComponent(1);
        if (contentPanel.getLayout() == null) {
            contentPanel.setLayout(new GridBagLayout());
        }

        // Create a simple graphical representation
        Panel graphPanel = new Panel();
        graphPanel.setLayout(new BorderLayout());
        graphPanel.setBackground(Color.WHITE);

        // Create a canvas-like panel for drawing
        Panel canvasPanel = new Panel() {
            public void paint(java.awt.Graphics g) {
                super.paint(g);
                // Draw simple bar chart
                g.setColor(new Color(52, 152, 219));
                if (!submittedAppraisals.isEmpty()) {
                    SubmittedAppraisal latest = submittedAppraisals.get(submittedAppraisals.size() - 1);
                    int academicBar = (int) (latest.getAcademicScore() * 2); // Scale to fit
                    int researchBar = (int) (latest.getResearchScore() * 2);
                    int extensionBar = (int) (latest.getExtensionScore() * 2);

                    g.fillRect(50, 20, academicBar, 20); // Academic
                    g.fillRect(50, 50, researchBar, 20);  // Research
                    g.fillRect(50, 80, extensionBar, 20);  // Extension

                    g.setColor(Color.BLACK);
                    g.drawString("Academic: " + String.format("%.1f", latest.getAcademicScore()), 160, 35);
                    g.drawString("Research: " + String.format("%.1f", latest.getResearchScore()), 160, 65);
                    g.drawString("Extension: " + String.format("%.1f", latest.getExtensionScore()), 160, 95);
                } else {
                    g.drawString("No data available", 50, 50);
                }
            }
        };
        canvasPanel.setPreferredSize(new Dimension(300, 120));
        canvasPanel.setBackground(Color.WHITE);

        graphPanel.add(canvasPanel, BorderLayout.CENTER);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        contentPanel.add(graphPanel, gbc);
    }

    private String getAdminStatus(SubmittedAppraisal appraisal) {
        // Use actual admin review status
        String status = getReviewStatus(appraisal);
        if ("Approved".equals(status)) {
            return "✅ Approved - High Performance";
        } else if ("Rejected".equals(status)) {
            return "❌ Rejected - Needs Improvement";
        } else {
            return "⏳ Under Review - Pending Decision";
        }
    }

    private String getAdminComments(SubmittedAppraisal appraisal) {
        return appraisal.getAdminComments() != null ? appraisal.getAdminComments() : "No comments";
    }

    private String getAdminActionDate(SubmittedAppraisal appraisal) {
        // Return action date if approved/rejected, otherwise current date for pending
        String status = getReviewStatus(appraisal);
        if ("Approved".equals(status) || "Rejected".equals(status)) {
            return new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
        } else {
            return "Pending";
        }
    }

    private void showAppraisalStatusPanel() {
        removeAll();
        setTitle("Appraisal Status - Faculty Appraisal System");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setBackground(new Color(102, 126, 234));

        Panel mainPanel = new Panel();
        mainPanel.setLayout(new GridLayout(submittedAppraisals.size() + 2, 1));
        mainPanel.setBackground(new Color(255, 255, 255, 230));

        Label titleLabel = new Label("Your Appraisal Submissions", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titleLabel);

        Label statsLabel = new Label("Total Submissions: " + submittedAppraisals.size(), Label.CENTER);
        statsLabel.setFont(new Font("Arial", Font.BOLD, 12));
        mainPanel.add(statsLabel);

        for (SubmittedAppraisal appraisal : submittedAppraisals) {
            if (appraisal.getFaculty().getName().equals(nameField.getText())) { // Match by faculty name
                Panel appraisalPanel = new Panel();
                appraisalPanel.setLayout(new GridLayout(1, 6, 5, 5));
                appraisalPanel.setBackground(new Color(248, 249, 250));
                appraisalPanel.add(new Label("Date: " + appraisal.getSubmissionDate()));
                appraisalPanel.add(new Label("Academic: " + appraisal.getAcademicScore()));
                appraisalPanel.add(new Label("Research: " + appraisal.getResearchScore()));
                appraisalPanel.add(new Label("Extension: " + appraisal.getExtensionScore()));
                appraisalPanel.add(new Label("Total: " + appraisal.getTotalScore()));
                appraisalPanel.add(new Label("Status: " + appraisal.getEligibility()));
                mainPanel.add(appraisalPanel);
            }
        }

        if (submittedAppraisals.stream().noneMatch(a -> a.getFaculty().getName().equals(nameField.getText()))) {
            mainPanel.add(new Label("No submissions found. Please submit an appraisal first.", Label.CENTER));
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(mainPanel);
        add(scrollPane, BorderLayout.CENTER);

        Button backButton = new Button("Back to Dashboard");
        backButton.addActionListener(ae -> showFacultyForm());
        add(backButton, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void showErrorDialog(String message) {
        Dialog errorDialog = new Dialog(this, "Error", true);
        errorDialog.setLayout(new FlowLayout());
        errorDialog.setBackground(new Color(220, 53, 69));
        errorDialog.add(new Label(message));
        Button okButton = new Button("OK");
        okButton.addActionListener(ae -> errorDialog.setVisible(false));
        errorDialog.add(okButton);
        errorDialog.setSize(250, 100);
        errorDialog.setVisible(true);
    }

    private void showSuccessDialog(String message) {
        Dialog successDialog = new Dialog(this, "Success", true);
        successDialog.setLayout(new FlowLayout());
        successDialog.setBackground(new Color(40, 167, 69));
        successDialog.add(new Label(message));
        Button okButton = new Button("OK");
        okButton.addActionListener(ae -> successDialog.setVisible(false));
        successDialog.add(okButton);
        successDialog.setSize(250, 100);
        successDialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command != null && command.equals("👤 Profile")) {
            showProfilePanel();
        } else if (command != null && command.equals("📝 Appraisal Status")) {
            showAppraisalStatusPanel();
        } else if (e.getSource() == registerButton) {
            showRegistrationDialog();
        } else if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleChoice.getSelectedItem();

            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password) && user.getRole().equals(role)) {
                    currentUser = user;
                    if ("faculty".equals(role)) {
                        // Load the faculty profile for this user
                        currentFaculty = facultyProfiles.get(username);
                        showFacultyForm();
                    } else if ("admin".equals(role)) {
                        showAdminPanel();
                    }
                    return;
                }
            }
            Dialog errorDialog = new Dialog(this, "Login Failed", true);
            errorDialog.setLayout(new FlowLayout());
            errorDialog.setBackground(new Color(102, 126, 234)); // Start of gradient
            errorDialog.add(new Label("Invalid credentials"));
            Button okButton = new Button("OK");
            okButton.addActionListener(ae -> errorDialog.setVisible(false));
            errorDialog.add(okButton);
            errorDialog.setSize(200, 100);
            errorDialog.setVisible(true);
        } else if (e.getSource() == calculateButton) {
            try {
                Faculty faculty;
                if (currentFaculty != null) {
                    // Use existing faculty profile
                    faculty = currentFaculty;
                    // Update level and PhD status from form if changed
                    faculty.setLevel(FacultyLevel.valueOf(levelChoice.getSelectedItem()));
                    faculty.setPhdHolder(phdCheckbox.getState());
                } else {
                    // Create new faculty if no profile exists
                    faculty = new Faculty();
                    faculty.setName(nameField.getText());
                    faculty.setLevel(FacultyLevel.valueOf(levelChoice.getSelectedItem()));
                    faculty.setPhdHolder(phdCheckbox.getState());
                    currentFaculty = faculty;
                }

                AppraisalData data = new AppraisalData();
                // PART A
                data.setAcademicResults(parseDouble(academicResultsField.getText()));
                data.setMiniProjectCount(parseInt(miniProjectCountField.getText()));
                data.setStudentFeedback(parseDouble(studentFeedbackField.getText()));
                data.setHodFeedback(parseDouble(hodFeedbackField.getText()));
                data.setInnovations(parseInt(innovationsField.getText()));
                data.setMentorshipPoints(parseInt(mentorshipPointsField.getText()));

                // PART B
                data.setPublications(parseInt(publicationsField.getText()));
                data.setPatents(parseInt(patentsField.getText()));
                data.setConsultancyProjects(parseInt(consultancyProjectsField.getText()));
                data.setCitations(parseInt(citationsField.getText()));
                data.setPhdGuidance(parseInt(phdGuidanceField.getText()));
                data.setBookPublications(parseInt(bookPublicationsField.getText()));

                // PART C
                data.setGuestLecturesDelivered(parseInt(guestLecturesDeliveredField.getText()));
                data.setCertifications(parseInt(certificationsField.getText()));
                data.setEventsOrganized(parseInt(eventsOrganizedField.getText()));
                data.setIndustryCollaborations(industryCollaborationsCheckbox.getState());

                double academicScore = appraisalService.calculateAcademicPerformance(data);
                double researchScore = appraisalService.calculateResearchDevelopment(data);
                double extensionScore = appraisalService.calculateAcademicExtension(data);
                double totalScore = appraisalService.calculateTotalScore(data);
                String eligibility = appraisalService.checkEligibility(faculty, totalScore);

                // Submit appraisal
                SubmittedAppraisal submitted = new SubmittedAppraisal(faculty, data, academicScore, researchScore, extensionScore, totalScore, eligibility);
                submittedAppraisals.add(submitted);

                Dialog resultDialog = new Dialog(this, "Appraisal Submitted", true);
                resultDialog.setLayout(new BorderLayout());
                resultDialog.setBackground(new Color(102, 126, 234)); // Start of gradient
                Panel contentPanel = new Panel(new GridLayout(6, 1));
                contentPanel.setBackground(new Color(255, 255, 255, 230)); // Semi-transparent white
                contentPanel.add(new Label("Academic Score: " + academicScore));
                contentPanel.add(new Label("Research Score: " + researchScore));
                contentPanel.add(new Label("Extension Score: " + extensionScore));
                contentPanel.add(new Label("Total Score: " + totalScore));
                contentPanel.add(new Label("Eligibility: " + eligibility));
                contentPanel.add(new Label("Appraisal submitted successfully!"));
                resultDialog.add(contentPanel, BorderLayout.CENTER);

                Panel buttonPanel = new Panel(new FlowLayout());
                Button okButton = new Button("OK");
                okButton.addActionListener(ae -> resultDialog.setVisible(false));
                buttonPanel.add(okButton);

                Button logoutButton = new Button("Logout");
                logoutButton.addActionListener(ae -> {
                    resultDialog.setVisible(false);
                    currentUser = null;
                    removeAll();
                    showLoginScreen();
                    revalidate();
                    repaint();
                });
                buttonPanel.add(logoutButton);

                resultDialog.add(buttonPanel, BorderLayout.SOUTH);
                resultDialog.setSize(350, 250);
                resultDialog.setVisible(true);

            } catch (Exception ex) {
                Dialog errorDialog = new Dialog(this, "Error", true);
                errorDialog.setLayout(new FlowLayout());
                errorDialog.setBackground(new Color(102, 126, 234)); // Start of gradient
                errorDialog.add(new Label("Invalid input: " + ex.getMessage()));
                Button okButton = new Button("OK");
                okButton.addActionListener(ae -> errorDialog.setVisible(false));
                errorDialog.add(okButton);
                errorDialog.setSize(300, 100);
                errorDialog.setVisible(true);
            }
        } else if (e.getActionCommand().equals("Logout")) {
            currentUser = null;
            currentFaculty = null; // Clear faculty profile on logout
            removeAll();
            showLoginScreen();
            revalidate();
            repaint();
    }

    private int parseInt(String text) {
        return text.isEmpty() ? 0 : Integer.parseInt(text);
    }

    private double parseDouble(String text) {
        return text.isEmpty() ? 0.0 : Double.parseDouble(text);
    }

    public static void main(String[] args) {
        FacultyAppraisalAWT app = new FacultyAppraisalAWT();
        app.setVisible(true);
    }
}
