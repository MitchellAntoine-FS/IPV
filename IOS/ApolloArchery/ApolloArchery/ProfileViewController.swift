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
    
    var imageUrl: URL?
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        self.profileImageView.layer.cornerRadius = self.profileImageView.frame.size.width / 2
        self.profileImageView.clipsToBounds = true
        
        if Auth.auth().currentUser != nil {
        setDataFromFirebase()
     }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        guard let savedImage = UserDefaults.standard.string(forKey: "imageUrl")
        else {return}
        self.setImageFromStringrURL(stringUrl: savedImage)
       
    }
    
    func setDataFromFirebase() {

        let user = Auth.auth().currentUser
        if let user = user {
          
          let userName = user.displayName
            self.userNameLabel.text = userName
            
            imageUrl = user.photoURL
            
            UserDefaults.standard.set(imageUrl?.absoluteString, forKey: "imageUrl")

            guard let savedImage = UserDefaults.standard.string(forKey: "imageUrl")
            else {return}
            
            self.setImageFromStringrURL(stringUrl: imageUrl!.absoluteString)
           
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
                    guard metadata != nil else {
                        // Uh-oh, an error occurred!
                        return
                    }
                    
                    // You can also access to download URL after upload.
                    profileRef.downloadURL { (url, error) in
                        
                        guard let downloadURL = url else {
                            // Uh-oh, an error occurred!
                            return
                        }
                        UserDefaults.standard.set(downloadURL.absoluteString, forKey: "imageUrl")

                        guard let savedImage = UserDefaults.standard.string(forKey: "imageUrl")
                        else {return}
                        self.setImageFromStringrURL(stringUrl: savedImage)
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
