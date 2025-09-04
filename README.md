# JSB – Q&A 게시판 (Thymeleaf + Spring Boot + JPA)

질문/답변 CRUD, 검색, 회원가입/로그인 기능을 제공하는 SSR 기반 게시판입니다.  
Spring Data JPA로 정렬·페이징을 지원합니다.

---

## 🔧 기술 스택

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
## 🔗 주요 URL (SSR)
### Question Routes
| Method | Path                       | Method 이름     | Note |
|-------:|----------------------------|-----------------|------|
| GET    | /questions/list            | showList        | 목록/검색(kwType, kw) |
| GET    | /questions/detail/{id}     | showDetail      | 상세 |
| GET    | /questions/create          | showCreate      | 작성 폼 |
| POST   | /questions/create          | create          | 🔒 로그인 필요, 유효성 검사 |
| GET    | /questions/update/{id}     | showUpdate      | 작성자 본인만(컨트롤러에서 체크) |
| POST   | /questions/update/{id}     | update          | 🔒 로그인 필요, 본인만 수정 |
| POST   | /questions/delete/{id}     | delete          | 🔒 로그인 필요, 본인만 삭제 |

### Answer Routes
| Method | Path                      | Method 이름 | Note |
|-------:|---------------------------|-------------|------|
| POST   | /answers/create           | create      | 🔒 로그인 필요, 등록 후 질문 상세로 리다이렉트 |
| POST   | /answers/update/{id}      | update      | 🔒 로그인 필요, 본인만 수정 |
| POST   | /answers/delete/{id}      | delete      | 🔒 로그인 필요, 본인만 삭제 |

### Member (Auth) Routes
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

### 🚀 추가 기능 (각자 구현)
> 스프린트 확장 과제: 아래 4개 항목을 **팀원 전원이 각각 독립 구현**해보기로 했습니다.  
- 답변 페이징 & 정렬
- 댓글
- 조회수
- 카테고리 분류

---
## 🛠 트러블슈팅
### 1) 엔티티/관계 매핑했는데 데이터가 안 들어감
- **원인**: 초기 DB 파일(스키마)이 **불완전한 상태**로 생성되어 매핑과 불일치.
- **해결**:
  - 로컬 H2/파일 DB 삭제 후 재실행(스키마 재생성).
  - (권장) DDL 자동설정 확인 `spring.jpa.hibernate.ddl-auto=update|create-drop`
  - 장기적으로는 **Flyway/Liquibase**로 스키마 버전 관리.
 
### 2) 검색이 동작하지 않음(쿼리스트링 미생성)
- **원인**: `th:feild` 오타 → `name` 속성이 생성되지 않아 파라미터 미전달.
- **해결**: `th:field="*{kw}"`로 수정.  
    렌더된 HTML에 `name="kw"`가 있는지 확인.
  ```html
  <form th:action="@{/questions/list}" th:object="${search}" method="get">
    <input th:field="*{kw}" type="text"/>
    <select th:field="*{kwType}">
      <option value="all">전체</option>
      <option value="subject">제목</option>
      <option value="content">내용</option>
    </select>
  </form>
  ```
### 3) 로그인 실패 / 비밀번호 검증 문제 (+ 2중 인코딩)
- **원인**:
  - 기존 수동 로그인(평문 비교) 과 Spring Security의 BCrypt matches() 절차 충돌.
  - @Builder 경로로 엔티티 생성 시 생성자/인코딩 로직을 우회하여 2중 인코딩 발생.
- **해결**:
  - 회원가입 시 Service 레이어에서만 인코딩:
    ```java
    String enc = passwordEncoder.encode(raw);
    memberRepository.save(new Member(username, enc, nickname));
    ```

  - 로그인은 POST /login 으로 Security 인증 절차에 위임(수동 비교/세션 주입 제거).

  - 엔티티에서 불필요한 인코딩 메서드 제거(또는 사용 금지).
→ “인코딩 책임 = Service”로 단일화.

### 4) H2 콘솔 접근 불가 / 로그인 후 엉뚱한 리다이렉트
- **원인**: Security가 H2 차단, 로그인 성공 후 기본 리다이렉트로 흐름 꼬임
- **해결(설정 예)**
  ```java
  http
    .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
    .authorizeHttpRequests(auth -> auth
      .requestMatchers("/**")
      .permitAll()
    )
    .formLogin(formLogin -> formLogin
      .loginPage("/login")
      .defaultSuccessUrl("/questions/list")
      .failureHandler(customAuthenticationFailureHandler)
    )
    .logout(logout -> logout
      .logoutUrl("/logout")
      .logoutSuccessUrl("/")
      .invalidateHttpSession(true)
    );
  ```
### 5) POST 요청 403 (CSRF)
- **원인**: CSRF 토큰 미포함 또는 action URL/메서드 불일치.
- **해결**:
  - 폼에 정확한 경로 지정: 
    ```html
      <form th:action="@{/...}" method="post">
    ```
