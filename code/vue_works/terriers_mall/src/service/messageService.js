import axios from "axios";

export default {
    
    getMessageList(params) {
        return new Promise((resolve, reject) => {
            axios.get("/api/bbs/notify/getList", {params})
                .then(res => resolve(res))
                .catch(err => reject(err));
        });
    },
  
    makeAllRead(params) {
        return new Promise((resolve, reject) => {
            axios.post("/api/bbs/notify/haveRead", null, {params})
                .then(res => resolve(res))
                .catch(err => reject(err));
        });
    },
 
    markRead(data) {
        return new Promise((resolve, reject) => {
            axios.post("/api/bbs/notify/markRead", data)
                .then(res => resolve(res))
                .catch(err => reject(err));
        });
    }
};
