import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
  Box,
  Paper,
  Typography,
  Chip,
  Button,
  Grid,
  TextField,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Divider,
  List,
  ListItem,
  ListItemText,
  Avatar,
  IconButton,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Card,
  CardContent,
  LinearProgress,
  Alert,
  Tooltip,
} from '@mui/material';
import {
  ArrowBack,
  Edit,
  Delete,
  Assignment,
  CheckCircle,
  Comment as CommentIcon,
  AttachFile,
  Person,
  Schedule,
  PriorityHigh,
  Category,
  Label,
  Update,
  Close,
  Send,
} from '@mui/icons-material';
import { ticketService } from '../../services/ticketService';
import { userService } from '../../services/userService';
import { notificationService } from '../../services/notificationService';
import { toast } from 'react-hot-toast';
import dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';

dayjs.extend(relativeTime);

const TicketDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [ticket, setTicket] = useState(null);
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [updating, setUpdating] = useState(false);
  const [comment, setComment] = useState('');
  const [assignDialogOpen, setAssignDialogOpen] = useState(false);
  const [selectedUser, setSelectedUser] = useState('');
  const [status, setStatus] = useState('');
  const [comments, setComments] = useState([]);
  const [showCommentForm, setShowCommentForm] = useState(false);

  useEffect(() => {
    fetchTicket();
    fetchUsers();
    fetchComments();
  }, [id]);

  const fetchTicket = async () => {
    try {
      const data = await ticketService.getTicketById(id);
      setTicket(data);
      setStatus(data.status);
    } catch (error) {
      console.error('Error fetching ticket:', error);
      toast.error('Failed to load ticket');
    } finally {
      setLoading(false);
    }
  };

  const fetchUsers = async () => {
    try {
      const data = await userService.getAllUsers();
      setUsers(data);
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  const fetchComments = async () => {
    // Mock comments - replace with actual API call
    const mockComments = [
      {
        id: 1,
        content: 'I have restarted the printer but the issue persists. Can someone from IT check?',
        user: { id: 1, fullName: 'John Doe', role: 'USER' },
        createdAt: '2024-01-10T10:30:00',
        isInternal: false,
      },
      {
        id: 2,
        content: 'I have assigned this to the hardware team. Please check the printer connections.',
        user: { id: 2, fullName: 'Sarah Johnson', role: 'TECHNICIAN' },
        createdAt: '2024-01-10T11:15:00',
        isInternal: false,
      },
      {
        id: 3,
        content: 'Internal note: This seems to be a network printer issue, not hardware.',
        user: { id: 2, fullName: 'Sarah Johnson', role: 'TECHNICIAN' },
        createdAt: '2024-01-10T11:20:00',
        isInternal: true,
      },
    ];
    setComments(mockComments);
  };

  const handleStatusUpdate = async () => {
    try {
      setUpdating(true);
      const updatedTicket = await ticketService.updateTicketStatus(
        id,
        status,
        status === 'RESOLVED' ? 'Issue has been resolved' : ''
      );
      setTicket(updatedTicket);
      
      // Send notification
      if (updatedTicket.assignedTo) {
        // In a real app, you would call notificationService.sendTicketUpdatedNotification
      }
      
      toast.success('Status updated successfully');
    } catch (error) {
      console.error('Error updating status:', error);
      toast.error('Failed to update status');
    } finally {
      setUpdating(false);
    }
  };

  const handleAssign = async () => {
    try {
      setUpdating(true);
      const updatedTicket = await ticketService.assignTicket(id, selectedUser);
      setTicket(updatedTicket);
      setAssignDialogOpen(false);
      
      // Send notification
      if (selectedUser) {
        const assignee = users.find(u => u.id === parseInt(selectedUser));
        if (assignee) {
          // In a real app, you would call notificationService.sendTicketAssignedNotification
        }
      }
      
      toast.success('Ticket assigned successfully');
    } catch (error) {
      console.error('Error assigning ticket:', error);
      toast.error('Failed to assign ticket');
    } finally {
      setUpdating(false);
    }
  };

  const handleDelete = async () => {
    if (window.confirm('Are you sure you want to delete this ticket?')) {
      try {
        await ticketService.deleteTicket(id);
        toast.success('Ticket deleted successfully');
        navigate('/tickets');
      } catch (error) {
        console.error('Error deleting ticket:', error);
        toast.error('Failed to delete ticket');
      }
    }
  };

  const handleAddComment = async () => {
    if (!comment.trim()) return;
    
    try {
      // Mock API call - replace with actual comment creation
      const newComment = {
        id: comments.length + 1,
        content: comment,
        user: { id: 1, fullName: 'Current User', role: 'ADMIN' },
        createdAt: new Date().toISOString(),
        isInternal: false,
      };
      
      setComments([newComment, ...comments]);
      setComment('');
      setShowCommentForm(false);
      
      // Send notification
      if (ticket.createdBy) {
        // In a real app, you would call notificationService.sendCommentAddedNotification
      }
      
      toast.success('Comment added successfully');
    } catch (error) {
      console.error('Error adding comment:', error);
      toast.error('Failed to add comment');
    }
  };

  const getStatusColor = (status) => {
    switch (status) {
      case 'OPEN': return 'warning';
      case 'IN_PROGRESS': return 'info';
      case 'RESOLVED': return 'success';
      case 'CLOSED': return 'default';
      case 'PENDING': return 'secondary';
      default: return 'default';
    }
  };

  const getPriorityColor = (priority) => {
    switch (priority) {
      case 'LOW': return 'success';
      case 'MEDIUM': return 'warning';
      case 'HIGH': return 'error';
      case 'CRITICAL': return 'error';
      default: return 'default';
    }
  };

  const getPriorityIcon = (priority) => {
    switch (priority) {
      case 'LOW': return '🟢';
      case 'MEDIUM': return '🟡';
      case 'HIGH': return '🔴';
      case 'CRITICAL': return '⚠️';
      default: return '';
    }
  };

  if (loading) {
    return (
      <Box sx={{ p: 3 }}>
        <LinearProgress />
      </Box>
    );
  }

  if (!ticket) {
    return (
      <Box sx={{ p: 3 }}>
        <Alert severity="error">Ticket not found</Alert>
        <Button
          startIcon={<ArrowBack />}
          onClick={() => navigate('/tickets')}
          sx={{ mt: 2 }}
        >
          Back to Tickets
        </Button>
      </Box>
    );
  }

  return (
    <Box sx={{ p: 3 }}>
      {/* Header */}
      <Box sx={{ display: 'flex', alignItems: 'center', mb: 3 }}>
        <Tooltip title="Back to tickets">
          <IconButton onClick={() => navigate('/tickets')} sx={{ mr: 2 }}>
            <ArrowBack />
          </IconButton>
        </Tooltip>
        <Box sx={{ flexGrow: 1 }}>
          <Typography variant="h4" gutterBottom>
            {ticket.title}
          </Typography>
          <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
            <Chip
              label={ticket.ticketNumber}
              size="small"
              sx={{ fontWeight: 'bold' }}
            />
            <Chip
              icon={<PriorityHigh />}
              label={ticket.priority}
              color={getPriorityColor(ticket.priority)}
              size="small"
            />
            <Chip
              label={ticket.status}
              color={getStatusColor(ticket.status)}
              size="small"
            />
          </Box>
        </Box>
        <Box sx={{ display: 'flex', gap: 1 }}>
          <Tooltip title="Edit ticket">
            <Button
              variant="outlined"
              startIcon={<Edit />}
              onClick={() => navigate(`/tickets/edit/${id}`)}
            >
              Edit
            </Button>
          </Tooltip>
          <Tooltip title="Delete ticket">
            <Button
              variant="outlined"
              color="error"
              startIcon={<Delete />}
              onClick={handleDelete}
            >
              Delete
            </Button>
          </Tooltip>
        </Box>
      </Box>

      <Grid container spacing={3}>
        {/* Left Column - Comments and Activity */}
        <Grid item xs={12} md={8}>
          {/* Ticket Description */}
          <Paper sx={{ p: 3, mb: 3 }}>
            <Typography variant="h6" gutterBottom>
              Description
            </Typography>
            <Typography variant="body1" color="text.secondary" paragraph>
              {ticket.description}
            </Typography>
            
            {ticket.resolutionNotes && (
              <>
                <Divider sx={{ my: 2 }} />
                <Typography variant="h6" gutterBottom>
                  Resolution Notes
                </Typography>
                <Typography variant="body1" color="text.secondary" paragraph>
                  {ticket.resolutionNotes}
                </Typography>
              </>
            )}
          </Paper>

          {/* Comments Section */}
          <Paper sx={{ p: 3, mb: 3 }}>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
              <Typography variant="h6">
                Comments ({comments.length})
              </Typography>
              <Button
                variant="contained"
                startIcon={<CommentIcon />}
                onClick={() => setShowCommentForm(!showCommentForm)}
              >
                Add Comment
              </Button>
            </Box>

            {/* Comment Form */}
            {showCommentForm && (
              <Box sx={{ mb: 3 }}>
                <TextField
                  fullWidth
                  multiline
                  rows={4}
                  placeholder="Add your comment here..."
                  value={comment}
                  onChange={(e) => setComment(e.target.value)}
                  sx={{ mb: 2 }}
                />
                <Box sx={{ display: 'flex', justifyContent: 'flex-end', gap: 1 }}>
                  <Button
                    variant="outlined"
                    onClick={() => setShowCommentForm(false)}
                  >
                    Cancel
                  </Button>
                  <Button
                    variant="contained"
                    startIcon={<Send />}
                    onClick={handleAddComment}
                    disabled={!comment.trim()}
                  >
                    Post Comment
                  </Button>
                </Box>
              </Box>
            )}

            {/* Comments List */}
            <List sx={{ maxHeight: 400, overflow: 'auto' }}>
              {comments.length === 0 ? (
                <ListItem>
                  <ListItemText
                    primary="No comments yet"
                    secondary="Be the first to comment on this ticket"
                  />
                </ListItem>
              ) : (
                comments.map((comment) => (
                  <React.Fragment key={comment.id}>
                    <ListItem alignItems="flex-start" sx={{ py: 2 }}>
                      <Avatar sx={{ mr: 2, bgcolor: 'primary.main' }}>
                        {comment.user.fullName.charAt(0)}
                      </Avatar>
                      <Box sx={{ flexGrow: 1 }}>
                        <Box sx={{ display: 'flex', alignItems: 'center', mb: 1 }}>
                          <Typography variant="subtitle2" sx={{ mr: 1 }}>
                            {comment.user.fullName}
                          </Typography>
                          <Chip
                            label={comment.user.role}
                            size="small"
                            sx={{ height: 20 }}
                          />
                          {comment.isInternal && (
                            <Chip
                              label="Internal"
                              size="small"
                              color="warning"
                              sx={{ ml: 1, height: 20 }}
                            />
                          )}
                          <Typography variant="caption" color="text.secondary" sx={{ ml: 'auto' }}>
                            {dayjs(comment.createdAt).fromNow()}
                          </Typography>
                        </Box>
                        <Typography variant="body2" paragraph>
                          {comment.content}
                        </Typography>
                      </Box>
                    </ListItem>
                    <Divider variant="inset" component="li" />
                  </React.Fragment>
                ))
              )}
            </List>
          </Paper>
        </Grid>

        {/* Right Column - Ticket Information */}
        <Grid item xs={12} md={4}>
          {/* Status Update Card */}
          <Card sx={{ mb: 3 }}>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Update Status
              </Typography>
              <FormControl fullWidth size="small" sx={{ mb: 2 }}>
                <InputLabel>Status</InputLabel>
                <Select
                  value={status}
                  onChange={(e) => setStatus(e.target.value)}
                  label="Status"
                >
                  <MenuItem value="OPEN">Open</MenuItem>
                  <MenuItem value="IN_PROGRESS">In Progress</MenuItem>
                  <MenuItem value="RESOLVED">Resolved</MenuItem>
                  <MenuItem value="CLOSED">Closed</MenuItem>
                  <MenuItem value="PENDING">Pending</MenuItem>
                </Select>
              </FormControl>
              <Button
                variant="contained"
                fullWidth
                startIcon={<Update />}
                onClick={handleStatusUpdate}
                disabled={updating || status === ticket.status}
              >
                {updating ? 'Updating...' : 'Update Status'}
              </Button>
            </CardContent>
          </Card>

          {/* Ticket Information Card */}
          <Card sx={{ mb: 3 }}>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Ticket Information
              </Typography>
              
              <Box sx={{ mb: 2 }}>
                <Typography variant="body2" color="text.secondary" sx={{ display: 'flex', alignItems: 'center', mb: 0.5 }}>
                  <Person sx={{ fontSize: 16, mr: 1 }} />
                  Created By
                </Typography>
                <Typography variant="body1">
                  {ticket.createdBy?.fullName || 'Unknown'}
                </Typography>
              </Box>

              <Box sx={{ mb: 2 }}>
                <Typography variant="body2" color="text.secondary" sx={{ display: 'flex', alignItems: 'center', mb: 0.5 }}>
                  <Assignment sx={{ fontSize: 16, mr: 1 }} />
                  Assigned To
                </Typography>
                <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                  <Typography variant="body1">
                    {ticket.assignedTo?.fullName || 'Unassigned'}
                  </Typography>
                  <Button
                    size="small"
                    onClick={() => setAssignDialogOpen(true)}
                  >
                    {ticket.assignedTo ? 'Reassign' : 'Assign'}
                  </Button>
                </Box>
              </Box>

              <Box sx={{ mb: 2 }}>
                <Typography variant="body2" color="text.secondary" sx={{ display: 'flex', alignItems: 'center', mb: 0.5 }}>
                  <Schedule sx={{ fontSize: 16, mr: 1 }} />
                  Created
                </Typography>
                <Typography variant="body1">
                  {dayjs(ticket.createdAt).format('MMM D, YYYY h:mm A')}
                </Typography>
              </Box>

              {ticket.dueDate && (
                <Box sx={{ mb: 2 }}>
                  <Typography variant="body2" color="text.secondary" sx={{ display: 'flex', alignItems: 'center', mb: 0.5 }}>
                    <Schedule sx={{ fontSize: 16, mr: 1 }} />
                    Due Date
                  </Typography>
                  <Typography variant="body1">
                    {dayjs(ticket.dueDate).format('MMM D, YYYY h:mm A')}
                  </Typography>
                </Box>
              )}

              {ticket.category && (
                <Box sx={{ mb: 2 }}>
                  <Typography variant="body2" color="text.secondary" sx={{ display: 'flex', alignItems: 'center', mb: 0.5 }}>
                    <Category sx={{ fontSize: 16, mr: 1 }} />
                    Category
                  </Typography>
                  <Typography variant="body1">
                    {ticket.category}
                  </Typography>
                </Box>
              )}

              {ticket.subCategory && (
                <Box sx={{ mb: 2 }}>
                  <Typography variant="body2" color="text.secondary" sx={{ display: 'flex', alignItems: 'center', mb: 0.5 }}>
                    <Label sx={{ fontSize: 16, mr: 1 }} />
                    Sub Category
                  </Typography>
                  <Typography variant="body1">
                    {ticket.subCategory}
                  </Typography>
                </Box>
              )}
            </CardContent>
          </Card>

          {/* Quick Actions Card */}
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Quick Actions
              </Typography>
              <Button
                fullWidth
                variant="outlined"
                startIcon={<AttachFile />}
                sx={{ mb: 1 }}
              >
                Add Attachment
              </Button>
              <Button
                fullWidth
                variant="outlined"
                startIcon={<CheckCircle />}
                onClick={() => {
                  setStatus('RESOLVED');
                  handleStatusUpdate();
                }}
              >
                Mark as Resolved
              </Button>
            </CardContent>
          </Card>
        </Grid>
      </Grid>

      {/* Assign Ticket Dialog */}
      <Dialog open={assignDialogOpen} onClose={() => setAssignDialogOpen(false)} maxWidth="sm" fullWidth>
        <DialogTitle>
          <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
            <Typography variant="h6">Assign Ticket</Typography>
            <IconButton onClick={() => setAssignDialogOpen(false)} size="small">
              <Close />
            </IconButton>
          </Box>
        </DialogTitle>
        <DialogContent>
          <Typography variant="body2" color="text.secondary" paragraph>
            Select a user to assign this ticket to:
          </Typography>
          <FormControl fullWidth sx={{ mt: 2 }}>
            <InputLabel>Select User</InputLabel>
            <Select
              value={selectedUser}
              onChange={(e) => setSelectedUser(e.target.value)}
              label="Select User"
            >
              {users
                .filter(user => user.role === 'TECHNICIAN' || user.role === 'ADMIN')
                .map((user) => (
                  <MenuItem key={user.id} value={user.id}>
                    <Box sx={{ display: 'flex', alignItems: 'center' }}>
                      <Avatar sx={{ width: 24, height: 24, mr: 1, fontSize: 12 }}>
                        {user.fullName.charAt(0)}
                      </Avatar>
                      {user.fullName} ({user.role})
                    </Box>
                  </MenuItem>
                ))}
            </Select>
          </FormControl>
        </DialogContent>
        <DialogActions sx={{ p: 2 }}>
          <Button onClick={() => setAssignDialogOpen(false)}>Cancel</Button>
          <Button
            onClick={handleAssign}
            disabled={!selectedUser || updating}
            variant="contained"
          >
            {updating ? 'Assigning...' : 'Assign Ticket'}
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default TicketDetail;