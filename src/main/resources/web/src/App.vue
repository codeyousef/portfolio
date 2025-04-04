<template>
  <div class="min-h-screen bg-gray-50">
    <NavBar />
    <main>
      <router-view />
    </main>
    <AppFooter />
  </div>
</template>

<script setup lang="ts">
import NavBar from './components/layout/NavBar.vue'
import AppFooter from './components/layout/AppFooter.vue'
</script> 