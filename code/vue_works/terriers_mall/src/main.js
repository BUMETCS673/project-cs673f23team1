// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';

import router from './router'
import store from './store'
import "@/assets/css/main.css"
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

import VueClipboards from 'vue-clipboard2'


Vue.config.productionTip = false

Vue.prototype.$utils = utils
Vue.prototype.$moment = moment

Vue.prototype.$t = store.state.translate.bind(store.state)

Vue.use(Antd);
Vue.use(mavonEditor)
Vue.use(VueClipboards);


/* eslint-disable no-new */
export default new Vue({
  router,
    store,
    render: h => h(App)
}).$mount('#app')