### 6) 로그인 없이 상세 페이지에서 수정/삭제 가능
- **원인**: `permitAll("/questions/detail/**")`을 모든 HTTP 메서드에 적용 → 상세에서의 수정/삭제 POST도 통과.
- **해결**: `HttpMethod.GET`으로 제한 걸어서 GET을 제외한 나머지 요청은 인증을 요구하도록 수정
---
## 😵 어려웠던 점 & 해결
### 1) 기능 단위 병합 후 코드 스타일/구조 제각각 → 가독성 저하, 이해 비용 증가
- **영향**: 리뷰/수정 속도 저하, 버그 추적 어려움(특히 주니어 입장 난도↑)
- **원인**: 개발 전 공통 **컨벤션/용어/패키지 구조** 합의 부재, PR 템플릿/자동 포맷터 미도입
- **해결**:
  - 병합은 **주 담당자 기준**으로 우선 정리
  - 이후 **리팩토링 스프린트**로 구조/용어 일원화  
    - URL: 명사 경로 + HTTP 메서드(폼만 `/new`, `/edit`)  
    - DTO: `*CreateRequest`, `*UpdateRequest`, `*Response`  
    - 계층: Controller 얇게, **Service @Transactional**, Repository 단순화
- **교훈/다음 액션**:
  - `/docs/conventions.md`에 **규칙 사전 합의**(네이밍, URL, 패키지, 예외/검증)
  - **PR 템플릿 + 리뷰 체크리스트**(권한, 검증, 로그, 문서 갱신)
  - **자동 포맷터/린터**(Spotless/Checkstyle) + **pre-commit hook** 적용
  - **코드 오너십/페어리뷰**로 주니어 온보딩 난도 완화

---

### 2) 한 컨트롤러에서 검색 + 페이지네이션 동시 적용 시 UI/흐름 구현 난이도↑
- **영향**: 화면에서 **검색 상태 유지**, **페이지 이동 시 파라미터 보존**이 꼬여 개발 시간 증가
- **원인**: 파라미터 전파 패턴 부재(`kwType`, `kw`, `sort`), 화면/서버 간 용어 불일치
- **해결 패턴**:
  - **컨트롤러**
    ```java
    @GetMapping("/questions")
    public String list(@ModelAttribute("search") QuestionSearchDto search,
                       @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC)
                       Pageable pageable, Model model) {
        Page<QuestionResponse> page = questionService.search(search, pageable);
        model.addAttribute("page", page);
        return "question/list";
    }
    ```
  - **뷰(Thymeleaf): 파라미터 보존**
    ```html
    <a th:href="@{/questions(
        page=${p},
        size=${page.size},
        sort=${param.sort},
        kwType=${search.kwType},
        kw=${search.kw}
    )}">[[${p+1}]]</a>
    ```
  - **검색 폼**: `th:object="${search}"` + `th:field="*{kw}"`, `th:field="*{kwType}"`
- **교훈/다음 액션**:
  - **공통 페이징/검색 컴포넌트**(Thymeleaf fragment)로 재사용
  - `QuestionSearchDto`에 **기본값**과 **검증** 부여, 화면·서버 **용어 통일(subject/content/all)**
  - 링크 생성은 **헬퍼 메서드/유틸**로 캡슐화하여 오타/누락 방지
---
## 🧩 화면
### Question (로그인 전)
| 목록 | 상세 |
|---|---|
| <img width="1065" height="832" alt="image" src="https://github.com/user-attachments/assets/4cdc40de-2277-4f73-94c3-ff5700f5fd9b" /> | <img width="1072" height="818" alt="image" src="https://github.com/user-attachments/assets/f87b5b84-e755-4fa0-98b4-49ec1ef0ba1e" /> |

### Question (로그인 후)
| 목록 | 상세 | 작성 | 수정 |
|---|---|---|---|
| <img width="1061" height="864" alt="image" src="https://github.com/user-attachments/assets/66dad328-234d-40a8-abd6-e35d89351bf9" /> | <img width="1055" height="1119" alt="image" src="https://github.com/user-attachments/assets/f7fffe6e-0d39-4507-a1fa-937f4dbb8382" /> | <img width="1064" height="813" alt="image" src="https://github.com/user-attachments/assets/b1cb6cbb-e426-4c66-bc67-23516cb01066" /> | <img width="1068" height="752" alt="image" src="https://github.com/user-attachments/assets/dbf7e4ca-a622-4db0-99bf-80f27db66cf6" /> |

### Answer
| 목록 & 등록 | 수정 |
|---|---|
| <img width="770" height="765" alt="image" src="https://github.com/user-attachments/assets/c5bb4e3d-c302-40c7-bc1a-65a21b6e3562" /> | <img width="724" height="437" alt="image" src="https://github.com/user-attachments/assets/bdded3a9-bef4-4c8f-8a67-eeb8cead3aa5" /> |



