import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { counselAPI } from '../services/api';
import './CounselForm.css';

const CounselForm = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    customerId: '',
    content: '',
    productInfo: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      const response = await counselAPI.createCounsel(formData);
      const newCounselId = response.data.id;

      // 생성된 상담의 상세 페이지로 이동
      navigate(`/counsels/${newCounselId}`);
    } catch (error) {
      setError('상담 등록에 실패했습니다. 다시 시도해주세요.');
      console.error('Error creating counsel:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleBack = () => {
    navigate('/counsels');
  };

  return (
    <div className="counsel-form-container">
      <header className="form-header">
        <button onClick={handleBack} className="btn btn-secondary">
          ← 목록으로 돌아가기
        </button>
        <h1>새 상담 등록</h1>
      </header>

      <main className="form-content">
        <div className="form-card">
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="customerId">고객 ID *</label>
              <input
                type="text"
                id="customerId"
                name="customerId"
                value={formData.customerId}
                onChange={handleChange}
                required
                placeholder="고객 ID를 입력하세요"
              />
            </div>

            <div className="form-group">
              <label htmlFor="productInfo">관련 상품</label>
              <input
                type="text"
                id="productInfo"
                name="productInfo"
                value={formData.productInfo}
                onChange={handleChange}
                placeholder="상담과 관련된 상품 정보를 입력하세요 (선택사항)"
              />
            </div>

            <div className="form-group">
              <label htmlFor="content">상담 내용 *</label>
              <textarea
                id="content"
                name="content"
                value={formData.content}
                onChange={handleChange}
                required
                rows="10"
                placeholder="상담 내용을 자세히 입력하세요"
              />
            </div>

            {error && <div className="error-message">{error}</div>}

            <div className="form-actions">
              <button type="button" onClick={handleBack} className="btn btn-secondary">
                취소
              </button>
              <button
                type="submit"
                className="btn btn-primary"
                disabled={loading}
              >
                {loading ? '등록 중...' : '상담 등록'}
              </button>
            </div>
          </form>
        </div>
      </main>
    </div>
  );
};

export default CounselForm;
