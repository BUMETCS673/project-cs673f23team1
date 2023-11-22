//
//  Item.swift
//  TerriersMall
//
//  Created by Huanzhou Wang on 11/9/23.
//

import Foundation
import SwiftData

@Model
final class Item {
    var timestamp: Date
    var name: String
    var price: Int
    var imageURL: URL
    
    init(timestamp: Date, name: String, price: Int, imageURL: URL) {
        self.timestamp = timestamp
        self.name = name
        self.price = price
        self.imageURL = imageURL
    }
}





     

