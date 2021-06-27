import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import GAuth from 'vue-google-oauth2'

Vue.config.productionTip = false

Vue.use(GAuth, {
  clientId: '232184996385-jlgvk9t282s6tf0hl0maaqvu506elsi9.apps.googleusercontent.com', scope: 'email', prompt: 'consent', fetch_basic_profile: false
})

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
