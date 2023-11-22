//
//  ContentView.swift
//  TerriersMall
//
//  Created by Huanzhou Wang on 11/9/23.
//

import SwiftUI
import SwiftData

struct ContentView: View {
    @Environment(\.modelContext) private var modelContext
    @Query private var items: [Item]
    
    let columns: [GridItem] = [
        GridItem(.flexible(minimum: 200, maximum: 200)),
        GridItem(.flexible(minimum: 200, maximum: 200))
    ]
    
    var body: some View {
        NavigationView{
            ScrollView {
                    LazyVGrid(columns: columns){
                        ForEach(displayItems) { item in
                            NavigationLink {
                                VStack{
                                    Text("UserID: Huanzhou Wang")
                                    AsyncImage(url: item.imageURL) { image in
                                        image
                                            .image?
                                            .resizable()
                                            .clipped()
                                            .frame(width: .infinity, height: 500)
                                        
                                    }
                                    .cornerRadius(25)
                                    .padding()
                                    HStack{
                                        Text("$\(item.price)")
                                            .font(.largeTitle)
                                        Spacer()
                                        Text(item.name)
                                            .font(.title)
                                    }
                                    .padding()
                                    Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
                                        Text("Interested")
                                    })
                                    .foregroundColor(.white)
                                    .padding()
                                    .background(Color.red)
                                    .cornerRadius(25)
                                }
                            } label: {
                            VStack{
                                AsyncImage(url: item.imageURL) { image in
                                    image
                                        .image?
                                        .resizable()
                                        .clipped()
                                        .frame(width: 200, height: 200)
                                }
                                .cornerRadius(5)
                                HStack{
                                    Text("$\(item.price)")
                                    Text(item.name)
                                }
                                
                            }
                        }
                    }
                }
                .navigationBarItems(leading:
                                        HStack{
                    NavigationLink {
                        LoginPage()
                    } label: {
                        Image(systemName: "person.fill")
                    }

                    
//                    Image(systemName: "gear")
                }
                                    , trailing:
                                        HStack{
                    
                    NavigationLink {
                        PostPage()
                    } label: {
                        Image(systemName: "square.and.pencil")

                    }
                })
                .navigationTitle("Terriers Mall")
                .foregroundColor(.red)
                .scrollIndicators(.hidden)
            }
        }
        .accentColor(.red)
    }
    
    var displayItems: [Item] =
    [Item(timestamp: Date(), name: "Used iPhone 14", price: 300, imageURL: URL(string: "https://img.kleinanzeigen.de/api/v1/prod-ads/images/ed/ed1bff99-12fd-45e1-8d16-c7080145e4c0?rule=$_57.JPG")!),
     Item(timestamp: Date(), name: "Algorithms Book", price: 10, imageURL: URL(string: "https://m.media-amazon.com/images/I/61Pgdn8Ys-L._AC_UF1000,1000_QL80_.jpg")!),
     Item(timestamp: Date(), name: "Data Structure Book", price: 17, imageURL: URL(string: "https://images.manning.com/book/e/59c8b18-b8fd-4d32-939b-25dcbb4d525d/Rocca-ADS-HI.png")!),
     Item(timestamp: Date(), name: "Hermen Miller Aeron", price: 499, imageURL: URL(string: "https://eustore.hermanmiller.com/cdn/shop/products/01-Herman_Miller-Aeron-Graphite-Standard_c6cf5f4e-ce52-488c-a547-56712bdfcf58_460x540_crop_center.jpg?v=1650967846")!),
     Item(timestamp: Date(), name: "IKEA DESK", price: 120, imageURL: URL(string: "https://media.karousell.com/media/photos/products/2022/8/11/ikea_norden_table_1660183841_1e0802c0_thumbnail")!),
     Item(timestamp: Date(), name: "David Yurman Ring", price: 400, imageURL: URL(string: "https://images.bloomingdalesassets.com/is/image/BLM/products/1/optimized/9828151_fpx.tif?op_sharpen=1&wid=700&fit=fit,1&$filtersm$")!)
    ]
    
}








#Preview {
    ContentView()
        .modelContainer(for: Item.self, inMemory: true)
}
