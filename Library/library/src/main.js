import Vue from 'vue'
import App from './App'
import uView from "uview-ui";
import dayjs from "dayjs"
import {
	loginCheck,getStatus
} from "utils/utils.js"
Vue.use(uView);
Vue.config.productionTip = false
App.mpType = 'app'

Vue.prototype.baseUrl = "http://localhost:8121"
Vue.prototype.appid = ""
Vue.prototype.loginCheck = loginCheck
Vue.prototype.getStatus = getStatus 
Vue.prototype.dayjs = dayjs;//可以全局使用dayjs
const app = new Vue({
	...App
})
app.$mount()
