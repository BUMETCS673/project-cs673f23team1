//
//  PostPage.swift
//  TerriersMall
//
//  Created by Huanzhou Wang on 11/9/23.
//

import SwiftUI
import PhotosUI

struct PostPage: View {
    @State private var itemName: String = ""
    @State private var itemPrice: String = ""
    @State private var itemDescription: String = ""
    @State private var selectedPhotoItem: PhotosPickerItem? = nil
    @State private var selectedImageData: Data? = nil

    var body: some View {
        NavigationView {
            VStack {
                
                Text("Listing Details")
                    .font(.largeTitle)
                    .fontWeight(.heavy)
                    .padding()

                if let selectedImageData = selectedImageData, let uiImage = UIImage(data: selectedImageData) {
                    Image(uiImage: uiImage)
                        .resizable()
                        .scaledToFit()
                        .frame(height: 200)
                } else {
                    PhotosPicker(
                        selection: $selectedPhotoItem,
                        matching: .images,
                        photoLibrary: .shared()) {
                            Text("Add Photos / Videos")
                                .foregroundColor(.black)
                                .frame(width: 200,height: 200)

                                .overlay(content: {
                                    Rectangle()
                                        .strokeBorder()
                                        .foregroundColor(.gray)
                                        
                                })
                                
                                
                            
                        }
                        .onChange(of: selectedPhotoItem) { newItem in
                            guard let newItem = newItem else { return }
                            Task {
                                // Load the selected image data
                                if let data = try? await newItem.loadTransferable(type: Data.self) {
                                    selectedImageData = data
                                }
                            }
                        }
                }
                TextField("Item Name", text: $itemName)
                    .padding()
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    

                TextField("Price", text: $itemPrice)
                    .keyboardType(.decimalPad)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .padding()

                TextField("Description", text: $itemDescription)
                    .keyboardType(.decimalPad)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .padding()
                
                Button(action: {
                    ContentView.displayItems.append(Item(timestamp: Date(), name: itemName, price: Int(itemPrice) ?? 0, imageURL:URL(string: "https://images.manning.com/book/e/59c8b18-b8fd-4d32-939b-25dcbb4d525d/Rocca-ADS-HI.png")!))
                    
                }) {
                    Text("Post")
                        .frame(minWidth: 0, maxWidth: .infinity)
                        .frame(height: 50)
                        .foregroundColor(.white)
                        .font(.system(size: 18, weight: .bold))
                        .background(Color.red)
                        .cornerRadius(25)
                }
                .padding()

                Spacer()
            }
            .padding()
        }
        
    }
}

#Preview {
    PostPage()
}
