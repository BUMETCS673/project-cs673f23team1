//
//  ChatPage.swift
//  TerriersMall
//
//  Created by Huanzhou Wang on 12/5/23.
//

import SwiftUI

struct Message: Identifiable {
    let id = UUID()
    let text: String
    let isSentByCurrentUser: Bool
}

struct ChatPage: View {
    @State private var messages: [Message] = [
        Message(text: "Is it available?", isSentByCurrentUser: true),
        Message(text: "Yes!", isSentByCurrentUser: false),
        Message(text: "Can you do 250?", isSentByCurrentUser: true),
        Message(text: "No way!!!", isSentByCurrentUser: false)
        // Add more messages here
    ]
    @State private var messageText: String = ""
    let chatPartnerName = "Huanzhou Wang" // Specify the name of the chat partner

    var body: some View {
        NavigationView {
            VStack {
                List {
                    ForEach(messages) { message in
                        HStack {
                            if message.isSentByCurrentUser {
                                Spacer()
                                Text(message.text)
                                    .padding()
                                    .background(Color.red)
                                    .foregroundColor(.white)
                                    .cornerRadius(15)
                            } else {
                                Text(message.text)
                                    .padding()
                                    .background(Color.gray.opacity(0.2))
                                    .cornerRadius(15)
                                Spacer()
                            }
                        }
                    }
                }
                .listStyle(PlainListStyle())

                HStack {
                    TextField("Type a message", text: $messageText)
                        .textFieldStyle(RoundedBorderTextFieldStyle())

                    Button(action: sendMessage) {
                        Image(systemName: "arrow.up.circle.fill")
                            .resizable()
                            .frame(width: 40, height: 40)
                            .foregroundColor(.red)
                    }
                }
                .padding()
            }
            .navigationBarTitle(Text(chatPartnerName), displayMode: .inline)
        }
    }

    private func sendMessage() {
        if !messageText.isEmpty {
            let newMessage = Message(text: messageText, isSentByCurrentUser: true)
            messages.append(newMessage)
            messageText = ""
            // Scroll to the new message if needed
        }
    }
}



#Preview {
    ChatPage()
}
