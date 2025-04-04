import axios from 'axios'
import { BlogPost, Project, Service, User } from '../models'

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
  getFeatured: () => api.get<Project[]>('/projects/featured'),
  getAll: () => api.get<Project[]>('/projects'),
  getById: (id: string) => api.get<Project>(`/projects/${id}`),
  create: (project: Omit<Project, 'id' | 'createdAt' | 'updatedAt'>) => api.post<Project>('/projects', project),
  update: (id: string, project: Partial<Omit<Project, 'id' | 'createdAt' | 'updatedAt'>>) => api.put<Project>(`/projects/${id}`, project),
  delete: (id: string) => api.delete(`/projects/${id}`)
}

// Blog API
export const blogAPI = {
  getPublished: (page = 1, size = 10) => api.get<{ content: BlogPost[], totalPages: number }>(`/blog?page=${page}&size=${size}`),
  getBySlug: (slug: string) => api.get<BlogPost>(`/blog/${slug}`),
  getByTag: (tag: string, page = 1, size = 10) => api.get<{ content: BlogPost[], totalPages: number }>(`/blog/tag/${tag}?page=${page}&size=${size}`),
  getAll: () => api.get<BlogPost[]>('/blog/all'),
  create: (post: Omit<BlogPost, 'id' | 'createdAt' | 'updatedAt'>) => api.post<BlogPost>('/blog', post),
  update: (id: string, post: Partial<Omit<BlogPost, 'id' | 'createdAt' | 'updatedAt'>>) => api.put<BlogPost>(`/blog/${id}`, post),
  delete: (id: string) => api.delete(`/blog/${id}`)
}

// Services API
export const servicesAPI = {
  getFeatured: () => api.get<Service[]>('/services/featured'),
  getAll: () => api.get<Service[]>('/services'),
  getById: (id: string) => api.get<Service>(`/services/${id}`),
  create: (service: Omit<Service, 'id' | 'createdAt' | 'updatedAt'>) => api.post<Service>('/services', service),
  update: (id: string, service: Partial<Omit<Service, 'id' | 'createdAt' | 'updatedAt'>>) => api.put<Service>(`/services/${id}`, service),
  delete: (id: string) => api.delete(`/services/${id}`)
}

// Auth API
export const authAPI = {
  login: (credentials: { username: string, password: string }) => api.post<{ token: string, user: User }>('/auth/login', credentials),
  logout: () => api.post('/auth/logout'),
  getCurrentUser: () => api.get<User>('/auth/user'),
  register: (userData: Omit<User, 'id' | 'createdAt' | 'updatedAt' | 'lastLogin'>) => api.post<User>('/auth/register', userData)
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