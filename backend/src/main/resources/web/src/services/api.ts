import axios from 'axios'

const baseURL = '/api/v1'

// Create axios instance for API requests
const api = axios.create({
  baseURL,
  headers: {
    'Content-Type': 'application/json'
  }
})

// Projects API
export const projectsAPI = {
  getFeatured: () => api.get('/projects/featured'),
  getAll: () => api.get('/projects'),
  getById: (id: string) => api.get(`/projects/${id}`),
  create: (project: any) => api.post('/projects', project),
  update: (id: string, project: any) => api.put(`/projects/${id}`, project),
  delete: (id: string) => api.delete(`/projects/${id}`)
}

// Blog API
export const blogAPI = {
  getPublished: (page = 1, size = 10) => api.get(`/blog?page=${page}&size=${size}`),
  getBySlug: (slug: string) => api.get(`/blog/${slug}`),
  getByTag: (tag: string, page = 1, size = 10) => api.get(`/blog/tag/${tag}?page=${page}&size=${size}`),
  getAll: () => api.get('/blog/all'),
  create: (post: any) => api.post('/blog', post),
  update: (id: string, post: any) => api.put(`/blog/${id}`, post),
  delete: (id: string) => api.delete(`/blog/${id}`)
}

// Services API
export const servicesAPI = {
  getFeatured: () => api.get('/services/featured'),
  getAll: () => api.get('/services'),
  getById: (id: string) => api.get(`/services/${id}`),
  create: (service: any) => api.post('/services', service),
  update: (id: string, service: any) => api.put(`/services/${id}`, service),
  delete: (id: string) => api.delete(`/services/${id}`)
}

// Auth API
export const authAPI = {
  login: (credentials: { username: string, password: string }) => api.post('/auth/login', credentials),
  logout: () => api.post('/auth/logout'),
  getCurrentUser: () => api.get('/auth/user'),
  register: (userData: any) => api.post('/auth/register', userData)
}

// Setup interceptors for handling auth errors
api.interceptors.response.use(
  response => response,
  error => {
    if (error.response && error.response.status === 401) {
      // Redirect to login or refresh token
      console.error('Authentication error')
    }
    return Promise.reject(error)
  }
)

export default api 