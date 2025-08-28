import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './index.css'
import Toast from 'vue-toastification'
import 'vue-toastification/dist/index.css'

const app = createApp(App)

app.use(router)
app.use(Toast, {
  position: 'top-left',
  timeout: 2990,
  transition: 'Vue-Toastification__fade',
  maxToasts: 4,
  newestOnTop: true,
})
app.mount('#app')
