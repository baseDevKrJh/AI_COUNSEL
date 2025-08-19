-- 초기 사용자 데이터 생성
INSERT INTO users (email, password, roles) VALUES
('counselor1@example.com', '$2a$10$rNQpF1vHGvrR3F7MJ8KmG.Czi4DcgoJJKOhx0NX.dZIKYK2Qj.EXK', 'ROLE_COUNSELOR'),
('counselor2@example.com', '$2a$10$rNQpF1vHGvrR3F7MJ8KmG.Czi4DcgoJJKOhx0NX.dZIKYK2Qj.EXK', 'ROLE_COUNSELOR'),
('counselor3@example.com', '$2a$10$rNQpF1vHGvrR3F7MJ8KmG.Czi4DcgoJJKOhx0NX.dZIKYK2Qj.EXK', 'ROLE_COUNSELOR'),
('counselor4@example.com', '$2a$10$rNQpF1vHGvrR3F7MJ8KmG.Czi4DcgoJJKOhx0NX.dZIKYK2Qj.EXK', 'ROLE_COUNSELOR'),
('counselor5@example.com', '$2a$10$rNQpF1vHGvrR3F7MJ8KmG.Czi4DcgoJJKOhx0NX.dZIKYK2Qj.EXK', 'ROLE_COUNSELOR'),
('counselor6@example.com', '$2a$10$rNQpF1vHGvrR3F7MJ8KmG.Czi4DcgoJJKOhx0NX.dZIKYK2Qj.EXK', 'ROLE_COUNSELOR'),
('counselor7@example.com', '$2a$10$rNQpF1vHGvrR3F7MJ8KmG.Czi4DcgoJJKOhx0NX.dZIKYK2Qj.EXK', 'ROLE_COUNSELOR'),
('counselor8@example.com', '$2a$10$rNQpF1vHGvrR3F7MJ8KmG.Czi4DcgoJJKOhx0NX.dZIKYK2Qj.EXK', 'ROLE_COUNSELOR'),
('counselor9@example.com', '$2a$10$rNQpF1vHGvrR3F7MJ8KmG.Czi4DcgoJJKOhx0NX.dZIKYK2Qj.EXK', 'ROLE_COUNSELOR'),
('counselor10@example.com', '$2a$10$rNQpF1vHGvrR3F7MJ8KmG.Czi4DcgoJJKOhx0NX.dZIKYK2Qj.EXK', 'ROLE_COUNSELOR');

-- 상담 데이터 샘플 생성
INSERT INTO counsel (counselor_id, customer_id, counsel_date, content, product_info, analysis, prediction, created_at, modified_at) VALUES
-- 신용카드 관련 상담
(1, 1, '2025-01-01 09:00:00', '신용카드 한도 상향 문의드립니다. 현재 소득이 증가하여 한도 상향이 가능할 것 같습니다.', '프리미엄 신용카드', NULL, NULL, CURRENT_TIMESTAMP(), NULL),
(1, 1, '2025-01-15 10:30:00', '카드 포인트 적립률 문의드립니다. 현재 사용중인 카드의 혜택이 변경되었다고 들��습니다.', '포인트적립 신용카드', NULL, NULL, CURRENT_TIMESTAMP(), NULL),
(1, 2, '2025-01-02 11:00:00', '해외결제 관련 문의입니다. 다음주 해외여행 예정인데 해외결제 수수료가 궁금합니다.', '글로벌 신용카드', NULL, NULL, CURRENT_TIMESTAMP(), NULL),

-- 예금/적금 관련 상담
(2, 3, '2025-01-03 13:15:00', '정기예금 금리 문의드립니다. 현재 1년 만기 기준 금리가 어떻게 되나요?', '정기예금', NULL, NULL, CURRENT_TIMESTAMP(), NULL),
(2, 4, '2025-01-04 14:20:00', '적금 상품 추천 부탁드립니다. 월 50만원 정도 저축 계획입니다.', '자유적금', NULL, NULL, CURRENT_TIMESTAMP(), NULL),
(2, 5, '2025-01-05 15:30:00', '급여이체 계좌 개설하고 싶습니다. 특별 우대금리 조건이 있나요?', '급여통장', NULL, NULL, CURRENT_TIMESTAMP(), NULL),

-- 대출 관련 상담
(3, 6, '2025-01-06 09:45:00', '주택담보대출 문의드립니다. 현재 아파트 시세가 5억인데 최대 대출 가능 금액이 궁금합니다.', '주택담보대출', NULL, NULL, CURRENT_TIMESTAMP(), NULL),
(3, 7, '2025-01-07 10:50:00', '신용대출 금리 문의드립니다. 현재 직장인이고 신용등급은 2등급입니다.', '신용대출', NULL, NULL, CURRENT_TIMESTAMP(), NULL),
(3, 8, '2025-01-08 11:55:00', '전세자금대출 관련 문의입니다. 다음달 전세 계약 예정인데 준비서류가 궁금합니다.', '전세자금대출', NULL, NULL, CURRENT_TIMESTAMP(), NULL),

