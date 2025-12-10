import api from './api';

export const notificationService = {
  // Get user notifications
  getUserNotifications: async (userId) => {
    const response = await api.get(`/notifications/user/${userId}`);
    return response.data;
  },

  // Get unread notifications
  getUnreadNotifications: async (userId) => {
    const response = await api.get(`/notifications/user/${userId}/unread`);
    return response.data;
  },

  // Get unread notification count
  getUnreadNotificationCount: async (userId) => {
    const response = await api.get(`/notifications/user/${userId}/unread-count`);
    return response.data;
  },

  // Mark notification as read
  markAsRead: async (notificationId) => {
    await api.patch(`/notifications/${notificationId}/read`);
  },

  // Mark all notifications as read
  markAllAsRead: async (userId) => {
    await api.patch(`/notifications/user/${userId}/read-all`);
  },

  // Delete notification
  deleteNotification: async (notificationId) => {
    await api.delete(`/notifications/${notificationId}`);
  },

  // Delete all notifications
  deleteAllNotifications: async (userId) => {
    await api.delete(`/notifications/user/${userId}`);
  },

  // For now, mock methods for ticket notifications
  sendTicketCreatedNotification: async (ticket) => {
    console.log('Ticket created notification:', ticket);
    // In a real app, this would call your backend API
  },

  sendTicketUpdatedNotification: async (ticket) => {
    console.log('Ticket updated notification:', ticket);
    // In a real app, this would call your backend API
  },

  sendTicketAssignedNotification: async (ticket, assignee) => {
    console.log('Ticket assigned notification:', ticket, assignee);
    // In a a real app, this would call your backend API
  },

  sendCommentAddedNotification: async (ticket, commenter, comment) => {
    console.log('Comment added notification:', ticket, commenter, comment);
    // In a real app, this would call your backend API
  },

  sendTicketResolvedNotification: async (ticket) => {
    console.log('Ticket resolved notification:', ticket);
    // In a real app, this would call your backend API
  },

  sendTicketClosedNotification: async (ticket) => {
    console.log('Ticket closed notification:', ticket);
    // In a real app, this would call your backend API
  },

  sendTicketReopenedNotification: async (ticket) => {
    console.log('Ticket reopened notification:', ticket);
    // In a real app, this would call your backend API
  },
};