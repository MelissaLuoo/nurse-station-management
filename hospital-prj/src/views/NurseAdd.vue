<script setup>
import { onMounted, ref, computed, reactive } from 'vue'
import axios from 'axios'
import { useToast } from 'vue-toastification'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()
const toast = useToast()

const arrowL = ref('<<<')
const arrowR = ref('>>>')

const allocated = ref([])
const un_allocated = ref([])
const station_list = ref([])
const backendData = ref('')
const nurseID = ref('')
const form = reactive({
  name: '',
  employeeID: '',
  stationIds: [],
})

const selectedStation = ref(null)
const selectStation = (station) => {
  selectedStation.value = station
}

const isAddDisabled = computed(() => {
  if (!selectedStation.value) {
    return true
  }
  return allocated.value.some((s) => s.id === selectedStation.value.id)
})

const isDeleteDisabled = computed(() => {
  if (!selectedStation.value) {
    return true
  }
  return un_allocated.value.some((s) => s.id === selectedStation.value.id)
})

const addStation = () => {
  if (selectedStation.value && !isAddDisabled.value) {
    const index = un_allocated.value.findIndex((s) => s.id === selectedStation.value.id)
    if (index > -1) {
      un_allocated.value.splice(index, 1)
      allocated.value.push(selectedStation.value)
      selectedStation.value = null
      form.stationIds = allocated.value.map((item) => item.id)
    }
  }
}
const deleteStation = () => {
  if (selectedStation.value && !isDeleteDisabled.value) {
    const index = allocated.value.findIndex((s) => s.id === selectedStation.value.id)
    if (index > -1) {
      allocated.value.splice(index, 1)
      un_allocated.value.push(selectedStation.value)
      selectedStation.value = null
      form.stationIds = allocated.value.map((item) => item.id)
    }
  }
}
async function editNurse() {
  const newNurseData = {
    name: form.name,
    employeeID: form.employeeID,
    stationIds: form.stationIds,
  }
  if (!newNurseData.name) {
    console.error('NULL name')
    status_info('NULL name')
    return
  }
  if (!newNurseData.employeeID) {
    console.error('NULL employeeID')
    status_info('NULL employeeID')
    return
  }
  let response
  console.log('emp_id' + newNurseData.employeeID)
  try {
    JSON.stringify(newNurseData)
    if (nurseID.value == 0) {
      response = await axios.post('http://localhost:8080/nurses', newNurseData)
    } else {
      response = await axios.put(`http://localhost:8080/nurses/${nurseID.value}`, newNurseData)
    }
    console.log('editNurse:', response.data)
    router.push('/')
  } catch (error) {
    status_info('editNurse error')
    console.error('editNurse error:', error)
  }
}
async function getAllStations(nID) {
  try {
    const response = await axios.get('http://localhost:8080/stations')
    station_list.value = response.data
    status_info('getAllStations OK')
    console.log('getAllStations:', response.data)
    loadData(nID)
  } catch (error) {
    status_info('getAllStations get error')
    console.error('getAllStations get error:', error)
  }
}
async function getNurse_Stations(nurseID) {
  try {
    const response = await axios.get(`http://localhost:8080/nurses/${nurseID}/stations`)
    console.log('getNurse_Stations:', response.data)
    const nurseStations = response.data
    allocated.value = fetchData(nurseStations)
    status_info('getNurse_Stations OK')
  } catch (error) {
    status_info('getNurse_Stations error')
    console.error('getNurse_Stations error:', error)
  }
}
async function getNurse(nurseID) {
  try {
    const response = await axios.get(`http://localhost:8080/nurses/${nurseID}`)
    const nurseData = response.data
    form.name = nurseData.name
    form.employeeID = nurseData.employeeID
    status_info('getNurse OK')
    console.log('station_list:', response.data)
  } catch (error) {
    status_info('getNurse error')
    console.error('getNurse error:', error)
  }
}
const fetchData = (listARR) => {
  return listARR.map((item) => ({
    id: item.id,
    name: item.name,
  }))
}
async function loadData(nID) {
  nurseID.value = nID
  if (nID == 0) {
    un_allocated.value = fetchData(station_list.value)
  } else {
    await getNurse(nID)
    await getNurse_Stations(nID)
    form.stationIds = allocated.value.map((item) => item.id)
    const stationSet = new Set(form.stationIds)
    const unallocated_stations = station_list.value.filter((item) => !stationSet.has(item.id))
    un_allocated.value = fetchData(unallocated_stations)
  }
}
async function status_info(str) {
  backendData.value = str
}
onMounted(async () => {
  const nID = route.params.nurseId
  getAllStations(nID)
})
</script>
<template>
  <main>
    <div class="flex flex-col min-h-lvh bg-zinc-600 items-center gap-y-6">
      <div class="txtlink text-3xl text-gray-300 underline bg-slate-700 w-80 mt-10 py-5">
        Nurse Edit
      </div>
      <div class="w-64 justify-center flex flex-row gap-x-4 text-xl">
        <RouterLink :to="nurseID === '0' ? '/' : '/nurse_list'" class="btn-re txtlink">
          BACK
        </RouterLink>
        <button class="btn-re txtlink" @click="editNurse">
          {{ nurseID === '0' ? 'ADD' : 'SAVE' }}
        </button>
      </div>

      <div class="w-72 flex flex-col justify-end">
        <label class="px-4 text-2xl font-sans font-semibold text-black">Nurse ID</label>
        <input
          type="text"
          v-model="form.employeeID"
          class="rounded p-2 font-mono bg-zinc-500 text-xl text-white"
          placeholder="form.employeeID"
        />
        <label class="px-4 text-2xl font-sans font-semibold text-black mt-2">Nurse name</label>
        <input
          type="text"
          v-model="form.name"
          class="rounded p-2 font-mono bg-zinc-500 text-xl text-white"
          placeholder="form.name"
        />
      </div>

      <!-- <div class="w-96 text-2xl font-sans text-slate-300 flex justify-center">
        assignment station
      </div> -->
      <!-- <div>{{ backendData }}</div> -->
      <div class="w-96 grid grid-cols-3 text-center">
        <div class="font-sans font-semibold">[allocated]</div>
        <div>station</div>
        <div class="font-sans font-semibold">[unallocated]</div>
      </div>

      <div class="w-1/2 h-40 grid grid-cols-3 text-center gap-x-1">
        <div class="ring-2 ring-gray-800 font-sans font-semibold overflow-y-auto">
          <ul class="bg-slate-700 px-2">
            <li
              v-for="station in allocated"
              :key="station.id"
              class="text-slate-300 text-xl py-1 cursor-pointer"
              :class="{
                'bg-blue-600': selectedStation && selectedStation.id === station.id,
                'hover:bg-blue-500': !(selectedStation && selectedStation.id === station.id),
              }"
              @click="selectStation(station)"
            >
              {{ station.name }}
            </li>
          </ul>
        </div>

        <div class="grid grid-rows-2 gap-2 place-self-center h-fit">
          <button
            :disabled="isAddDisabled"
            class="table-btn h-fit"
            :class="{ 'opacity-50 cursor-not-allowed': isAddDisabled }"
            @click="addStation"
          >
            {{ arrowL }} add
          </button>
          <button
            :disabled="isDeleteDisabled"
            @click="deleteStation"
            class="table-btn h-fit"
            :class="{ 'opacity-50 cursor-not-allowed': isDeleteDisabled }"
          >
            delete {{ arrowR }}
          </button>
        </div>

        <div class="ring-2 ring-gray-800 font-sans font-semibold overflow-y-auto">
          <ul class="bg-slate-700">
            <li
              v-for="station in un_allocated"
              :key="station.id"
              class="text-slate-300 text-xl py-1 cursor-pointer"
              :class="{
                'bg-blue-600': selectedStation && selectedStation.id === station.id,
                'hover:bg-blue-500': !(selectedStation && selectedStation.id === station.id),
              }"
              @click="selectStation(station)"
            >
              {{ station.name }}
            </li>
          </ul>
        </div>
      </div>
    </div>
  </main>
</template>
