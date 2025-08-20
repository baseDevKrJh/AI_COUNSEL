import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

// Axios 인스턴스 생성
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 요청 인터셉터 - JWT 토큰 자동 추가
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 응답 인터셉터 - 토큰 만료 처리
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// 인증 API
export const authAPI = {
  login: (credentials) => api.post('/api/login', credentials),
  register: (userData) => api.post('/api/signup', userData),
};

// 상담 API
export const counselAPI = {
  getAllCounsels: () => api.get('/api/counsels'),
  getCounselById: (id) => api.get(`/api/counsels/${id}`),
  createCounsel: (counselData) => api.post('/api/counsels', counselData),
  analyzeCounsel: (id) => api.get(`/api/counsels/${id}/analysis`),
  predictNextCounsel: (customerId) => api.get(`/api/counsels/${customerId}/prediction`),
};

export default api;
