# JSB – Q&A 게시판 (Thymeleaf + Spring Boot + JPA)

질문/답변 CRUD, 검색, 회원가입/로그인 기능을 제공하는 SSR 기반 게시판입니다.  
Spring Data JPA로 정렬·페이징을 지원합니다.

---

## 🔧 Tech Stack

- **Language/Build**: Java 21, Gradle
- **Framework**: Spring Boot
- **Web (MVC)**: Spring Web, Thymeleaf + TailwindCSS 
- **Persistence**: Spring Data JPA (Hibernate)
- **Validation**: Jakarta Bean Validation (`spring-boot-starter-validation`)
- **Auth/Security**: Spring Security 6
- **DB**: H2
- **Dev/Tooling**: Lombok, Spring Boot DevTools
- **Testing**: Spring Boot Test, Spring Security Test, JUnit Platform

---

## 📦 도메인 개요

### BaseEntity (`@MappedSuperclass`, Auditing)
| Field          | Type            | Notes |
|----------------|-----------------|-------|
| id             | long            | PK (`IDENTITY`) |
| createdDate    | LocalDateTime   | `@CreatedDate` |
| modifiedDate   | LocalDateTime   | `@LastModifiedDate` |

> Auditing 사용 시 `@EnableJpaAuditing`(설정 클래스) 필요.

### Member
| Field     | Type   | Notes |
|-----------|--------|-------|
| username  | String | `@Column(unique = true)` |
| password  | String | BCrypt 저장 (`encodePassword`) |
| nickname  | String | — |

### Question
| Field      | Type         | Notes |
|------------|--------------|-------|
| subject    | String       | — |
| content    | String       | `@Column(columnDefinition="TEXT")` |
| answerList | List<Answer> | `@OneToMany(mappedBy="question", cascade={PERSIST, REMOVE})` |
| author     | Member       | `@ManyToOne(fetch=LAZY)` |

### Answer
| Field   | Type    | Notes |
|---------|---------|-------|
| content | String  | — |
| question| Question| `@ManyToOne(fetch=LAZY)` |
| author  | Member  | `@ManyToOne(fetch=LAZY)` |

#### 관계(요약)
- **Member 1 : N Question**
- **Member 1 : N Answer**
- **Question 1 : N Answer** (질문 삭제 시 답변도 삭제: `cascade=REMOVE`)

---
## Question Routes
| Method | Path                       | Method 이름     | Note |
|-------:|----------------------------|-----------------|------|
| GET    | /questions/list            | showList        | 목록/검색(kwType, kw) |
| GET    | /questions/detail/{id}     | showDetail      | 상세 |
| GET    | /questions/create          | showCreate      | 작성 폼 |
| POST   | /questions/create          | create          | 🔒 로그인 필요, 유효성 검사 |
| GET    | /questions/update/{id}     | showUpdate      | 작성자 본인만(컨트롤러에서 체크) |
| POST   | /questions/update/{id}     | update          | 🔒 로그인 필요, 본인만 수정 |
| POST   | /questions/delete/{id}     | delete          | 🔒 로그인 필요, 본인만 삭제 |

## Answer Routes
| Method | Path                      | Method 이름 | Note |
|-------:|---------------------------|-------------|------|
| POST   | /answers/create           | create      | 🔒 로그인 필요, 등록 후 질문 상세로 리다이렉트 |
| POST   | /answers/update/{id}      | update      | 🔒 로그인 필요, 본인만 수정 |
| POST   | /answers/delete/{id}      | delete      | 🔒 로그인 필요, 본인만 삭제 |

## Member (Auth) Routes
| Method | Path    | Method 이름  | Note |
|-------:|---------|--------------|------|
| GET    | /login  | showLogin    | 로그인 폼, 실패 메시지 표시(WebAttributes) |
| POST   | /login   | —           | **Spring Security 처리**(loginProcessingUrl) |
| GET    | /signup | signup       | 회원가입 폼 |
| POST   | /signup | signup       | 회원 생성, 중복/비번확인 검증, `redirect:/login` |
| POST   | /logout  | —           | **Spring Security 처리**(로그아웃) |

---
## 👥 역할 및 담당
> 아래 표는 **주 담당(Primary Owner)** 기준입니다.  
> 실제로는 **팀 역량 향상을 위해 모든 팀원이 기본 기능 전체를 1회 이상 end-to-end로 구현**했으며,  
> **지금 표에 있는 모든 기능을 전원이 구현 가능**합니다.

| 역할 | 담당 기능 / 기여 |
|---|---|
| **김동엽 (팀장)** | 질문 상세보기, 답변 삭제 |
| **이지연** | 질문 등록, 로그인 & 로그아웃 |
| **이병진** | 질문 검색, 답변 수정 |
| **유승재** | 질문 수정, 답변 등록 |
| **고영서** | 질문 삭제, 회원가입 |
| **이연서** | 질문 목록, 답변 목록 |

### 팀 운영 메모
- **주 담당**: 해당 기능의 최종 품질 책임(최종 PR, 버그 핫픽스, 문서화)
- **공통 구현**: 스프린트 초반, 각자 모든 기본 기능을 독립 구현 → 코드 리뷰/리팩토링으로 컨벤션 통합
- **협업 흐름**: GitHub Flow(브랜치 → PR → 리뷰 → 머지), 공통 코드스타일/URL 규칙 준수

---
## 🛠 트러블슈팅
---
## 😵 어려웠던 점 & 해결
---
## 🧩 화면
---
## 🌿 브랜치/커밋 컨벤션
- 브랜치: feature/<scope>, fix/<scope>, chore/<scope>
- 커밋: feat: OOO, fix: OOO, refactor: OOO
---
## 🔭 향후 개선사항



