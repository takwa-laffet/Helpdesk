import React from 'react';
import {
  Drawer,
  Box,
  Typography,
  IconButton,
  List,
  ListItem,
  ListItemText,
  ListItemIcon,
  ListItemAvatar,
  Avatar,
  Badge,
  Chip,
  Divider,
  Button,
} from '@mui/material';
import {
  Close,
  Notifications,
  Assignment,
  CheckCircle,
  Error,
  Info,
} from '@mui/icons-material';

const NotificationPanel = ({ open, onClose, notifications }) => {
  const getNotificationIcon = (type) => {
    if (!type) return <Notifications />;
    switch (type.toLowerCase()) {
      case 'ticket_created':
      case 'ticket_assigned':
        return <Assignment />;
      case 'ticket_resolved':
      case 'ticket_closed':
        return <CheckCircle />;
      case 'ticket_updated':
        return <Info />;
      default:
        return <Notifications />;
    }
  };

  const getNotificationColor = (type) => {
    if (!type) return 'default';
    switch (type.toLowerCase()) {
      case 'ticket_created':
      case 'ticket_assigned':
        return 'warning';
      case 'ticket_resolved':
      case 'ticket_closed':
        return 'success';
      case 'ticket_updated':
        return 'info';
      default:
        return 'default';
    }
  };

  return (
    <Drawer
      anchor="right"
      open={open}
      onClose={onClose}
      PaperProps={{
        sx: {
          width: 380,
          maxWidth: '90vw',
        },
      }}
    >
      <Box sx={{ p: 2 }}>
        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
          <Typography variant="h6">
            Notifications
            <Chip
              label={notifications.filter(n => !n.isRead).length}
              color="error"
              size="small"
              sx={{ ml: 1 }}
            />
          </Typography>
          <IconButton onClick={onClose} size="small">
            <Close />
          </IconButton>
        </Box>

        <Divider sx={{ mb: 2 }} />

        <Box sx={{ mb: 2 }}>
          <Button
            variant="outlined"
            size="small"
            fullWidth
            sx={{ mb: 1 }}
          >
            Mark all as read
          </Button>
          <Button
            variant="text"
            size="small"
            fullWidth
          >
            Clear all
          </Button>
        </Box>

        <List sx={{ maxHeight: 'calc(100vh - 200px)', overflow: 'auto' }}>
          {notifications.length === 0 ? (
            <ListItem>
              <ListItemText
                primary="No notifications"
                secondary="You're all caught up!"
              />
            </ListItem>
          ) : (
            notifications.map((notification) => (
              <ListItem
                key={notification.id}
                sx={{
                  mb: 1,
                  borderRadius: 1,
                  backgroundColor: notification.isRead ? 'transparent' : 'action.hover',
                  '&:hover': {
                    backgroundColor: 'action.selected',
                  },
                }}
              >
                <ListItemAvatar>
                  <Avatar
                    sx={{
                      bgcolor: `${getNotificationColor(notification.type)}.light`,
                      color: `${getNotificationColor(notification.type)}.dark`,
                    }}
                  >
                    {getNotificationIcon(notification.type)}
                  </Avatar>
                </ListItemAvatar>
                <ListItemText
                  primary={
                    <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                      <Typography variant="subtitle2" component="span">
                        {notification.title}
                      </Typography>
                      {!notification.isRead && (
                        <Badge
                          color="error"
                          variant="dot"
                          sx={{ '& .MuiBadge-badge': { top: 6, right: -8 } }}
                        />
                      )}
                    </Box>
                  }
                  secondary={
                    <>
                      <Typography variant="body2" color="text.secondary">
                        {notification.message}
                      </Typography>
                      <Typography variant="caption" color="text.secondary" sx={{ display: 'block', mt: 0.5 }}>
                        {new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                      </Typography>
                    </>
                  }
                />
              </ListItem>
            ))
          )}
        </List>
      </Box>
    </Drawer>
  );
};

export default NotificationPanel;