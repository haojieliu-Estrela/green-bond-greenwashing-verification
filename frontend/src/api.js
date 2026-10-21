import axios from 'axios'

const api = axios.create({
  baseURL: '/api'
})

export const getModelInfo = () => api.get('/config/model')
export const chat = (message) => api.post('/chat', { message })
export const verify = (content) => api.post('/verify', { content })
