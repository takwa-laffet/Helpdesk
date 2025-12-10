import api from './api';

export const dashboardService = {
  // Get dashboard statistics
  getDashboardStats: async (userId = null) => {
    const params = userId ? { userId } : {};
    const response = await api.get('/dashboard/stats', { params });
    return response.data;
  },
};