//
//  ListingDetailView.swift
//  TerriersMall
//
//  Created by Huanzhou Wang on 12/6/23.
//

import SwiftUI
import SwiftData


struct ListingDetailView: View {

    
    @Environment(\.modelContext) private var modelContext
    @Query private var items: [Item]
    

    var body: some View {
        List{
            
            ForEach(items) { item in
                if item.sentByUser {
                    VStack {
                        
                        
                        if item.selectedImageData != nil {
                            Image(uiImage: UIImage(data: item.selectedImageData!)!)
                                .resizable()
                                .scaledToFit()
                                .frame(height: 200)
                                .padding()
                        } else{
                            AsyncImage(url: item.imageURL)
                            { image in
                                image
                                    .image?
                                    .resizable()
                                    .scaledToFit()
                                    .frame(height: 200)
                                    .padding()
                            }

                        }
                        

                        
                        Text(item.name)
                            .font(.title2)
                            .padding([.top, .leading, .trailing])
                        
                        HStack {
                            Text("$\(item.price)")
                                .font(.headline)
                            Spacer()
                            Text(item.isSold ? "Sold" : "Available")
                            Spacer()
                            Text(item.timestamp.formatted(.dateTime))
                                .font(.caption)
                                .foregroundColor(.gray)
                        }
                        .padding()
                        
                        HStack {
                            
                            
                            Button(item.isSold ? "Sold on \(Date().formatted(.dateTime.day().month().year()))" : "Mark as sold") {
                                markAsSold(item: item)
                            }
                            .fontWeight(.bold)
                            .padding()
                            .frame(maxWidth: .infinity)
                            .background(item.isSold ? Color.green : Color.red)
                            .foregroundColor(.white)
                            .cornerRadius(10)
                        }
                        .padding()
                        
                        Spacer()
                    }
                    
                }
                
                
            }
            
        }
    }
    
    func markAsSold(item: Item) {
        item.isSold = true
    }
}

#Preview {
    ListingDetailView()
}
