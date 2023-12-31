//
//  ChatBoxPage.swift
//  TerriersMall
//
//  Created by Huanzhou Wang on 12/6/23.
//

import SwiftUI

struct Conversation: Identifiable {
    let id = UUID()
    let imageName: String
    let title: String
    let subtitle: String
    let date: String
    let isRead: Bool
}

struct ChatBoxPage: View {
    
    @State private var isChat = false
    
    @State private var conversations: [Conversation] = [
        // Add your conversation data here
        Conversation(imageName: "macmini", title: "Hermen Miller Aeron", subtitle: "The seller changed the price...", date: "Dec 7", isRead: false),
        // ... more conversations
    ]
    
    var body: some View {
        
            List {
                ForEach(conversations) { conversation in
                    HStack {
                        Image(systemName: conversation.imageName) // Replace with your own image names or URLs
                            .resizable()
                            .frame(width: 50, height: 50)
                            .cornerRadius(8)
                        
                        VStack(alignment: .leading) {
                            Text(conversation.title)
                                .font(.headline)
                            Text(conversation.subtitle)
                                .font(.subheadline)
                                .foregroundColor(.gray)
                        }
                        
                        Spacer()
                        
                        VStack(alignment: .trailing) {
                            Text(conversation.date)
                                .font(.caption)
                                .foregroundColor(.gray)
                            if !conversation.isRead {
                                Circle()
                                    .frame(width: 10, height: 10)
                                    .foregroundColor(.blue)
                            }
                        }
                    }.onTapGesture {
                        isChat.toggle()
                    }                }
            }
        
        .sheet(isPresented: $isChat, content: {
            ChatPage()
        })
        
    }
}




#Preview {
    ChatBoxPage()
}
