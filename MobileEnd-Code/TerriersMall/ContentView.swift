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
    
    @Query private var allItems: [Item]  // Rename this to allItems
    @State private var searchText = ""  // State variable for search text

    // Computed property to filter items based on search text
    var searchedItems: [Item] {
        if searchText.isEmpty {
            return items
        } else {
            return items.filter { $0.name.localizedCaseInsensitiveContains(searchText) }
        }
    }

    
    
    let columns: [GridItem] = [
        GridItem(.flexible(minimum: 200, maximum: 200)),
        GridItem(.flexible(minimum: 200, maximum: 200))
    ]
    
    @State var isChat = false
    
    var body: some View {
        NavigationView{
            ScrollView {
                TextField("Search items", text: $searchText)
                    .padding()
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .padding(.horizontal)
                LazyVGrid(columns: columns){
                    ForEach(searchedItems) { item in
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
                                if item.selectedImageData != nil {
                                    Image(uiImage: UIImage(data: item.selectedImageData!)!)
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
                .background(Color("GrayColor"))
            }
            
        }
        .scrollIndicators(.hidden)
        .accentColor(.red)
        .onAppear {
            addSamples()
        }
    }
     
    func addSamples() {
        modelContext.insert(Item(timestamp: Date(), name: "Used iPhone 14", price: 300, imageURL: URL(string: "https://apollo-singapore.akamaized.net/v1/files/0x9ciuzhgt44-IN/image;s=360x0")!, sentByUser: false))
        modelContext.insert(Item(timestamp: Date(), name: "Algorithms Book", price: 10, imageURL: URL(string: "https://m.media-amazon.com/images/I/61Pgdn8Ys-L._AC_UF1000,1000_QL80_.jpg")!, sentByUser: false))
        modelContext.insert(Item(timestamp: Date(), name: "Data Structure Book", price: 17, imageURL: URL(string: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQIuT1lwBI7DFZ74qgtMrXeQLIXq9wn9fdq3Q&usqp=CAU")!, sentByUser: false))
        modelContext.insert(Item(timestamp: Date(), name: "Hermen Miller Aeron", price: 499, imageURL: URL(string: "https://hips.hearstapps.com/hmg-prod/images/hm-1644256971.jpeg?crop=1.00xw:0.669xh;0,0.247xh&resize=640:*")!, sentByUser: false))
        modelContext.insert(Item(timestamp: Date(), name: "BU water bottle", price: 120, imageURL: URL(string: "https://images.footballfanatics.com/boston-university/bu-24-oz-stainless-water-bottle_ss10_p-100425504+u-g3grtlwag5aerq0j0oqr+v-us8azevkiq6zqrv6cnxj.jpg?_hv=2")!, sentByUser: false))
        modelContext.insert(Item(timestamp: Date(), name: "BU sweater", price: 40, imageURL: URL(string: "data:image/jpeg;base64,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")!, sentByUser: false))
    }
    
    
    
    
}








#Preview {
    ContentView()
        .modelContainer(for: Item.self, inMemory: true)
}
