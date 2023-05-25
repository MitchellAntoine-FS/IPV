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
import FirebaseStorage

class ProfileViewController: UIViewController, PHPickerViewControllerDelegate {

    @IBOutlet weak var profileImageView: UIImageView!
    @IBOutlet weak var userNameLabel: UILabel!
    @IBOutlet weak var tableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        self.profileImageView.layer.cornerRadius = self.profileImageView.frame.size.width/2
        self.profileImageView.layer.masksToBounds = true
        self.profileImageView.clipsToBounds = true
        
        let user = Auth.auth().currentUser
        if let user = user {
          
          let userName = user.displayName
            self.userNameLabel.text = userName
            
            let imageUrl = user.photoURL
            
            if imageUrl != nil {
                self.setImageFromStringrURL(stringUrl: imageUrl!.absoluteString)
            }
        }
        
    }
    
    
    func picker(_ picker: PHPickerViewController, didFinishPicking results: [PHPickerResult]) {
        picker.dismiss(animated: true, completion: nil)
        
        results.forEach { result in
            result.itemProvider.loadObject(ofClass: UIImage.self) { reading, error in

                guard let profileImage = reading as? UIImage, error == nil else {
                    return
                }

                    // Get a reference to the storage service using the default Firebase App
                    let storage = Storage.storage()
                    let storageRef = storage.reference()
                    let profileRef = storageRef.child("profileImage.jpg")
                    let profileImageData = profileImage.pngData()
                    

                    // Upload the file to the path "profileImage.jpg"
                    profileRef.putData(profileImageData!, metadata: nil) { (metadata, error) in
                        guard let metadata = metadata else {
                            // Uh-oh, an error occurred!
                            return
                        }
                        
                        // You can also access to download URL after upload.
                        profileRef.downloadURL { (url, error) in
                            
                            guard let downloadURL = url else {
                                // Uh-oh, an error occurred!
                                return
                                }
                            self.setImageFromStringrURL(stringUrl: downloadURL.absoluteString)

                        }
                        
                    }
            }

        }
    }

    func setImageFromStringrURL(stringUrl: String) {
        if let url = URL(string: stringUrl) {
        URLSession.shared.dataTask(with: url) { (data, response, error) in
          // Error handling...
          guard let imageData = data else { return }

          DispatchQueue.main.async {
              self.profileImageView.image = UIImage(data: imageData)
          }
        }.resume()
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
