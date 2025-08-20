import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { counselAPI } from '../services/api';
import './CounselDetail.css';

const CounselDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [counsel, setCounsel] = useState(null);
  const [analysis, setAnalysis] = useState(null);
  const [parsedAnalysis, setParsedAnalysis] = useState(null);
  const [loading, setLoading] = useState(true);
  const [analysisLoading, setAnalysisLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchCounselDetail();
  }, [id]);

  const fetchCounselDetail = async () => {
    try {
      const response = await counselAPI.getCounselById(id);
      setCounsel(response.data);

      // 분석 결과가 있을 경우 JSON 파싱 시도
      if (response.data.analysis) {
        try {
          // JSON 형식인지 확인 후 파싱
          if (response.data.analysis.startsWith('{')) {
            const jsonData = JSON.parse(response.data.analysis);

            // 기존 UI 구조에 맞게 데이터 변환
            const mappedData = {
              // 키워드 매핑
              keywords: jsonData['주요 키워드'] || [],
              // 감정 분석 매핑 - 임의의 퍼센트 값 설정
              sentimentScore: {
                positive: jsonData['고객 감정'] === '긍정' ? 80 : 10,
                neutral: jsonData['고객 감정'] === '중립' ? 80 : 10,
                negative: jsonData['고객 감정'] === '부정' ? 80 : 10,
              },
              // 주제 분류 매핑
              topic: jsonData['상담 유형'] || '분류 없음',
              // 추천 상품은 없을 경우 빈 배열로 설정
              recommendedProducts: []
            };

            setParsedAnalysis(mappedData);
          }
        } catch (parseError) {
          console.error('분석 데이터 파싱 오류:', parseError);
        }
      }
    } catch (error) {
      setError('상담 상세 정보를 불러오는데 실패했습니다.');
      console.error('Error fetching counsel detail:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleAnalyze = async () => {
    setAnalysisLoading(true);
    try {
      const response = await counselAPI.analyzeCounsel(id);
      setAnalysis(response.data);
      // 분석 결과가 업데이트된 최신 데이터로 다시 로드
      await fetchCounselDetail();
    } catch (error) {
      setError('상담 분석에 실패했습니다.');
      console.error('Error analyzing counsel:', error);
    } finally {
      setAnalysisLoading(false);
    }
  };

  const handleBack = () => {
    navigate('/counsels');
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleString('ko-KR');
  };

  if (loading) {
    return <div className="loading">로딩 중...</div>;
  }

  if (error) {
    return (
      <div className="error-container">
        <div className="error">{error}</div>
        <button onClick={handleBack} className="btn btn-primary">
          목록으로 돌아가기
        </button>
      </div>
    );
  }

  if (!counsel) {
    return (
      <div className="error-container">
        <div className="error">상담을 찾을 수 없습니다.</div>
        <button onClick={handleBack} className="btn btn-primary">
          목록으로 돌아가기
        </button>
      </div>
    );
  }

  return (
    <div className="counsel-detail-container">
      <header className="detail-header">
        <button onClick={handleBack} className="btn btn-secondary">
          ← 목록으로 돌아가기
        </button>
        <h1>상담 상세 정보</h1>
      </header>

      <main className="detail-content">
        <div className="counsel-info-card">
          <div className="counsel-info-header">
            <h2>상담 #{counsel.id}</h2>
            <div className="counsel-meta">
              <span>고객 ID: {counsel.customerId}</span>
              <span>상담 날짜: {formatDate(counsel.counselDate)}</span>
            </div>
          </div>

          {counsel.productInfo && (
            <div className="product-section">
              <h3>관련 상품</h3>
              <p>{counsel.productInfo}</p>
            </div>
          )}

          <div className="content-section">
            <h3>상담 내용</h3>
            <div className="counsel-content">
              <p>{counsel.content}</p>
            </div>
          </div>

          <div className="analysis-section">
            <div className="analysis-header">
              <h3>상담 분석</h3>
              <button
                onClick={handleAnalyze}
                className="btn btn-primary"
                disabled={analysisLoading}
              >
                {analysisLoading ? '분석 중...' : '상담 분석하기'}
              </button>
            </div>

            {parsedAnalysis && (
              <div className="analysis-results">
                <div className="analysis-item">
                  <h4>키워드 분석</h4>
                  <div className="keywords">
                    {parsedAnalysis.keywords && parsedAnalysis.keywords.length > 0 ? (
                      parsedAnalysis.keywords.map((keyword, index) => (
                        <span key={index} className="keyword-tag">
                          {keyword}
                        </span>
                      ))
                    ) : (
                      <p>키워드가 없습니다.</p>
                    )}
                  </div>
                </div>

                <div className="analysis-item">
                  <h4>감정 분석</h4>
                  <div className="sentiment-scores">
                    <div className="sentiment-item positive">
                      <span>긍정: {parsedAnalysis.sentimentScore?.positive?.toFixed(2) || '0.00'}%</span>
                    </div>
                    <div className="sentiment-item neutral">
                      <span>중립: {parsedAnalysis.sentimentScore?.neutral?.toFixed(2) || '0.00'}%</span>
                    </div>
                    <div className="sentiment-item negative">
                      <span>부정: {parsedAnalysis.sentimentScore?.negative?.toFixed(2) || '0.00'}%</span>
                    </div>
                  </div>
                </div>

                <div className="analysis-item">
                  <h4>주제 분류</h4>
                  <p className="topic">{parsedAnalysis.topic || '주제를 분류할 수 없습니다.'}</p>
                </div>

                <div className="analysis-item">
                  <h4>상품 추천</h4>
                  <div className="recommendations">
                    {parsedAnalysis.recommendedProducts && parsedAnalysis.recommendedProducts.length > 0 ? (
                      parsedAnalysis.recommendedProducts.map((product, index) => (
                        <span key={index} className="product-tag">
                          {product}
                        </span>
                      ))
                    ) : (
                      <p>추천 상품이 없습니다.</p>
                    )}
                  </div>
                </div>
              </div>
            )}
          </div>
        </div>
      </main>
    </div>
  );
};

export default CounselDetail;
