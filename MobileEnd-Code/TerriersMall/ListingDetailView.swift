//
//  ListingDetailView.swift
//  TerriersMall
//
//  Created by Huanzhou Wang on 12/6/23.
//

import SwiftUI


struct Listing: Identifiable {
    let id = UUID()
    let imageName: String
    let title: String
    let price: String
    let status: String
    let listedDate: String
}

struct ListingDetailView: View {
    var listing: Listing = Listing(
        imageName: "dyson.airpurifier",
        title: "Dyson BP03 Air Purifier",
        price: "$700",
        status: "Available",
        listedDate: "Listed on 10/12"
    )
    
    @State private var isSold: Bool = false

    var body: some View {
            List{
                VStack {
                    Image(systemName: listing.imageName) // Replace with your image resource name
                        .resizable()
                        .scaledToFit()
                        .frame(height: 200)
                        .padding()
                    
                    Text(listing.title)
                        .font(.title2)
                        .padding([.top, .leading, .trailing])
                    
                    HStack {
                        Text(listing.price)
                            .font(.headline)
                        Spacer()
                        Text(isSold ? "Sold" : listing.status)
                        Spacer()
                        Text(listing.listedDate)
                            .font(.caption)
                            .foregroundColor(.gray)
                    }
                    .padding()
                    
                    HStack {
                        
                        
                        Button(isSold ? "Sold on \(Date().formatted(.dateTime.day().month().year()))" : "Mark as sold") {
                            markAsSold()
                        }
                        .fontWeight(.bold)
                        .padding()
                        .frame(maxWidth: .infinity)
                        .background(isSold ? Color.green : Color.red)
                        .foregroundColor(.white)
                        .cornerRadius(10)
                    }
                    .padding()
                    
                    Spacer()
                }
        }
        
    }
    
    func markAsSold() {
        isSold = true
    }
}

#Preview {
    ListingDetailView()
}
