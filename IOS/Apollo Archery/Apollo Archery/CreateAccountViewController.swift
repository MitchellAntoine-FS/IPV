//
//  CreateAccountViewController.swift
//  Apollo Archery
//
//  Created by Antoine Mitchell on 5/20/23.
//

import UIKit
import FirebaseAuth
import SwiftUI

class CreateAccountViewController: UIViewController {

    @IBOutlet weak var firstNameTextFild: UITextField!
    @IBOutlet weak var lastNameTextField: UITextField!
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextFild: UITextField!
    @IBOutlet weak var logInLink: UILabel!
    @IBOutlet weak var createAccountButton: UIButton!
    @IBOutlet weak var errorLabel: UILabel!
 

    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        let tap = UITapGestureRecognizer(target: self, action: #selector(CreateAccountViewController.logIn))
        logInLink.addGestureRecognizer(tap)
        
        setUpElements()
    }
    
    func setUpElements() {
        
        // Hide error label
        errorLabel.alpha = 0
        
        // Style the textFileds
        Utilities.styleTextField(firstNameTextFild)
        Utilities.styleTextField(lastNameTextField)
        Utilities.styleTextField(emailTextField)
        Utilities.styleTextField(passwordTextFild)
        
    }

    func validateFields() -> String? {
        
        // Check that all fields are filled in
        if firstNameTextFild.text?.trimmingCharacters(in: .whitespacesAndNewlines) == " " ||
            lastNameTextField.text?.trimmingCharacters(in: .whitespacesAndNewlines) == " " ||
            emailTextField.text?.trimmingCharacters(in: .whitespacesAndNewlines) == " " ||
            passwordTextFild.text?.trimmingCharacters(in: .whitespacesAndNewlines) == " " {
            
            return "Please fill in all fileds"
        }
        
        // Check if password is secure
        let cleanedPassword = passwordTextFild.text!.trimmingCharacters(in: .whitespacesAndNewlines)
        
        if Utilities.isPasswordValid(cleanedPassword) == false {
            // Password is not secure
            return "Make sure your password is at least 8 characters, contains a special character and a number"
        }
        
        return nil
    }
    
    @IBAction func createAccountButton(_ sender: UIButton) {
        
        let email = emailTextField.text!.trimmingCharacters(in: .whitespacesAndNewlines)
        let password = passwordTextFild.text!.trimmingCharacters(in: .whitespacesAndNewlines)
        let firstName = firstNameTextFild.text!.trimmingCharacters(in: .whitespacesAndNewlines)
        let lastName = lastNameTextField.text!.trimmingCharacters(in: .whitespacesAndNewlines)
        let displayName = firstName + " " + lastName
        
        // Create new account by passing the new user's email address and password to createUser.
        Auth.auth().createUser(withEmail: email, password: password) { result, err in
            
            // Check for errors
            if err != nil {
                self.showError("Error creating user")
            }
            else {
                
                let changeRequest = Auth.auth().currentUser?.createProfileChangeRequest()
                    changeRequest?.displayName = displayName
                    changeRequest?.commitChanges { error in
                }
                self.goToHomeView()
            }
        }
    }
    
    @IBAction func logIn(_ sender: UITapGestureRecognizer) {
        navigationController?.pushViewController(LogInViewController(), animated: true)
    }
    
    func showError(_ message: String) {
        errorLabel.text = message
        errorLabel.alpha = 1
    }
    
    func goToHomeView() {
        
        let mainStoryBoard = UIStoryboard(name: "Main", bundle: Bundle.main)
        
        if let navController = mainStoryBoard.instantiateViewController(withIdentifier: "NavVC") as? UINavigationController {

            present(navController, animated: true, completion: nil )
        }
    }
    


    
}
