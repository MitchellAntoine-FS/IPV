//
//  SetupViewController.swift
//  Apollo Archery
//
//  Created by Antoine Mitchell on 5/20/23.
//

import UIKit
import FirebaseAuth

class LogInViewController: UIViewController {
    
    
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var forgotPasswordLink: UILabel!
    @IBOutlet weak var logInbutton: UIButton!
    @IBOutlet weak var createAccountLink: UILabel!
    @IBOutlet weak var errorLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        // Hide error label
        errorLabel.alpha = 0
        
        let tapPwd = UITapGestureRecognizer(target: self, action: #selector(self.forgotPassword))
        
        tapPwd.numberOfTapsRequired = 1
        forgotPasswordLink.isUserInteractionEnabled = true
        forgotPasswordLink.textColor = UIColor.white
        forgotPasswordLink.addGestureRecognizer(tapPwd)
        
        let tapCreateAccount = UITapGestureRecognizer(target: self, action: #selector(self.createAccount))
        
        tapCreateAccount.numberOfTapsRequired = 1
        createAccountLink.isUserInteractionEnabled = true
        createAccountLink.textColor = UIColor.white
        createAccountLink.addGestureRecognizer(tapCreateAccount)
    }
    
    @IBAction func logInButton(_ sender: UIButton) {
        
        let email = emailTextField.text!.trimmingCharacters(in: .whitespacesAndNewlines)
        let password = passwordTextField.text!.trimmingCharacters(in: .whitespacesAndNewlines)

        
        // Sign in the user
        Auth.auth().signIn(withEmail: email, password: password) { (results, error) in
            if error != nil {
                // Couldn't sign in
                self.errorLabel.text = error!.localizedDescription
                self.errorLabel.alpha = 1
            }
            else {
            
                self.goToHomeView()
            }
        }
        
    }
    
    func validateFields() -> String? {
        
        // Check that all fields are filled in
        if emailTextField.text?.trimmingCharacters(in: .whitespacesAndNewlines) == " " ||
            passwordTextField.text?.trimmingCharacters(in: .whitespacesAndNewlines) == " " {
            
            return "Please fill in all fileds"
        }
        
        // Check if password is secure
        let cleanedPassword = passwordTextField.text!.trimmingCharacters(in: .whitespacesAndNewlines)
        
        if Utilities.isPasswordValid(cleanedPassword) == false {
            // Password is not secure
            return "Make sure your password is at least 8 characters, contains a special character and a number"
        }
        
        return nil
    }
    
    @objc func forgotPassword(_ sender: UITapGestureRecognizer) {
        goToForgotPwd()
    }
    
    @objc func createAccount(_ sender: UITapGestureRecognizer) {
         goToCreateAccountView()
        print("Clicked")
    }
    
    func goToForgotPwd() {
        
        let mainStoryBoard = UIStoryboard(name: "Main", bundle: Bundle.main)
        let navResetPasswordController = mainStoryBoard.instantiateViewController(withIdentifier: "ResetVC") as! ResetPasswordViewController
        self.navigationController?.pushViewController(navResetPasswordController, animated: true)
           
    }
    
    func goToCreateAccountView() {
        
        let mainStoryBoard = UIStoryboard(name: "Main", bundle: Bundle.main)
        let navCreateAccountController = mainStoryBoard.instantiateViewController(withIdentifier: "CreateAccountVC") as! CreateAccountViewController
        self.navigationController?.pushViewController(navCreateAccountController, animated: true)
            
    }
    
    func goToHomeView() {
        
        let mainStoryBoard = UIStoryboard(name: "Main", bundle: Bundle.main)
        
        if let navController = mainStoryBoard.instantiateViewController(withIdentifier: "NavVC") as? UINavigationController {

            present(navController, animated: true, completion: nil )
        }
    }
    
}
