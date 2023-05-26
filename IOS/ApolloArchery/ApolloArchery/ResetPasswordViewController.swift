//
//  ResetPasswordViewController.swift
//  Apollo Archery
//
//  Created by Antoine Mitchell on 5/20/23.
//

import UIKit
import FirebaseAuth

class ResetPasswordViewController: UIViewController {

    @IBOutlet weak var emailTextField: UITextField!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    

    
    @IBAction func sendButton(_ sender: UIButton) {
        
        let email = emailTextField.text!.trimmingCharacters(in: .whitespacesAndNewlines)
        
        if (!email.isEmpty || !email.contains("@") || !email.contains(".com")) {
            
            let alert = UIAlertController(title: "Reset Password", message: "Check your email to reset your password", preferredStyle: .alert)
            
            let okButton = UIAlertAction(title: "OK", style: .destructive, handler: {_ in
                
                self.navigationController?.popToRootViewController(animated: true)

                Auth.auth().sendPasswordReset(withEmail: email) { error in
                  // ...
                }
                
            })
            alert.addAction(okButton)
            
            // Present the alert
            present(alert, animated: true, completion: nil)
        }
        
    }
    
}
