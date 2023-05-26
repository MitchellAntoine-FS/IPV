//
//  UserDefaults.swift
//  ApolloArchery
//
//  Created by Antoine Mitchell on 5/25/23.
//

import Foundation
import UIKit

extension UserDefaults {
    
    // Save a UIColor as a data object
    func set(color: UIColor, forKey key: String) {
        
        //Convert the UIColor object into a Data object by archiving
        let binaryData = try? NSKeyedArchiver.archivedData(withRootObject: color, requiringSecureCoding: true)
        
        // Save binary data to Userdefaults
        self.set(binaryData, forKey: key)
        
    }
    
    // Get UIColor from saved defaults with key
    func color(forKey key: String) -> UIColor? {
        // Check for valid data
        if let binaryData = data(forKey: key) {
            //Is the data UIColor
            if let color = try? NSKeyedUnarchiver.unarchivedObject(ofClass: UIColor.self, from: binaryData) {
                //Retturn the UIColor
                return color
            }
        }
        // If we don't return a UIColor, something is wrong
        return nil
    }
    
}
