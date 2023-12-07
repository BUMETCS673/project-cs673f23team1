//
//  Item.swift
//  TerriersMall
//
//  Created by Huanzhou Wang on 11/9/23.
//

import Foundation
import SwiftData

@Model
class Item {
    var timestamp: Date
    var name: String
    var price: Int
    var imageURL: URL
    var sentByUser: Bool
    
    init(timestamp: Date, name: String, price: Int, imageURL: URL, sentByUser: Bool) {
        self.timestamp = timestamp
        self.name = name
        self.price = price
        self.imageURL = imageURL
        self.sentByUser = sentByUser
    }
}





     

