//
//  ViewController.swift
//  ApolloArchery
//
//  Created by Antoine Mitchell on 5/24/23.
//

import UIKit

class ViewController: UIViewController {
    
    @IBOutlet weak var arrowDailyCountLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        guard let savedCount = UserDefaults.standard.value(forKey: "arrowCount")
        else {return}
        let savedCounter = savedCount as! Int
        
        self.arrowDailyCountLabel.text = savedCounter.description
    }
    
    override func viewWillAppear(_ animated: Bool) {
        guard let savedCount = UserDefaults.standard.value(forKey: "arrowCount")
        else {return}
        let savedCounter = savedCount as? Int
        self.arrowDailyCountLabel.text = savedCounter?.description
    }

   
    @IBAction func profileButton(_ sender: UIButton) {
    }
    
    @IBAction func arrowCounterButton(_ sender: UIButton) {
    }
    
}
