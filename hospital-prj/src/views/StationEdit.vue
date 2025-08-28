<script setup>
import { onMounted, ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import axios from 'axios'
const router = useRouter()
const route = useRoute()

const station_name = ref('')
const nurse_list = ref([])
const backendData = ref('')
const stationId = ref('')

async function getAllNurses(sID) {
  stationId.value = sID
  try {
    const response = await axios.get(`http://localhost:8080/stations/${sID}`)
    console.log('getStationName:', response.data)
    station_name.value = response.data
  } catch (error) {
    backendData.value = 'getStationName error'
    console.error('getStationName error:', error)
  }

  try {
    const response = await axios.get(`http://localhost:8080/stations/${sID}/nurses`)
    backendData.value = 'getAllNurses OK'
    console.log('nurse_list:', response.data)
    nurse_list.value = response.data
  } catch (error) {
    backendData.value = 'nurse_list get error'
    console.error('nurse_list get error:', error)
  }
}
async function update() {
  const newData = {
    name: station_name.value,
  }
  try {
    const response = await axios.put(`http://localhost:8080/stations/${stationId.value}`, newData)
    console.log('station_update:', response.data)
    backendData.value = 'station_update OK'
    router.push('/station_list')
  } catch (error) {
    backendData.value = 'station_update put error'
    console.error('station_update put error', error)
  }
}
onMounted(() => {
  // Get stationId from the URL parameters
  const sID = route.params.stationId
  if (sID) {
    getAllNurses(sID)
  }
})
</script>
<template>
  <div class="flex flex-col min-h-lvh bg-zinc-600 items-center gap-y-4">
    <div class="txtlink text-3xl text-gray-300 underline bg-slate-700 w-80 mt-10 py-5">
      Station Edit
    </div>
    <div class="flex flex-row justify-center gap-x-4 text-xl">
      <RouterLink to="/station_list" class="btn-re txtlink">BACK</RouterLink>
      <button class="btn-re txtlink" @click="update">SAVE</button>
    </div>
    <!-- <div class="w-1/2 text-sm justify-center">{{ backendData }}</div> -->
    <div class="w-64 justify-center flex flex-col mt-4">
      <label class="text-center text-2xl font-sans font-semibold text-black">Station Name</label>
      <input
        type="text"
        v-model="station_name"
        class="rounded p-2 font-mono bg-zinc-500 text-xl text-white"
      />
    </div>
    <div class="w-2/3 justify-center text-2xl font-sans font-semibold">
      <div class="px-4">Nurses ID List</div>
      <table class="w-full border-separate table-border">
        <thead>
          <tr class="table-thr">
            <th class="table-border">ID</th>
            <th class="table-border">EDIT TIME</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in nurse_list" :key="item.id">
            <td class="table-border w-1/6 p-2 whitespace-nowrap table-body">
              {{ item.employeeID }}
            </td>
            <td class="table-border p-2 whitespace-nowrap table-body">
              {{ item.editTIME.replace('T', ' ') }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
