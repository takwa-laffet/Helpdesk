import React, { useState, useEffect } from 'react';
import {
  Grid,
  Card,
  CardContent,
  Typography,
  Box,
  LinearProgress,
} from '@mui/material';
import {
  Assignment,
  CheckCircle,
  HourglassEmpty,
  Warning,
  Person,
  TrendingUp,
} from '@mui/icons-material';
import { dashboardService } from '../../services/dashboardService';

const Dashboard = () => {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchDashboardStats();
  }, []);

  const fetchDashboardStats = async () => {
    try {
      const data = await dashboardService.getDashboardStats();
      setStats(data);
    } catch (error) {
      console.error('Error fetching dashboard stats:', error);
    } finally {
      setLoading(false);
    }
  };

  const statCards = [
    {
      title: 'Total Tickets',
      value: stats?.totalTickets || 0,
      icon: <Assignment />,
      color: '#1976d2',
    },
    {
      title: 'Open Tickets',
      value: stats?.openTickets || 0,
      icon: <HourglassEmpty />,
      color: '#ff9800',
    },
    {
      title: 'In Progress',
      value: stats?.inProgressTickets || 0,
      icon: <TrendingUp />,
      color: '#2196f3',
    },
    {
      title: 'Resolved',
      value: stats?.resolvedTickets || 0,
      icon: <CheckCircle />,
      color: '#4caf50',
    },
    {
      title: 'High Priority',
      value: stats?.highPriorityTickets || 0,
      icon: <Warning />,
      color: '#f44336',
    },
    {
      title: 'Assigned to Me',
      value: stats?.assignedToMeTickets || 0,
      icon: <Person />,
      color: '#9c27b0',
    },
  ];

  if (loading) {
    return <LinearProgress />;
  }

  return (
    <Box sx={{ flexGrow: 1, p: 3 }}>
      <Typography variant="h4" gutterBottom>
        Dashboard Overview
      </Typography>
      
      <Grid container spacing={3}>
        {statCards.map((card, index) => (
          <Grid item xs={12} sm={6} md={4} key={index}>
            <Card
              sx={{
                height: '100%',
                display: 'flex',
                flexDirection: 'column',
                transition: 'transform 0.2s',
                '&:hover': {
                  transform: 'translateY(-4px)',
                  boxShadow: 6,
                },
              }}
            >
              <CardContent>
                <Box
                  sx={{
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'space-between',
                    mb: 2,
                  }}
                >
                  <Box
                    sx={{
                      backgroundColor: `${card.color}20`,
                      borderRadius: '50%',
                      p: 1,
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'center',
                    }}
                  >
                    {React.cloneElement(card.icon, {
                      sx: { color: card.color, fontSize: 30 },
                    })}
                  </Box>
                </Box>
                <Typography variant="h3" component="div">
                  {card.value}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  {card.title}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>

      <Grid container spacing={3} sx={{ mt: 2 }}>
        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Ticket Status Distribution
              </Typography>
              <Box sx={{ mt: 2 }}>
                <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 1 }}>
                  <Typography>Open</Typography>
                  <Typography>{stats?.openTickets || 0}</Typography>
                </Box>
                <LinearProgress
                  variant="determinate"
                  value={((stats?.openTickets || 0) / (stats?.totalTickets || 1)) * 100}
                  sx={{ height: 10, borderRadius: 5 }}
                />
                
                <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 1, mt: 2 }}>
                  <Typography>In Progress</Typography>
                  <Typography>{stats?.inProgressTickets || 0}</Typography>
                </Box>
                <LinearProgress
                  variant="determinate"
                  value={((stats?.inProgressTickets || 0) / (stats?.totalTickets || 1)) * 100}
                  sx={{ height: 10, borderRadius: 5 }}
                />
                
                <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 1, mt: 2 }}>
                  <Typography>Resolved</Typography>
                  <Typography>{stats?.resolvedTickets || 0}</Typography>
                </Box>
                <LinearProgress
                  variant="determinate"
                  value={((stats?.resolvedTickets || 0) / (stats?.totalTickets || 1)) * 100}
                  sx={{ height: 10, borderRadius: 5 }}
                  color="success"
                />
              </Box>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </Box>
  );
};

export default Dashboard;