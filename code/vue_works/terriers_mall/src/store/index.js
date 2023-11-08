import Vue from "vue";
import Vuex from "vuex";
import en_US from "@/i18n/en_US";
import utils from "@/config/utils";

const langs = {en_US};

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    isLogin: false,

    loginVisible: false,

    registerVisible: false,

    mobileResetPasswordVisible: false,

    emailResetPasswordVisible: false,

    userId: "",

    picture: "",

    isManage: false,

    articleCheck: "enable",

    userMaxLength: 10,

    colorOptions: ["#000000", "#3eaf7c", "#13c2c2", "#1869ff", "#722ed1", "#eb2f96"],

    themeColor: "#13c2c2",

    isCarousel: 1,

    collapsed: false,

    collapsedMax: false,

    width: 0,

    height: 0,

    locale: "en_US",

    systemNotifyCount: 0,

    taskNotifyCount: 0,

    manageDomain: ' ',

    translate: function (val) {

      if (!val) {
        return "";
      }
      const arr = val.split(".");
      let l = arr.length;
      let re;
      try {
        re = langs[this.locale];
        for (let i = 0; i < l; i++) {
          re = re[arr[i]];
        }
      } catch (err) {
        re = arr[l - 1];
      }
      return re || arr[l - 1];
    }
  },
  getters: {
    formCol(state) {
      if (state.width >= 500) {
        return {label: 6, wrapper: 16};
      }
      return {label: 8, wrapper: 16};
    },

    contentWidth(state) {
      if (state.collapsed) {
        return state.width - 120;
      }
      return state.width - 314;
    },
    isPc(state) {

      if (state.width > 750) {
        return true;
      }
      return false;
    },

    stateList(state) {
      return [
        {title: state.translate("common.enabled"), value: 1},
        {title: state.translate("common.disabled"), value: 0}
      ];
    }
  },
  mutations: {

    changeColor(state, color) {
      utils.updateTheme(color);
      window.localStorage.themeColor = color;
      state.themeColor = color;
    }
  },
  actions: {},
  modules: {}
});
