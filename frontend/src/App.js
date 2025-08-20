import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import CounselList from './components/CounselList';
import CounselDetail from './components/CounselDetail';
import CounselForm from './components/CounselForm';
import './App.css';

// 인증된 사용자만 접근 가능한 라우트를 위한 컴포넌트
const ProtectedRoute = ({ children }) => {
  const token = localStorage.getItem('token');
  return token ? children : <Navigate to="/login" />;
};

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/counsels" element={
            <ProtectedRoute>
              <CounselList />
            </ProtectedRoute>
          } />
          <Route path="/counsels/new" element={
            <ProtectedRoute>
              <CounselForm />
            </ProtectedRoute>
          } />
          <Route path="/counsels/:id" element={
            <ProtectedRoute>
              <CounselDetail />
            </ProtectedRoute>
          } />
          <Route path="/" element={<Navigate to="/counsels" />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
