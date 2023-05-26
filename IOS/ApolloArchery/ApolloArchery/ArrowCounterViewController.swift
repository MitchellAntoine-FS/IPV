//
//  ArrowCounterViewController.swift
//  Apollo Archery
//
//  Created by Antoine Mitchell on 5/20/23.
//

import UIKit

class ArrowCounterViewController: UIViewController {
    
    @IBOutlet weak var countLabel: UILabel!
    
    var arrowCount = 0
    var savedCounter: Int?
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        guard let savedCount = UserDefaults.standard.value(forKey: "arrowCount")
        else {return}
        savedCounter = savedCount as? Int
        self.countLabel.text = savedCounter?.description
    }

    
    @IBAction func addButton(_ sender: UIButton) {
        self.arrowCount += 1
        UserDefaults.standard.set(self.arrowCount, forKey: "arrowCount")
        guard let savedCount = UserDefaults.standard.value(forKey: "arrowCount")
        else {return}
        savedCounter = savedCount as? Int
        self.countLabel.text = savedCounter?.description
    }
    
    @IBAction func minusButton(_ sender: UIButton) {
        if (arrowCount != 0) {
            self.arrowCount -= 1
            UserDefaults.standard.set(self.arrowCount, forKey: "arrowCount")
            guard let savedCount = UserDefaults.standard.value(forKey: "arrowCount")
            else {return}
            savedCounter = savedCount as? Int
            self.countLabel.text = savedCounter?.description
        }
    }
    
}
