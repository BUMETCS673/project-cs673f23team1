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
        List {
            ForEach(items) { item in
                if item.sentByUser {
                    VStack {
                        ItemImageView(item: item)
                            .frame(height: 200)
                            .padding(.vertical)

                        Text(item.name)
                            .font(.title2)
                            .padding([.top, .leading, .trailing])
                        
                        ItemDetailsView(item: item)
                            .padding(.horizontal)

                        SoldButtonView(item: item, action: { markAsSold(item: item) })
                            .padding()
                    }
                    .background(Color.white)
                    .cornerRadius(12)
                    .shadow(radius: 5)
                    .padding(.horizontal)
                }
            }
        }
        .listStyle(PlainListStyle())
    }

    func markAsSold(item: Item) {
        item.isSold = true
    }
}

struct ItemImageView: View {
    var item: Item

    var body: some View {
        if let imageData = item.selectedImageData, let image = UIImage(data: imageData) {
            Image(uiImage: image)
                .resizable()
                .scaledToFit()
        } else {
            AsyncImage(url: item.imageURL) { phase in
                switch phase {
                case .success(let image):
                    image.resizable().scaledToFit()
                case .failure(_):
                    Image(systemName: "photo")
                case .empty:
                    ProgressView()
                @unknown default:
                    EmptyView()
                }
            }
        }
    }
}

struct ItemDetailsView: View {
    var item: Item

    var body: some View {
        HStack {
            Text("$\(item.price)")
                .font(.headline)
                .foregroundColor(.green)
            Spacer()
            Text(item.isSold ? "Sold" : "Available")
                .foregroundColor(item.isSold ? .gray : .blue)
            Spacer()
            Text(item.timestamp.formatted(.dateTime))
                .font(.caption)
                .foregroundColor(.gray)
        }
    }
}

struct SoldButtonView: View {
    var item: Item
    var action: () -> Void

    var body: some View {
        Button(action: action) {
            Text(item.isSold ? "Sold on \(item.timestamp.formatted(.dateTime.day().month().year()))" : "Mark as sold")
                .fontWeight(.bold)
        }
        .padding()
        .frame(maxWidth: .infinity)
        .background(item.isSold ? Color.green : Color.red)
        .foregroundColor(.white)
        .cornerRadius(10)
    }
}

// Preview
struct ListingDetailView_Previews: PreviewProvider {
    static var previews: some View {
        ListingDetailView()
    }
}
