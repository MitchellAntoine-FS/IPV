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
        self.profileImageView.layer.cornerRadius = self.profileImageView.frame.size.width / 2
        self.profileImageView.layer.masksToBounds = true
        self.profileImageView.clipsToBounds = true
        self.profileImageView.layer.borderWidth = 2
        self.profileImageView.layer.borderColor = UIColor.black.cgColor
        
        if Auth.auth().currentUser != nil {
        setDataFromFirebase()
     }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        
        guard let newImage = UserDefaults.standard.object(forKey: "profileImageUrl") as? UIImage else {
            return
        }
        DispatchQueue.main.async {
            self.profileImageView.image = newImage
        }
        
    }
    
    func setDataFromFirebase() {

        let user = Auth.auth().currentUser
        if let user = user {
          
          let userName = user.displayName
            self.userNameLabel.text = userName
            
            guard let imageUrl = user.photoURL else {
                return
            }
            self.setImageFromStringrURL(stringUrl: imageUrl.absoluteString)
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
        
        self.presentPhotoActionsheet()
    }
    
}

extension ProfileViewController: UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    func presentPhotoActionsheet() {
        let actionSheet = UIAlertController(title: "Profile Picture", message: "How would you like to select a picture?", preferredStyle: .actionSheet)
        actionSheet.addAction(UIAlertAction(title: "Cancel", style: .cancel))
        actionSheet.addAction(UIAlertAction(title: "Take Photo", style: .default, handler: { [weak self] _ in
            
            self?.presentCamera()
        }))
        actionSheet.addAction(UIAlertAction(title: "Select Photo", style: .default, handler: { [weak self] _ in
            
            self?.presentPHPicker()
        }))
        
        present(actionSheet, animated: true)
    }
    
    func presentCamera() {
        let vc = UIImagePickerController()
        vc.sourceType = .camera
        vc.delegate = self
        vc.allowsEditing = true
        present(vc, animated: true)
    }
    
    func presentPhotoPicker() {
        let vc = UIImagePickerController()
        vc.sourceType = .photoLibrary
        vc.delegate = self
        vc.allowsEditing = true
        present(vc, animated: true)
    }
    
    func presentPHPicker() {
        var config = PHPickerConfiguration(photoLibrary: .shared())
        config.selectionLimit = 1
        config.filter = .images
        let vc = PHPickerViewController(configuration: config)
        vc.delegate = self
        present(vc, animated: true)
    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        guard let selectedImage = info[UIImagePickerController.InfoKey.editedImage] as? UIImage else {
            return
        }
        
        self.profileImageView.image = selectedImage
        
        picker.dismiss(animated: true)
    }
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        picker.dismiss(animated: true)
    }
    
    func picker(_ picker: PHPickerViewController, didFinishPicking results: [PHPickerResult]) {
        picker.dismiss(animated: true, completion: nil)
        
        results.forEach { result in
            result.itemProvider.loadObject(ofClass: UIImage.self) { reading, error in

                guard let profileImage = reading as? UIImage, error == nil, let profileImageData = profileImage.pngData() else {
                    return
                }
                
                DispatchQueue.main.async {
                    self.profileImageView.image = profileImage
                }
                
                let fileName = "profile_picture.jpg"
                StorageManager.shared.uploadProfilePicture(with: profileImageData, fileName: fileName, completion: {result in
                    switch result {
                    case .success(let downloadUrl):
                        UserDefaults.standard.set(downloadUrl, forKey: "profileImageUrl")
                        print(downloadUrl)
                    case .failure(let error):
                        print("Storage Manager error: \(error)")
                    }
                })
            }
        }
    }
    
    
}
