import React, { useState, useEffect } from 'react';
import { counselAPI } from '../services/api';
import { useNavigate } from 'react-router-dom';
import './CounselList.css';

const CounselList = () => {
  const [counsels, setCounsels] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchCounsels();
  }, []);

  const fetchCounsels = async () => {
    try {
      const response = await counselAPI.getAllCounsels();
      setCounsels(response.data);
    } catch (error) {
      setError('상담 목록을 불러오는데 실패했습니다.');
      console.error('Error fetching counsels:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleCounselClick = (id) => {
    navigate(`/counsels/${id}`);
  };

  const handleNewCounsel = () => {
    navigate('/counsels/new');
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleString('ko-KR');
  };

  if (loading) {
    return <div className="loading">로딩 중...</div>;
  }

  if (error) {
    return <div className="error">{error}</div>;
  }

  return (
    <div className="counsel-list-container">
      <header className="header">
        <h1>AI 상담 분석 시스템</h1>
        <div className="header-actions">
          <button onClick={handleNewCounsel} className="btn btn-primary">
            새 상담 등록
          </button>
          <button onClick={handleLogout} className="btn btn-secondary">
            로그아웃
          </button>
        </div>
      </header>

      <main className="main-content">
        <h2>상담 목록</h2>

        {counsels.length === 0 ? (
          <div className="empty-state">
            <p>등록된 상담이 없습니다.</p>
            <button onClick={handleNewCounsel} className="btn btn-primary">
              첫 상담 등록하기
            </button>
          </div>
        ) : (
          <div className="counsel-grid">
            {counsels.map((counsel) => (
              <div
                key={counsel.id}
                className="counsel-card"
                onClick={() => handleCounselClick(counsel.id)}
              >
                <div className="counsel-header">
                  <h3>상담 #{counsel.id}</h3>
                  <span className="customer-id">고객: {counsel.customerId}</span>
                </div>
                <div className="counsel-content">
                  <p className="counsel-summary">
                    {counsel.content?.length > 100
                      ? counsel.content.substring(0, 100) + '...'
                      : counsel.content
                    }
                  </p>
                  {counsel.productInfo && (
                    <p className="product-info">상품: {counsel.productInfo}</p>
                  )}
                </div>
                <div className="counsel-footer">
                  <span className="counsel-date">
                    {formatDate(counsel.counselDate)}
                  </span>
                </div>
              </div>
            ))}
          </div>
        )}
      </main>
    </div>
  );
};

export default CounselList;