### Auth & Search
| 로그인 | 회원가입 |
|---|---|
| <img width="1074" height="1015" alt="image" src="https://github.com/user-attachments/assets/6478b80e-1c9f-46d0-b7ac-5f35b3b358b3" /> | <img width="1072" height="884" alt="image" src="https://github.com/user-attachments/assets/698c552e-c223-49ca-b17e-0c24a2ba1d5f" /> |

---
## 🧪 E2E 테스트 시나리오 (Manual)

> 브라우저 수동 테스트 흐름입니다.  
> 사전 조건: 클린 DB(H2 메모리), Chrome 최신, CSRF 활성, 기본 계정 없음.

### 기본 플로우
| # | Action (Path) | 입력/조건 | 기대 결과 |
|---|---|---|---|
| 1 | 회원가입 폼 진입 **GET** `/signup` |  | 폼 렌더링 |
| 2 | 회원가입 제출 **POST** `/signup` | `username`, `password`, `password_confirm`, `nickname` | 검증 통과 시 **`redirect:/login`**. 중복/불일치 시 에러 메시지 표시 |
| 3 | 로그인 폼/처리 **GET/POST** `/login` | 가입한 계정 | 로그인 성공 후 홈/목록으로 리다이렉트, 헤더에 사용자명 노출 |
| 4 | 질문 목록 진입 **GET** `/questions/list` |  | 목록/검색폼 렌더링 |
| 5 | 질문 작성 폼 **GET** `/questions/create` |  | 폼 렌더링 |
| 6 | 질문 생성 **POST** `/questions/create` | `subject`, `content` | 성공 시 **`redirect:/questions/detail/{id}`** |
| 7 | 질문 상세 **GET** `/questions/detail/{id}` |  | 제목/본문/답변섹션 표시 |
| 8 | 질문 수정 폼 **GET** `/questions/update/{id}` | 작성자 로그인 상태 | 폼 렌더링(기존 값 바인딩). 작성자 아님 → 접근 차단/리다이렉트 |
| 9 | 질문 수정 **POST** `/questions/update/{id}` | 변경된 `subject`, `content` | 성공 시 **상세로 리다이렉트**, 내용 반영 |
| 10 | 답변 등록 **POST** `/answers/create` | `content`, `questionId` | 성공 시 **상세로 리다이렉트**, 답변 노출 |
| 11 | 답변 수정 **POST** `/answers/update/{answerId}` | 수정된 `content` | 성공 시 **상세로 리다이렉트**, 수정 반영 |
| 12 | 답변 삭제 **POST** `/answers/delete/{answerId}` | 작성자 로그인 | 성공 시 **상세로 리다이렉트**, 목록에서 제거 |
| 13 | 질문 삭제 **POST** `/questions/delete/{id}` | 작성자 로그인 | **`redirect:/questions/list`**, 삭제 확인(상세 접근 시 404/리다이렉트) |
| 14 | 로그아웃 **POST** `/logout` |  | 세션 종료, 로그인/비로그인 UI 전환 |

### 검증 포인트 (Checklist)
- [ ] 모든 POST 폼에 **CSRF 토큰** 포함 (Thymeleaf 자동 주입 확인)  
- [ ] **권한**: 비로그인 사용자는 작성/수정/삭제 불가, 버튼 비노출 + 서버단 검사  
- [ ] **소유권**: 타 사용자로 로그인 시 타인의 질문/답변 수정·삭제 차단 (컨트롤러/서비스 둘 다)  
- [ ] **검증 메시지**: 빈 제목/본문 등 유효성 실패 시 `BindingResult`로 폼 재렌더링 & 입력값 유지  
- [ ] **검색**: 목록에서 검색 후 이동 시 `kwType`, `kw` 유지  

---
## 🌿 브랜치/커밋 컨벤션
- 브랜치: feature/<scope>, fix/<scope>, chore/<scope>
- 커밋: feat: OOO, fix: OOO, refactor: OOO
---
## 🔭 향후 개선사항
- [ ] **답변 페이징 & 정렬**  _(Planned)_
  - DoD: `page,size,sort` 파라미터 동작, 상세 화면에서 답변 목록 페이징 UI 제공

- [ ] **댓글(Comment)**  _(Planned)_
  - DoD: Answer 하위 댓글 CRUD, 작성자만 수정/삭제, 권한/검증 메시지

- [ ] **조회수(views)**  _(Planned)_
  - DoD: 상세 진입 시 증가, 동일 세션/짧은 시간 중복 방지, 목록/상세에 노출

- [ ] **카테고리 분류**  _(Planned)_
  - DoD: 단일/다중 중 1안 선택, 목록 필터링 및 검색 파라미터 유지



