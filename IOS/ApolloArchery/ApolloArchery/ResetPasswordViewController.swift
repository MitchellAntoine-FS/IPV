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
        
        Auth.auth().sendPasswordReset(withEmail: email) { error in
          // ...
        }
        
    }
    
}
