# AI 상담 분석 프로젝트

상담사들의 상담 내용을 분석하고 예측하는 AI 기반 상담 분석 시스템

## 1. 개발환경 구성

- [x] Spring Boot 3.x 설정
- [x] H2 Database 설정
- [x] Swagger UI 설정
- [x] REST API 기본 구조 설정

## 2. 기능 개발

### 2.1 상담 내용 분석 기능
- [x] 상담 데이터 모델 설계
- [x] 상담 내용 저장 API 개발
- [x] 상담 내용 분석 알고리즘 구현
- [x] 분석 결과 조회 API 개발

### 2.2 다음 상담 예측 기능
- [x] 상담 패턴 분석 모델 설계
- [x] 예측 알고리즘 구현
- [x] 예측 결과 API 개발

### 2.3 상담 상품 관리
- [x] 상품 데이터 모델 설계 (Counsel 엔티티에 포함)
- [x] 상담-상품 연관 관계 설계 (productInfo 필드로 구현)
- [ ] 별도 상품 관리 API 개발
- [ ] 상담별 상품 통계 기능 구현

## 3. 기술 스택

- Backend: Spring Boot 3.x
- Database: H2 Database
- API Documentation: Swagger UI
- Architecture: REST API

## 4. 프로젝트 진행 상황

- [x] 프로젝트 기본 환경 설정
  - [x] Spring Boot 3.x 설정
  - [x] H2 Database 설정
  - [x] Swagger UI 설정
- [x] 데이터베이스 스키마 설계
  - [x] 상담 엔티티 설계
- [x] API 엔드포인트 설계
  - [x] 상담 CRUD API 구현
  - [x] 상담 분석 API 구현
  - [x] 예측 API 구현
- [x] 기본 CRUD 기능 구현
- [x] 분석 알고리즘 개발
  - [x] 상담 내용 분석 알고리즘
  - [x] 다음 상담 예측 알고리즘
    - [x] 상담 패턴 분석
    - [x] 상담 주기 계산
    - [x] 키워드 빈도 분석
- [x] 테스트 케이스 작성
  - [x] 상담 분석 테스트
  - [x] 예측 알고리즘 테스트
- [x] API 문서화 (Swagger UI)
- [x] 개발환경 설정
  - [x] IDE별 .gitignore 설정 완료
  - [x] Git 저장소 초기화 및 첫 커밋 완료

## 5. 완료된 주요 기능

### ✅ 완료된 API 엔드포인트
- POST `/api/counsels` - 상담 등록
- GET `/api/counsels/{id}` - 상담 상세 조회  
- GET `/api/counsels` - 상담 목록 조회
- GET `/api/counsels/{id}/analysis` - 상담 내용 분석
- GET `/api/counsels/{customerId}/prediction` - 다음 상담 예측

### ✅ 구현된 분석 알고리즘
- **키워드 분석**: 상담 내용에서 주요 키워드 추출 및 빈도 분석
- **감정 분석**: 긍정/부정/중립 감정 점수 계산
- **주제 분류**: 상담 내용을 주제별로 자동 분류
- **패턴 분석**: 고객별 상담 패턴 및 주기 분석
- **상품 추천**: 상담 내용 기반 관련 상품 추천

### ✅ 테스트 커버리지
- CounselAnalysisServiceTest: 분석 알고리즘 단위 테스트
- CounselPredictionServiceTest: 예측 알고리즘 단위 테스트
- 모든 주요 비즈니스 로직에 대한 테스트 케이스 완료

## 6. 다음 단계 개발 계획

### 🔄 진행 예정 작업
- [ ] 별도 상품 관리 API 개발
- [ ] 상담별 상품 통계 기능 구현
- [ ] 대시보드 기능 추가
- [ ] 실시간 분석 결과 알림 기능
- [ ] 데이터 내보내기 기능

### 🛠️ 추가 개발 요구사항
- [x] 공통 Exception 처리
  - [x] 전역 예외 핸들러 구현
  - [x] 커스텀 예외 클래스 정의
  - [x] API 에러 응답 표준화
- [x] Profile별 환경 설정
  - [x] 개발(dev) 환경 설정
  - [x] 검증(test) 환경 설정  
  - [x] 운영(prod) 환경 설정
  - [x] 환경별 application.yml 분리
- [x] JWT 기반 인증 시스템
  - [x] JWT 토큰 생성/검증 로직
  - [x] 로그인 API 구현
  - [x] 인증 필터 구현
  - [x] 사용자 관리 기능

### 🚀 고도화 계획
- [x] OpenAI API 연동으로 분석 정확도 향상 (기본 예제 추가)
- [ ] 머신러닝 모델 도입
- [ ] 실시간 스트리밍 분석
- [ ] 다국어 지원

## 7. 브라우저 접속 정보

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **H2 Database Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:counseldb`
  - User Name: `sa`
  - Password: (빈칸)

## 8. 추가 변경 및 최신화 내역

- 데이터베이스를 H2에서 MariaDB로 변경
- MariaDB 설정 및 도커 실행법 추가
- application.properties 주요 설정 반영
- Swagger UI 경로 최신화: http://localhost:8080/swagger-ui/index.html
- H2 관련 내용 제거, MariaDB 접속 정보로 대체
- 환경별 application.yml 분리 및 dev/test/prod 환경 설정 완료
- JWT 기반 인증 시스템 및 관련 API 구현 완료
- README 최신화 (2025-08-20)

## 9. 앞으로 해야 할 일 (프론트엔드)

1. **Front단 구현**
   - [x] React.js 기반 프론트엔드 프로젝트 생성 및 환경 설정
2. **React.js 사용**
   - [x] React.js로 SPA 구조 설계 및 개발
3. **Swagger API 문서 기반 주요 페이지 개발**
   - [x] 로그인 페이지: JWT 인증 API 연동, 사용자 로그인 기능 구현
   - [x] 상담 목록 페이지: 상담 목록 조회 API 연동, 상담 리스트 표시
   - [x] 상담 상세 내역 페이지: 상담 상세 조회 API 연동, 상담 정보 및 분석 버튼 표시
   - [x] 상담 입력 페이지: 상담 등록 API 연동, 신규 상담 입력 및 저장 기능 구현
4. **상담 상세 내역 페이지 기능**
   - [x] 상담 분석 버튼 추가: 클릭 시 상담 분석 API 호출, 분석 결과 화면에 표시

> ✅ **프론트엔드 개발 완료!** `/frontend` 폴더에 React.js로 모든 기능이 구현되었습니다. 백엔드 Swagger API 문서를 기반으로 각 페이지별 기능을 성공적으로 구현했습니다.

### 🎉 구현 완료된 프론트엔드 기능들:
- React.js + React Router 기반 SPA 구조
- JWT 인증 시스템 (로그인/로그아웃)
- 상담 CRUD 기능 (목록/상세/입력)
- 상담 분석 API 연동 및 결과 표시
- 반응형 웹 디자인
- API 통신 및 에러 처리
- 보안 라우팅 (인증된 사용자만 접근 가능)

### 🚀 실행 방법:
1. 백엔드: `./mvnw spring-boot:run` (포트 8080)
2. 프론트엔드: `cd frontend && npm start` (포트 3000)
3. 브라우저: http://localhost:3000
