import axios from "axios";

export default {

  getResourceList(params) {
    return new Promise((resolve, reject) => {
      axios.get("/api/bbs/resource/getList", {params})
          .then((res) => resolve(res))
          .catch((err) => reject(err));
    });
  },

  getCategorys(params) {
    return new Promise((resolve, reject) => {
      axios.get("/api/bbs/resource/getCategorys", {params})
          .then((res) => resolve(res))
          .catch((err) => reject(err));
    });
  },

  uploadResourceLogo(data) {
    return new Promise((resolve, reject) => {
      axios.post("/api/bbs/resource/uploadResourceLogo", data)
          .then((res) => resolve(res))
          .catch((err) => reject(err));
    });
  },

  resourceCreate(data) {
    return new Promise((resolve, reject) => {
      axios.post("/api/bbs/resource/create", data)
          .then((res) => resolve(res))
          .catch((err) => reject(err));
    });
  },

  resourceUpdate(data) {
    return new Promise((resolve, reject) => {
      axios.post("/api/bbs/resource/update", data)
          .then((res) => resolve(res))
          .catch((err) => reject(err));
    });
  },

  resourceDelete(data) {
    return new Promise((resolve, reject) => {
      axios.post("/api/bbs/resource/delete/" + data)
          .then((res) => resolve(res))
          .catch((err) => reject(err));
    });
  },

};
