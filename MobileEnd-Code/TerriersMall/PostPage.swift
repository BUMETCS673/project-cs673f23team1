//
//  PostPage.swift
//  TerriersMall
//
//  Created by Huanzhou Wang on 11/9/23.
//

import SwiftUI
import PhotosUI
import SwiftData

struct PostPage: View {
    @State private var itemName: String = ""
    @State private var itemPrice: String = ""
    @State private var itemDescription: String = ""
    @State private var selectedPhotoItem: PhotosPickerItem? = nil
    @State private var selectedImageData: Data? = nil
    
    @Environment(\.modelContext) private var modelContext
    @Environment(\.dismiss) private var dismiss
    @Query private var items: [Item]
    
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
                    modelContext.insert(Item(timestamp: Date(), name: itemName, price: Int(itemPrice) ?? 0, sentByUser: true, selectedImageData: selectedImageData))
                    dismiss()
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
