//
//  LoginPage.swift
//  TerriersMall
//
//  Created by Huanzhou Wang on 11/9/23.
//

import SwiftUI

struct LoginPage: View {
    @State private var username: String = ""
    @State private var password: String = ""
    @Environment(\.dismiss) private var dismiss
    @StateObject private var loginViewModel = LoginViewModel() // ViewModel instance

    @State private var showingLoginError = false

    var body: some View {
        NavigationView {
            VStack {
                Text("Login Account")
                    .font(.largeTitle)
                    .fontWeight(.semibold)
                    .padding()
                Spacer()
                Image("BUDog")
                    .padding(.bottom)
                TextField("Username", text: $username)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .padding()

                SecureField("Password", text: $password)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .padding()
                
                Button(action: {
                    // Call the login method
                    loginViewModel.login(username: username, password: password)

                    // Check the login status after a brief delay
                    DispatchQueue.main.asyncAfter(deadline: .now() + 1.0) {
                        if loginViewModel.isLoggedIn {
                            dismiss()
                        } else if let error = loginViewModel.error {
                            showingLoginError = true
                            print("Login error: \(error.localizedDescription)")
                        }
                    }
                }) {
                    Text("Login")
                        .frame(minWidth: 0, maxWidth: .infinity)
                        .frame(height: 50)
                        .foregroundColor(.white)
                        .font(.system(size: 18, weight: .bold))
                        .background(Color.red)
                        .cornerRadius(25)
                }
                .padding()
                .alert(isPresented: $showingLoginError) {
                    Alert(title: Text("Login Failed"), message: Text(loginViewModel.error?.localizedDescription ?? "Unknown error"), dismissButton: .default(Text("OK")))
                }

                Spacer()
                Spacer()

                HStack {
                    Text("Don't have an account?")
//                    NavigationLink(<#LocalizedStringKey#>) {
                        Text("Sign Up")
                            .fontWeight(.semibold)
//                    }
                }
            }
            .padding()
        }
        .accentColor(.red)
    }
}

#Preview {
    LoginPage()
}



