<template>
  <nav class="bg-white shadow-sm sticky top-0 z-50">
    <div class="container mx-auto px-4">
      <div class="flex justify-between h-16">
        <div class="flex items-center">
          <router-link to="/" class="flex-shrink-0 flex items-center">
            <span class="text-xl font-bold text-primary-600 font-heading">Yousef</span>
          </router-link>
        </div>
        
        <!-- Mobile menu button -->
        <div class="flex items-center md:hidden">
          <button 
            @click="isMobileMenuOpen = !isMobileMenuOpen" 
            class="inline-flex items-center justify-center p-2 rounded-md text-gray-500 hover:text-primary-600 hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-primary-500"
          >
            <span class="sr-only">Open main menu</span>
            <svg 
              v-if="!isMobileMenuOpen" 
              class="block h-6 w-6" 
              xmlns="http://www.w3.org/2000/svg" 
              fill="none" 
              viewBox="0 0 24 24" 
              stroke="currentColor" 
              aria-hidden="true"
            >
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
            </svg>
            <svg 
              v-else 
              class="block h-6 w-6" 
              xmlns="http://www.w3.org/2000/svg" 
              fill="none" 
              viewBox="0 0 24 24" 
              stroke="currentColor" 
              aria-hidden="true"
            >
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>
        
        <!-- Desktop menu -->
        <div class="hidden md:ml-6 md:flex md:items-center md:space-x-4">
          <router-link 
            v-for="item in navigationItems" 
            :key="item.name"
            :to="item.href"
            class="px-3 py-2 rounded-md text-sm font-medium hover:text-primary-600 hover:bg-gray-50 transition-colors"
            :class="{ 'text-primary-600': isActive(item.href), 'text-gray-700': !isActive(item.href) }"
          >
            {{ item.name }}
          </router-link>
        </div>
      </div>
    </div>
    
    <!-- Mobile menu -->
    <div v-if="isMobileMenuOpen" class="md:hidden">
      <div class="px-2 pt-2 pb-3 space-y-1">
        <router-link
          v-for="item in navigationItems"
          :key="item.name"
          :to="item.href"
          class="block px-3 py-2 rounded-md text-base font-medium hover:bg-gray-50 transition-colors"
          :class="{ 'text-primary-600': isActive(item.href), 'text-gray-700': !isActive(item.href) }"
          @click="isMobileMenuOpen = false"
        >
          {{ item.name }}
        </router-link>
      </div>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const isMobileMenuOpen = ref(false)

const navigationItems = [
  { name: 'Home', href: '/' },
  { name: 'Projects', href: '/projects' },
  { name: 'Blog', href: '/blog' },
  { name: 'Services', href: '/services' },
  { name: 'About', href: '/about' },
  { name: 'Contact', href: '/contact' }
]

const isActive = (path: string) => {
  if (path === '/') {
    return route.path === '/'
  }
  return route.path.startsWith(path)
}
</script> 