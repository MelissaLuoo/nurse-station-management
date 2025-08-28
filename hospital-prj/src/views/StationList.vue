<script setup>
import { ref, reactive, onMounted } from 'vue'
import axios from 'axios'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

const station_list = ref([])
const backendData = ref('')

async function getAllStations() {
  try {
    const response = await axios.get('http://localhost:8080/stations')
    backendData.value = response.data
    console.log('station_list:', response.data)
    station_list.value = response.data
  } catch (error) {
    backendData.value = 'station_list get error'
    console.error('station_list get error:', error)
  }
}

async function del_station(stationId) {
  try {
    const response = await axios.delete(`http://localhost:8080/stations/${stationId}`)
    backendData.value = 'del_station OK'
    console.log('del_station:', response.data)
    backendData.value = 'del_station' + stationId + 'OK'
  } catch (error) {
    backendData.value = 'del_station error'
    console.error('del_station error:', error)
  }
  getAllStations()
}

onMounted(async () => {
  await getAllStations()
})
</script>
<template>
  <main>
    <div class="flex flex-col min-h-lvh bg-zinc-600 items-center gap-y-6">
      <div class="txtlink text-3xl text-gray-300 underline bg-slate-700 w-80 mt-10 py-5">
        Station List
      </div>
      <div class="justify-center">
        <RouterLink to="/" class="btn-re txtlink text-xl">BACK</RouterLink>
      </div>
      <!-- <div class="w-1/2 text-sm justify-center">{{ backendData }}</div> -->
      <table class="w-2/3 justify-center border-separate table-border">
        <thead>
          <tr class="table-thr">
            <th class="table-border">Name</th>
            <th class="table-border">EDIT TIME</th>
            <th class="table-border">edit</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in station_list" :key="item.id">
            <td class="table-border w-1/6 p-2 whitespace-nowrap table-body">
              {{ item.name }}
            </td>
            <td class="table-border p-2 whitespace-nowrap table-body">
              {{ item.editTIME.replace('T', ' ') }}
            </td>
            <td class="w-1/4 table-border">
              <div class="flex flex-row gap-x-2 py-2 px-4 justify-center">
                <RouterLink
                  :to="{ name: 'StationEdit', params: { stationId: item.id } }"
                  class="table-btn"
                >
                  view
                </RouterLink>
                <button class="table-btn" @click="del_station(item.id)">delete</button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </main>
</template>
<style>
.table-thr {
  @apply text-slate-300 text-xl bg-zinc-800;
}

.table-body {
  @apply text-slate-300 text-xl text-center;
  @apply font-mono font-light;
}

.table-border {
  @apply border border-gray-400;
}

.table-btn {
  @apply text-zinc-800 bg-indigo-300 font-bold;
  @apply hover:bg-indigo-700 hover:text-slate-300;
  @apply px-2 rounded;
}
</style>
