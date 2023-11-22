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
                    // Implement login logic here
                    print("Login button tapped")
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

                Spacer()
                Spacer()

                HStack {
                    Text("Don't have an account?")
                    NavigationLink(destination: Text("Sign Up View")) {
                        Text("Sign Up")
                            .fontWeight(.semibold)
                    }
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






struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
