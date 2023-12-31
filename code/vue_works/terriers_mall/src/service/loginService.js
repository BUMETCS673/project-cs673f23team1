import axios from "axios"

export default {
    
    register(data) {
        return new Promise((resolve, reject) => {
            axios.post("/api/bbs/sso/register", data)
                .then(res => resolve(res))
                .catch(err => reject(err));
        });
    },
    
    login(data) {
        return new Promise((resolve, reject) => {
            axios.post("/api/bbs/sso/login", data)
                .then(res => resolve(res))
                .catch(err => reject(err));
        });
    },
    
    logout(data) {
        return new Promise((resolve, reject) => {
            axios.get("/api/bbs/sso/logout", data)
                .then(res => resolve(res))
                .catch(err => reject(err))
        })
    }
}