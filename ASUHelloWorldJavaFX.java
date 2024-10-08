package asuHelloWorldJavaFX;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ASUHelloWorldJavaFX extends Application {

    // Define password criteria
    static int minLength = 5; // Minimum password length
    static int passLength = 16; // Maximum password length
    static String specialCharacters = "!@#$%^_&*()-+";

    // User storage and tracking
    private Map<String, String> users = new HashMap<>();
    private boolean isFirstUser = true;

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CSE360 Project Phase 1"); // Set the title of the window
        Scene mainScene = createMainScene(primaryStage); // Create the main scene
        primaryStage.setScene(mainScene); // Set the primary scene
        primaryStage.setWidth(400); // Set the width of the window
        primaryStage.setHeight(400); // Set the height for the window
        primaryStage.show(); // Display the window
    }

    private Scene createMainScene(Stage primaryStage) {
        // Create labels and text fields for username and password input
        Label label1 = new Label("Enter Username");
        label1.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Set label font
        TextField userText = new TextField();
        userText.setPromptText("ASU360 username"); // Placeholder text

        Label label2 = new Label("Enter Password");
        label2.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        PasswordField passText = new PasswordField();
        passText.setPromptText("Enter your password");

        Label label3 = new Label("Re-enter Password");
        label3.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        PasswordField checkPassword = new PasswordField();
        checkPassword.setPromptText("Re-enter your password");

        // Create the button to submit the form
        Button btn = new Button("Create Account");
        btn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 14px;"); // Style the button
        btn.setOnAction(event -> {
            String username = userText.getText(); // Get username from input
            String password = passText.getText(); // Get password from input
            String password2 = checkPassword.getText(); // Get re-entered password
            boolean match = passwordsMatch(password, password2); // Check if passwords match
            boolean validUser = passwordValidation(password.toCharArray()); // Validate password requirements

            // Check conditions for navigating to success or denied scene
            if (match && validUser) {
                if (isFirstUser) {
                    users.put(username, password); // Create first user account
                    isFirstUser = false; // Set flag to false after first account creation
                    primaryStage.setScene(createFinishSetupScene(primaryStage, username)); // Direct to finish setup
                } else {
                    if (users.containsKey(username)) {
                        primaryStage.setScene(createLoginSuccessScene(primaryStage)); // Show success scene
                    } else {
                        primaryStage.setScene(createDeniedScene(primaryStage)); // Show denied scene
                    }
                }
            } else {
                primaryStage.setScene(createDeniedScene(primaryStage)); // Show denied scene
            }
        });

        // Set up the layout for the main scene
        VBox root = new VBox(10); // Vertical box with 10px spacing
        root.setPadding(new Insets(20, 20, 20, 20)); // Padding around the content
        root.setAlignment(Pos.CENTER); // Center alignment of elements
        root.getChildren().addAll(label1, userText, label2, passText, label3, checkPassword, btn); // Add elements to layout
        root.setStyle("-fx-background-color: #f0f8ff;"); // Set background color

        return new Scene(root); // Return the constructed scene
    }

    private Scene createFinishSetupScene(Stage primaryStage, String username) {
        // Create labels and text fields for name
        Label setupLabel = new Label("Finish Setting Up Your Account");
        setupLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label nameLabel = new Label("Enter your Full Name:");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        TextField middleNameField = new TextField();
        middleNameField.setPromptText("Middle Name (optional)");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        TextField preferredNameField = new TextField();
        preferredNameField.setPromptText("Preferred First Name (optional)");

        // Create button to go to home
        Button goHomeBtn = new Button("Go to Dashboard");
        goHomeBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 14px;");
        goHomeBtn.setOnAction(event -> primaryStage.setScene(createHomeScene(primaryStage, firstNameField.getText(), lastNameField.getText()))); // Navigate to home page

        // Layout for finish setup scene
        VBox finishSetupLayout = new VBox(10, setupLabel, nameLabel, firstNameField, middleNameField, lastNameField, preferredNameField, goHomeBtn);
        finishSetupLayout.setAlignment(Pos.CENTER);
        finishSetupLayout.setPadding(new Insets(20, 20, 20, 20));
        finishSetupLayout.setStyle("-fx-background-color: #f0f8ff;"); // Set background color
        return new Scene(finishSetupLayout, 400, 400); // Return the scene with specified size
    }

    private Scene createHomeScene(Stage primaryStage, String firstName, String lastName) {
        // Create label for home page
        Label homeLabel = new Label("Home");
        homeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Set label font
        homeLabel.setTextFill(Color.BLUE); // Set text color to blue

        // Create a text field for email input
        TextField emailField = new TextField();
        emailField.setPromptText("exampleEmail.asu.edu"); // Placeholder text

        // Create button to send invite
        Button inviteBtn = new Button("Invite");
        inviteBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 14px;");
        inviteBtn.setOnAction(e -> {
            String email = emailField.getText(); // Get email from input
            System.out.println("Invite sent to: " + email); // Placeholder action for sending an invite
            emailField.clear(); // Clear the email field after sending the invite
        });

        // Create button to return to the main scene
        Button goBackBtn = new Button("Go Back to Login");
        goBackBtn.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 14px;");
        goBackBtn.setOnAction(e -> primaryStage.setScene(createMainScene(primaryStage))); // Navigate back to main scene

        // Layout for home page
        VBox homeLayout = new VBox(20, homeLabel, emailField, inviteBtn, goBackBtn); // Vertical box with spacing
        homeLayout.setAlignment(Pos.CENTER); // Center alignment
        homeLayout.setStyle("-fx-background-color: #e6f7ff;"); // Set background color
        return new Scene(homeLayout, 400, 300); // Return the scene with specified size
    }

    private Scene createLoginSuccessScene(Stage primaryStage) {
        // Create label to indicate successful login
        Label successLabel = new Label("Login Successful!");
        successLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Set label font
        successLabel.setTextFill(Color.GREEN); // Set text color to green

        // Create button to return to the main scene
        Button goBackBtn = new Button("Go Back to Login");
        goBackBtn.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 14px;");
        goBackBtn.setOnAction(e -> primaryStage.setScene(createMainScene(primaryStage))); // Navigate back to main scene

        // Layout for success message
        VBox successLayout = new VBox(20, successLabel, goBackBtn); // Vertical box with spacing
        successLayout.setAlignment(Pos.CENTER); // Center alignment
        successLayout.setStyle("-fx-background-color: #e6ffe6;"); // Set background color
        return new Scene(successLayout, 400, 300); // Return the scene with specified size
    }

    private Scene createDeniedScene(Stage primaryStage) {
        // Create label for denied message
        Label deniedLabel = new Label("Password Mismatch or Invalid Password!");
        deniedLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        deniedLabel.setTextFill(Color.RED); // Set text color to red

        // Create button to retry
        Button retryBtn = new Button("Retry");
        retryBtn.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-size: 14px;");
        retryBtn.setOnAction(e -> primaryStage.setScene(createMainScene(primaryStage))); // Navigate back to main scene

        // Layout for denied message
        VBox deniedLayout = new VBox(20, deniedLabel, retryBtn); // Vertical box with spacing
        deniedLayout.setAlignment(Pos.CENTER); // Center alignment
        deniedLayout.setStyle("-fx-background-color: #ffe6e6;"); // Set background color
        return new Scene(deniedLayout, 400, 300); // Return the scene with specified size
    }

    // Method to validate the password according to defined criteria
    public static boolean passwordValidation(char[] password) {
        boolean meetsLength = password.length >= minLength && password.length <= passLength; // Length check
        boolean hasCapitalLetter = false; // Flag for uppercase letter
        boolean hasLowerCaseLetter = false; // Flag for lowercase letter
        boolean hasDigit = false; // Flag for digit
        boolean hasSpecialChar = false; // Flag for special character

        // Check each character in the password
        for (char ch : password) {
            if (Character.isUpperCase(ch)) {
                hasCapitalLetter = true; // Found an uppercase letter
            } else if (Character.isLowerCase(ch)) {
                hasLowerCaseLetter = true; // Found a lowercase letter
            } else if (Character.isDigit(ch)) {
                hasDigit = true; // Found a digit
            } else if (specialCharacters.indexOf(ch) >= 0) {
                hasSpecialChar = true; // Found a special character
            }
        }
        // Return true if all conditions are met
        return meetsLength && hasCapitalLetter && hasLowerCaseLetter && hasDigit && hasSpecialChar;
    }

    // Method to check if two passwords match
    public static boolean passwordsMatch(String pass1, String pass2) {
        return pass1.equals(pass2); // Return true if passwords are identical
    }
}