-- 펀드/투자 관련 상담
(4, 9, '2025-01-09 13:00:00', '해외주식형 펀드 추천 부탁드립니다. 위험도는 보통 수준으로 생각하고 있습니다.', '해외주식형펀드', NULL, NULL, CURRENT_TIMESTAMP(), NULL),
(4, 10, '2025-01-10 14:05:00', 'ETF 투자 상담 원합니다. 국내 ETF 중에서 배당수익률이 높은 상품 추천해주세요.', 'ETF', NULL, NULL, CURRENT_TIMESTAMP(), NULL),

-- 보험 관련 상담
(5, 11, '2025-01-11 15:10:00', '암보험 가입 상담 원합니다. 40대 여성입니다.', '암보험', NULL, NULL, CURRENT_TIMESTAMP(), NULL),
(5, 12, '2025-01-12 16:15:00', '자동차보험 갱신 관련 문의드립니다. 현재 보험료가 너무 올라서 걱정입니다.', '자동차보험', NULL, NULL, CURRENT_TIMESTAMP(), NULL),

-- 모바일뱅킹 관련 상담
(6, 13, '2025-01-13 09:20:00', '앱 로그인이 안되는데 확인 부탁드립니다. 비밀번호는 정확히 입력했습니다.', '모바일뱅킹', NULL, NULL, CURRENT_TIMESTAMP(), NULL),
(6, 14, '2025-01-14 10:25:00', '해외송금 앱으로 가능한가요? 미국으로 송금하려고 합니다.', '해외송금', NULL, NULL, CURRENT_TIMESTAMP(), NULL),

-- 추가 상담 케이스
(1, 15, '2025-01-15 11:30:00', '체크카드 발급 문의드립니다. 학생인데 발급 가능한가요?', '체크카드', NULL, NULL, CURRENT_TIMESTAMP(), NULL),
(2, 16, '2025-01-16 13:35:00', 'ISA 계좌 개설 문의드립니다. 가입 조건과 한도가 궁금합니다.', 'ISA', NULL, NULL, CURRENT_TIMESTAMP(), NULL),
(3, 17, '2025-01-17 14:40:00', '사업자대출 관련 문의드립니다. 창업 3개월 차인데 가능할까요?', '사업자대출', NULL, NULL, CURRENT_TIMESTAMP(), NULL),
(4, 18, '2025-01-18 15:45:00', '퇴직연금 IRP 상담 원합니다. 포트폴리오 조정하고 싶습니다.', 'IRP', NULL, NULL, CURRENT_TIMESTAMP(), NULL),
(5, 19, '2025-01-19 16:50:00', '여행자보험 가입하고 싶습니다. 다음주 유럽여행 예정입니다.', '여행자보험', NULL, NULL, CURRENT_TIMESTAMP(), NULL),

-- 반복 상담 케이스 (고객 후속 상담)
(1, 1, '2025-01-20 09:55:00', '지난번 문의드린 한도 상향 관련해서 추가 서류 제출하려고 합니다.', '프리미엄 신용카드', NULL, NULL, CURRENT_TIMESTAMP(), NULL),
(2, 3, '2025-01-21 11:00:00', '정기예금 가입 진행하고 싶습니다. 방문 준비서류 알려주세요.', '정기예금', NULL, NULL, CURRENT_TIMESTAMP(), NULL),
(3, 6, '2025-01-22 13:05:00', '주택담보대출 심사 결과는 언제 나오나요? 지난주 신청했습니다.', '주택담보대출', NULL, NULL, CURRENT_TIMESTAMP(), NULL),

-- 불만처리 상담
(7, 20, '2025-01-23 14:10:00', 'ATM에서 거래했는데 금액이 맞지 않습니다. 확인 부탁드립니다.', 'ATM', NULL, NULL, CURRENT_TIMESTAMP(), NULL),
(7, 21, '2025-01-24 15:15:00', '카드 부정사용 의심거래가 있습니다. 확인 후 처리 부탁드립니다.', '신용카드', NULL, NULL, CURRENT_TIMESTAMP(), NULL),

-- 상품 변경 관련 상담
(8, 22, '2025-01-25 16:20:00', '현재 적금 상품 금리를 우대금리 상품으로 변경 가능한가요?', '자유적금', NULL, NULL, CURRENT_TIMESTAMP(), NULL),
(8, 23, '2025-01-26 09:25:00', '보험료 납입방식을 변경하고 싶습니다. 월납에서 연납으로 변경 가능한가요?', '종신보험', NULL, NULL, CURRENT_TIMESTAMP(), NULL),

-- 온라인 서비스 관련 상담
(9, 24, '2025-01-27 10:30:00', '공인인증서 갱신하는 방법 알려주세요.', '보안서비스', NULL, NULL, CURRENT_TIMESTAMP(), NULL),
(9, 25, '2025-01-28 11:35:00', '해외IP로 인터넷뱅킹 사용이 가능한가요?', '인터넷뱅킹', NULL, NULL, CURRENT_TIMESTAMP(), NULL),

-- 기타 금융상품 상담
(10, 26, '2025-01-29 13:40:00', '외화예금 개설 문의드립니다. 달러와 엔화 모두 가능한가요?', '외화예금', NULL, NULL, CURRENT_TIMESTAMP(), NULL),
(10, 27, '2025-01-30 14:45:00', '연금저축펀드 상품 추천 부탁드립니다.', '연금저축펀드', NULL, NULL, CURRENT_TIMESTAMP(), NULL);
