//
//  LoginViewModel.swift
//  TerriersMall
//
//  Created by Huanzhou Wang on 12/10/23.
//

import Foundation
import RealmSwift

class LoginViewModel: ObservableObject {
    private var app: App = App(id: "terriersrestful-hvlwh") // Replace with your Realm App ID

    // Observable properties to track user state
    @Published var currentUser: User?
    @Published var isLoggedIn: Bool = false
    @Published var error: Error?

    init() {
        // Initialize with the current user if already logged in
        currentUser = app.currentUser
        isLoggedIn = currentUser != nil
    }

    func login(username: String, password: String) {
        app.login(credentials: .emailPassword(email: username, password: password)) { [weak self] result in
            DispatchQueue.main.async {
                switch result {
                case .failure(let error):
                    self?.error = error
                    self?.isLoggedIn = false
                case .success(let user):
                    self?.currentUser = user
                    self?.isLoggedIn = true
                    self?.error = nil
                }
            }
        }
    }

    func logout() {
        guard let user = currentUser else { return }

        user.logOut { [weak self] (error) in
            DispatchQueue.main.async {
                if let error = error {
                    self?.error = error
                } else {
                    self?.currentUser = nil
                    self?.isLoggedIn = false
                }
            }
        }
    }
}
