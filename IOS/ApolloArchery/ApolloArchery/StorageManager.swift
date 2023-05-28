//
//  StorageManager.swift
//  ApolloArchery
//
//  Created by Antoine Mitchell on 5/26/23.
//

import Foundation
import FirebaseStorage

final class StorageManager {
    
    static let shared = StorageManager()
    
    private let storage = Storage.storage().reference()

    /*
     /images/profile_picture.jpg
     */

    public typealias UploadPictureCompletion = (Result<String, Error>) -> Void

    // Upload image to Firebase storage and return completion with url string to download
    public func uploadProfilePicture(with data: Data, fileName: String, completion: @escaping UploadPictureCompletion) {
        storage.child("images/\(fileName)").putData(data, metadata: nil, completion: { metadata, error in
            guard error == nil else {
                // failed
                print("Failed to upload data to Firebese")
                completion(.failure(StorageErrors.failedToUpload))
                return
            }
            
            self.storage.child("images/\(fileName)").downloadURL(completion: { url, error in
                guard let url = url else {
                    print("Failed to get download Url")
                    completion(.failure(StorageErrors.failedToGetDownloadUrl))
                    return
                }
                
                let urlString = url.absoluteString
                print("download url returned: \(urlString)")
                completion(.success(urlString))
            })
        })
        
    }

    public enum StorageErrors: Error {
        case failedToUpload
        case failedToGetDownloadUrl
        
    }

}
