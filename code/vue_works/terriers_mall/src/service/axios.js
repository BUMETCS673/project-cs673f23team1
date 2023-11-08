import axios from "axios";
import store from "@/store/index";

axios.defaults.timeout = 15000;
if (process.env.NODE_ENV === "production") {

}
export default (() => {


  axios.interceptors.request.use(
      function (config) {
        return config;
      },
      function (error) {
        return Promise.reject(error);
      }
  );


  axios.interceptors.response.use(
      function (response) {

        if (response.headers["x-user-picture"]) {
          store.state.picture = response.headers["x-user-picture"];
        }

        if (response.headers["x-system-notify-count"]) {
          store.state.systemNotifyCount = response.headers["x-system-notify-count"];
        }
        if (response.headers["x-task-notify-count"]) {
          store.state.taskNotifyCount = response.headers["x-task-notify-count"];
        }

        if (response.status === 200) {
          if (response.data) {
            if (response.data.code === 0) {
              return Promise.resolve(response.data);


            } else if (response.data.code === 302 && response.config.url !== '/api/bbs/user/getCurrentUserRights') {
              // window.location.href = response.data.data.target;
              store.state.isLogin = false;
              store.state.loginVisible = true;
              return Promise.reject(response.data);
            } else {
              throw response.data;
            }
          } else {
            throw response;
          }
        } else {

          return Promise.reject(response);
        }
      },
      function (error) {
        return Promise.reject(error);
      }
  );
})();
