//
//  ProfileViewController.swift
//  Apollo Archery
//
//  Created by Antoine Mitchell on 5/20/23.
//

import UIKit
import Photos
import PhotosUI
import FirebaseAuth

class ProfileViewController: UIViewController, PHPickerViewControllerDelegate {

    @IBOutlet weak var profileImageView: UIImageView!
    @IBOutlet weak var userNameLabel: UILabel!
    @IBOutlet weak var tableView: UITableView!
    
    var images = [UIImage]()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
       
        /*
        let user = Auth.auth().currentUser
        if let user = user {
          // The user's ID, unique to the Firebase project.
          // Do NOT use this value to authenticate with your backend server,
          // if you have one. Use getTokenWithCompletion:completion: instead.
          let uid = user.uid
          let email = user.email
          let photoURL = user.photoURL
          
          
        }
        */
        
    }
    
    
    func picker(_ picker: PHPickerViewController, didFinishPicking results: [PHPickerResult]) {
        picker.dismiss(animated: true, completion: nil)
        
        results.forEach { result in
            result.itemProvider.loadObject(ofClass: UIImage.self) { reading, error in

                guard let image = reading as? UIImage, error == nil else {
                    return
                }
                
                DispatchQueue.main.async {
                    
                    self.profileImageView.image = image
                    
                }
                
            }

        }
    }
   

    
    @IBAction func uploadImageButton(_ sender: UIButton) {
        
        var config = PHPickerConfiguration(photoLibrary: .shared())
        config.selectionLimit = 1
        config.filter = .images
        let vc = PHPickerViewController(configuration: config)
        vc.delegate = self
        present(vc, animated: true)

    }
    
}
