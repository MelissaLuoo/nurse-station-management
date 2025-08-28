<script setup></script>
<template>
  <main>
    <div class="flex flex-col min-h-lvh bg-slate-700 items-center gap-y-11">
      <div class="txtlink text-5xl text-zinc-800 underline bg-slate-300 w-80 mt-10 py-3">
        BIONIME
      </div>
      <RouterLink to="/station_add" class="btn-router-link">STATION_ADD +</RouterLink>
      <RouterLink to="/station_list" class="btn-router-link">STATION_LIST</RouterLink>
      <RouterLink :to="{ name: 'NurseAdd', params: { nurseId: 0 } }" class="btn-router-link"
        >NURSE_ADD +</RouterLink
      >
      <RouterLink to="/nurse_list" class="btn-router-link">NURSE_LIST</RouterLink>
    </div>
  </main>
</template>
<style>
.txtlink {
  @apply text-center font-mono font-bold px-6 font-serif;
}

.btn-re {
  @apply py-1 h-auto rounded-md;
  @apply font-light;
  @apply bg-zinc-700 text-slate-300 ring-2 ring-gray-400;
}

.btn-router-link {
  @apply btn-re txtlink w-72 text-2xl;
}
</style>
