import api from './api';

export const ticketService = {
  // Get all tickets
  getAllTickets: async () => {
    const response = await api.get('/tickets');
    return response.data;
  },

  // Get ticket by ID
  getTicketById: async (id) => {
    const response = await api.get(`/tickets/${id}`);
    return response.data;
  },

  // Create new ticket
  createTicket: async (ticketData) => {
    const response = await api.post('/tickets', ticketData);
    return response.data;
  },

  // Update ticket status
  updateTicketStatus: async (id, status, resolutionNotes = '') => {
    const response = await api.patch(`/tickets/${id}/status`, null, {
      params: { status, resolutionNotes },
    });
    return response.data;
  },

  // Assign ticket
  assignTicket: async (id, userId) => {
    const response = await api.patch(`/tickets/${id}/assign/${userId}`);
    return response.data;
  },

  // Get tickets by status
  getTicketsByStatus: async (status) => {
    const response = await api.get(`/tickets/status/${status}`);
    return response.data;
  },

  // Get user's tickets
  getUserTickets: async (userId) => {
    const response = await api.get(`/tickets/user/${userId}`);
    return response.data;
  },

  // Get assigned tickets
  getAssignedTickets: async (userId) => {
    const response = await api.get(`/tickets/assigned/${userId}`);
    return response.data;
  },

  // Delete ticket
  deleteTicket: async (id) => {
    await api.delete(`/tickets/${id}`);
  },
};