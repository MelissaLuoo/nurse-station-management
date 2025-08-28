<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()
const nurse_list = ref([])
const backendData = ref('')

async function getAllNurses() {
  try {
    const response = await axios.get('http://localhost:8080/nurses')
    backendData.value = 'getAllNurses OK'
    nurse_list.value = response.data
    console.log('nurse.List:', response.data)
  } catch (error) {
    backendData.value = 'nurse.List get error'
    console.error('nurse.List get error:', error)
  }
}
async function del_nurse(nurseID) {
  try {
    const response = await axios.delete(`http://localhost:8080/nurses/${nurseID}`)
    backendData.value = 'del_nurse' + nurseID + 'OK'
    console.log('del_nurse:', response.data)
  } catch (error) {
    backendData.value = 'del_nurse' + nurseID + 'error'
    console.error('del_nurse error:', error)
  }
  await getAllNurses()
}

onMounted(async () => {
  await getAllNurses()
})
</script>
<template>
  <main>
    <div class="flex flex-col min-h-lvh bg-zinc-600 items-center gap-y-6">
      <div class="txtlink text-3xl text-gray-300 underline bg-slate-700 w-80 mt-10 py-5">
        Nurse List
      </div>
      <div class="w-80 flex justify-end">
        <RouterLink to="/" class="btn-re txtlink text-xl">BACK</RouterLink>
      </div>
      <!-- <div class="text-sm">{{ backendData }}</div> -->
      <table class="w-2/3 justify-center border-separate table-border">
        <thead>
          <tr class="table-thr">
            <th class="table-border">ID.</th>
            <th class="table-border">EDIT TIME</th>
            <th class="table-border">edit</th>
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
            <td class="w-1/4">
              <div class="flex flex-row gap-x-2 table-border py-2 px-4 justify-center">
                <RouterLink
                  :to="{ name: 'NurseAdd', params: { nurseId: item.id } }"
                  class="table-btn"
                  >view
                </RouterLink>
                <button class="table-btn" @click="del_nurse(item.id)">delete</button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </main>
</template>
