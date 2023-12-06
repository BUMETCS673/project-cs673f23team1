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
    
    @State var isChat = false
    
    var body: some View {
        NavigationView{
            ScrollView {
                LazyVGrid(columns: columns){
                    ForEach(ContentView.displayItems) { item in
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
                                Button(action: {
                                    isChat.toggle()
                                }, label: {
                                    Text("Interested")
                                })
                                .foregroundColor(.white)
                                .padding()
                                .background(Color.red)
                                .cornerRadius(25)
                                .sheet(isPresented: $isChat, content: {
                                    ChatPage()
                                })
                            }
                        } label: {
                            VStack{
                                AsyncImage(url: item.imageURL) { image in
                                    image
                                        .image?
                                        .resizable()
                                        .clipped()
                                        .frame(width: 200, height: 200)
                                        .overlay(content: {
                                            Text("$\(item.price)")
                                                .fontWeight(.bold)
                                                .padding(8)
                                                .background(.regularMaterial)
                                                .cornerRadius(25)
                                                .position(x:38,y:170)
                                        })
                                }
                                HStack{
                                    Text(item.name)
                                        .fontWeight(.semibold)
                                }
                                .padding()
                            }
                            .background(.white)
                            .cornerRadius(8)
                            .padding(.horizontal)

                        }
                    }
                }
                .padding()
                .navigationBarItems(leading:
                                        HStack{
                    NavigationLink {
                        LoginPage()
                    } label: {
                        Image(systemName: "person.fill")
                    }
                    NavigationLink {
                        ChatBoxPage()
                    } label: {
                        Image(systemName: "bubble.left.and.text.bubble.right.fill")
                    }
                }
                                    , trailing:
                                        HStack{
                    NavigationLink {
                        ListingDetailView()
                    } label: {
                        Image(systemName: "tag.fill")
                    }

                    NavigationLink {
                        PostPage()
                    } label: {
                        Image(systemName: "pencil.and.list.clipboard")
                        
                    }
                })
                .navigationTitle("Terriers Mall")
                .foregroundColor(.red)
                .scrollIndicators(.hidden)
                .background(Color("GrayColor"))
            }
            
        }
        .accentColor(.red)
    }
    
    static var displayItems: [Item] =
    [Item(timestamp: Date(), name: "Used iPhone 14", price: 300, imageURL: URL(string: "https://apollo-singapore.akamaized.net/v1/files/0x9ciuzhgt44-IN/image;s=360x0")!),
     Item(timestamp: Date(), name: "Algorithms Book", price: 10, imageURL: URL(string: "https://m.media-amazon.com/images/I/61Pgdn8Ys-L._AC_UF1000,1000_QL80_.jpg")!),
     Item(timestamp: Date(), name: "Data Structure Book", price: 17, imageURL: URL(string: "https://images.manning.com/book/e/59c8b18-b8fd-4d32-939b-25dcbb4d525d/Rocca-ADS-HI.png")!),
     Item(timestamp: Date(), name: "Hermen Miller Aeron", price: 499, imageURL: URL(string: "https://marathonbe.com/wp-content/uploads/2023/03/Black-Aeron-1.jpg")!),
     Item(timestamp: Date(), name: "IKEA DESK", price: 120, imageURL: URL(string: "https://media.karousell.com/media/photos/products/2022/8/11/ikea_norden_table_1660183841_1e0802c0_thumbnail")!),
     Item(timestamp: Date(), name: "David Yurman Ring", price: 400, imageURL: URL(string: "https://i.ebayimg.com/images/g/D~4AAOSwHZVgx6m8/s-l1200.webp")!)
    ]
    
}








#Preview {
    ContentView()
        .modelContainer(for: Item.self, inMemory: true)
}
