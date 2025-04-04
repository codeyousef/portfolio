<template>
  <div>
    <!-- Hero Section -->
    <section class="bg-gradient-to-r from-primary-700 to-primary-900 text-white py-16 md:py-24">
      <div class="container">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-10 items-center">
          <div>
            <h1 class="text-4xl md:text-5xl lg:text-6xl font-bold mb-4">
              Software Developer & Consultant
            </h1>
            <p class="text-xl mb-8 text-gray-100">
              Building innovative web applications with modern technologies and clean architecture.
            </p>
            <div class="flex flex-wrap gap-4">
              <router-link to="/projects" class="btn bg-white text-primary-700 hover:bg-gray-100">
                View My Work
              </router-link>
              <router-link to="/contact" class="btn border border-white text-white hover:bg-white/10">
                Contact Me
              </router-link>
            </div>
          </div>
          <div class="hidden md:block">
            <img src="../assets/images/hero-image.svg" alt="Hero Illustration" class="max-w-full h-auto">
          </div>
        </div>
      </div>
    </section>

    <!-- Featured Projects Section -->
    <section class="section bg-gray-50">
      <div class="container">
        <div class="text-center mb-12">
          <h2 class="text-3xl md:text-4xl font-bold mb-4">Featured Projects</h2>
          <p class="text-gray-600 max-w-2xl mx-auto">
            Check out some of my recent work that showcases my skills and expertise.
          </p>
        </div>
        
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          <!-- Project cards will be loaded from API -->
          <div class="card group">
            <div class="relative overflow-hidden aspect-video">
              <img src="https://via.placeholder.com/600x400" alt="Project" class="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105">
            </div>
            <div class="p-6">
              <h3 class="text-xl font-bold mb-2">Project Title</h3>
              <p class="text-gray-600 mb-4">A brief description of the project showcasing the main features and technologies used.</p>
              <div class="flex flex-wrap gap-2 mb-4">
                <span class="px-2 py-1 bg-gray-100 text-gray-800 text-sm rounded">Vue.js</span>
                <span class="px-2 py-1 bg-gray-100 text-gray-800 text-sm rounded">TypeScript</span>
                <span class="px-2 py-1 bg-gray-100 text-gray-800 text-sm rounded">Tailwind</span>
              </div>
              <router-link to="/projects/1" class="text-primary-600 font-medium hover:text-primary-700">View Details →</router-link>
            </div>
          </div>
          
          <!-- More placeholder project cards -->
          <div class="card group">
            <div class="relative overflow-hidden aspect-video">
              <img src="https://via.placeholder.com/600x400" alt="Project" class="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105">
            </div>
            <div class="p-6">
              <h3 class="text-xl font-bold mb-2">Project Title</h3>
              <p class="text-gray-600 mb-4">A brief description of the project showcasing the main features and technologies used.</p>
              <div class="flex flex-wrap gap-2 mb-4">
                <span class="px-2 py-1 bg-gray-100 text-gray-800 text-sm rounded">React</span>
                <span class="px-2 py-1 bg-gray-100 text-gray-800 text-sm rounded">Node.js</span>
                <span class="px-2 py-1 bg-gray-100 text-gray-800 text-sm rounded">MongoDB</span>
              </div>
              <router-link to="/projects/2" class="text-primary-600 font-medium hover:text-primary-700">View Details →</router-link>
            </div>
          </div>
          
          <div class="card group">
            <div class="relative overflow-hidden aspect-video">
              <img src="https://via.placeholder.com/600x400" alt="Project" class="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105">
            </div>
            <div class="p-6">
              <h3 class="text-xl font-bold mb-2">Project Title</h3>
              <p class="text-gray-600 mb-4">A brief description of the project showcasing the main features and technologies used.</p>
              <div class="flex flex-wrap gap-2 mb-4">
                <span class="px-2 py-1 bg-gray-100 text-gray-800 text-sm rounded">Angular</span>
                <span class="px-2 py-1 bg-gray-100 text-gray-800 text-sm rounded">Firebase</span>
                <span class="px-2 py-1 bg-gray-100 text-gray-800 text-sm rounded">SCSS</span>
              </div>
              <router-link to="/projects/3" class="text-primary-600 font-medium hover:text-primary-700">View Details →</router-link>
            </div>
          </div>
        </div>
        
        <div class="mt-12 text-center">
          <router-link to="/projects" class="btn btn-primary">View All Projects</router-link>
        </div>
      </div>
    </section>

    <!-- Services Section -->
    <section class="section bg-white">
      <div class="container">
        <div class="text-center mb-12">
          <h2 class="text-3xl md:text-4xl font-bold mb-4">Services</h2>
          <p class="text-gray-600 max-w-2xl mx-auto">
            Expert services tailored to your needs, from web development to consulting.
          </p>
        </div>
        
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          <!-- Service cards will be loaded from API -->
          <div class="card text-center p-8 hover:shadow-lg transition-shadow">
            <div class="inline-flex items-center justify-center w-16 h-16 bg-primary-100 text-primary-600 rounded-full mb-4">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 20l4-16m4 4l4 4-4 4M6 16l-4-4 4-4" />
              </svg>
            </div>
            <h3 class="text-xl font-bold mb-3">Web Development</h3>
            <p class="text-gray-600 mb-4">Custom web applications built with modern frameworks and best practices.</p>
            <router-link to="/services/1" class="text-primary-600 font-medium hover:text-primary-700">Learn More →</router-link>
          </div>
          
          <div class="card text-center p-8 hover:shadow-lg transition-shadow">
            <div class="inline-flex items-center justify-center w-16 h-16 bg-primary-100 text-primary-600 rounded-full mb-4">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 18h.01M8 21h8a2 2 0 002-2V5a2 2 0 00-2-2H8a2 2 0 00-2 2v14a2 2 0 002 2z" />
              </svg>
            </div>
            <h3 class="text-xl font-bold mb-3">Mobile App Development</h3>
            <p class="text-gray-600 mb-4">Native and cross-platform mobile applications for iOS and Android.</p>
            <router-link to="/services/2" class="text-primary-600 font-medium hover:text-primary-700">Learn More →</router-link>
          </div>
          
          <div class="card text-center p-8 hover:shadow-lg transition-shadow">
            <div class="inline-flex items-center justify-center w-16 h-16 bg-primary-100 text-primary-600 rounded-full mb-4">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.75 17L9 20l-1 1h8l-1-1-.75-3M3 13h18M5 17h14a2 2 0 002-2V5a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
              </svg>
            </div>
            <h3 class="text-xl font-bold mb-3">UI/UX Design</h3>
            <p class="text-gray-600 mb-4">User-centered design with beautiful interfaces and intuitive experiences.</p>
            <router-link to="/services/3" class="text-primary-600 font-medium hover:text-primary-700">Learn More →</router-link>
          </div>
        </div>
        
        <div class="mt-12 text-center">
          <router-link to="/services" class="btn btn-primary">View All Services</router-link>
        </div>
      </div>
    </section>

    <!-- Blog Section -->
    <section class="section bg-gray-50">
      <div class="container">
        <div class="text-center mb-12">
          <h2 class="text-3xl md:text-4xl font-bold mb-4">Latest Articles</h2>
          <p class="text-gray-600 max-w-2xl mx-auto">
            Read my latest thoughts on technology, development, and best practices.
          </p>
        </div>
        
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          <!-- Blog post cards will be loaded from API -->
          <div class="card group">
            <div class="relative overflow-hidden aspect-video">
              <img src="https://via.placeholder.com/600x400" alt="Blog Post" class="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105">
              <div class="absolute top-4 left-4">
                <span class="px-3 py-1 bg-primary-600 text-white text-sm font-medium rounded-full">Development</span>
              </div>
            </div>
            <div class="p-6">
              <div class="flex items-center text-sm text-gray-500 mb-3">
                <span>April 15, 2023</span>
                <span class="mx-2">•</span>
                <span>5 min read</span>
              </div>
              <h3 class="text-xl font-bold mb-2">Modern Frontend Development Techniques</h3>
              <p class="text-gray-600 mb-4">Exploring the latest tools and methods in frontend development for building scalable applications.</p>
              <router-link to="/blog/modern-frontend" class="text-primary-600 font-medium hover:text-primary-700">Read Article →</router-link>
            </div>
          </div>
          
          <div class="card group">
            <div class="relative overflow-hidden aspect-video">
              <img src="https://via.placeholder.com/600x400" alt="Blog Post" class="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105">
              <div class="absolute top-4 left-4">
                <span class="px-3 py-1 bg-primary-600 text-white text-sm font-medium rounded-full">Architecture</span>
              </div>
            </div>
            <div class="p-6">
              <div class="flex items-center text-sm text-gray-500 mb-3">
                <span>March 28, 2023</span>
                <span class="mx-2">•</span>
                <span>7 min read</span>
              </div>
              <h3 class="text-xl font-bold mb-2">Clean Architecture in Modular Applications</h3>
              <p class="text-gray-600 mb-4">How to implement clean architecture principles in modular applications for better maintainability.</p>
              <router-link to="/blog/clean-architecture" class="text-primary-600 font-medium hover:text-primary-700">Read Article →</router-link>
            </div>
          </div>
          
          <div class="card group">
            <div class="relative overflow-hidden aspect-video">
              <img src="https://via.placeholder.com/600x400" alt="Blog Post" class="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105">
              <div class="absolute top-4 left-4">
                <span class="px-3 py-1 bg-primary-600 text-white text-sm font-medium rounded-full">Productivity</span>
              </div>
            </div>
            <div class="p-6">
              <div class="flex items-center text-sm text-gray-500 mb-3">
                <span>February 10, 2023</span>
                <span class="mx-2">•</span>
                <span>4 min read</span>
              </div>
              <h3 class="text-xl font-bold mb-2">Developer Productivity Hacks</h3>
              <p class="text-gray-600 mb-4">Essential tools and techniques to improve your workflow and productivity as a developer.</p>
              <router-link to="/blog/productivity-hacks" class="text-primary-600 font-medium hover:text-primary-700">Read Article →</router-link>
            </div>
          </div>
        </div>
        
        <div class="mt-12 text-center">
          <router-link to="/blog" class="btn btn-primary">Read All Articles</router-link>
        </div>
      </div>
    </section>

    <!-- CTA Section -->
    <section class="section bg-primary-700 text-white">
      <div class="container">
        <div class="text-center max-w-3xl mx-auto">
          <h2 class="text-3xl md:text-4xl font-bold mb-4">Ready to Start Your Project?</h2>
          <p class="text-xl mb-8">
            Let's work together to bring your ideas to life with cutting-edge technology and design.
          </p>
          <router-link to="/contact" class="btn bg-white text-primary-700 hover:bg-gray-100">Get in Touch</router-link>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
// The data will be fetched from the API in a real implementation
// This is just a placeholder
</script> 