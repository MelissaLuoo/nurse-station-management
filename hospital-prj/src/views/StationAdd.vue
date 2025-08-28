<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()
const add_name = ref('')
const backendData = ref('')

async function addStation() {
  const newData = {
    name: add_name.value,
  }
  if (!add_name.value) {
    console.error('NULL name')
    backendData.value = 'NULL name'
    return
  }
  try {
    // JSON.stringify(newName)
    const response = await axios.post('http://localhost:8080/stations', newData)
    console.log('addStation:', response.data)
    router.push('/')
  } catch (error) {
    backendData.value = 'addStation post error'
    console.error('addStation post error:', error)
  }
}
</script>
<template>
  <main>
    <div class="flex flex-col min-h-lvh bg-zinc-600 items-center gap-y-6">
      <div class="txtlink text-3xl text-gray-300 underline bg-slate-700 w-80 mt-10 py-5">
        Station Add
      </div>
      <div class="flex flex-row gap-x-4 text-xl">
        <RouterLink to="/" class="btn-re txtlink">BACK</RouterLink>
        <button class="btn-re txtlink" @click="addStation">SAVE</button>
      </div>
      <div>
        <label class="px-4 text-2xl font-sans text-slate-300">Station Name ></label>
        <input
          type="text"
          v-model="add_name"
          class="rounded p-2 font-mono bg-zinc-500 text-xl text-white"
          placeholder="eg. taichung-A"
        />
      </div>
      <div class="text-sm">{{ backendData }}</div>
    </div>
  </main>
</template>
