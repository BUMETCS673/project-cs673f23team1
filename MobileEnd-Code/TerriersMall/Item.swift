//
//  Item.swift
//  TerriersMall
//
//  Created by Huanzhou Wang on 11/9/23.
//

import Foundation
import SwiftData
import _PhotosUI_SwiftUI

@Model
class Item {
    var timestamp: Date
    var name: String
    var price: Int
    var imageURL: URL?
    var sentByUser: Bool
    var selectedImageData: Data?
    var isSold = false
    
    
    init(timestamp: Date, name: String, price: Int, imageURL: URL? = nil, sentByUser: Bool, selectedImageData: Data? = nil) {
        self.timestamp = timestamp
        self.name = name
        self.price = price
        self.imageURL = imageURL
        self.sentByUser = sentByUser
        self.selectedImageData = selectedImageData
    }
    
}





     

