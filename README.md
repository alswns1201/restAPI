# restAPI
RESTful API 서버 구현 - 구멍가게 코딩단 


# Chapter 
- @RequiredArgsConstructor , final : 의존성 주입
- TDD에서 @Log4j2
  - testCompileOnly 'org.projectlombok:lombok:1.18.24' 
  - testAnnotationProcessor'org.projectlombok:lombok:1.18.24'
- 데이터 베이스 : 스프링부트 컨넥션 풀은 HikariCP
  - implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
  - implementation 'org.springframework.boot:spring-boot-starter-data-jdbc'
  - runtimeOnly 'com.mysql:mysql-connector-j'
- 레파지토리
  - extends JpaRepository<TodoEntity, Long>

# 영속성 컨텍스트 , 더킹 체크
- 하나의 트랜젠셕안에서 
  - Entity를 변화하면 자동으로 save를 쓰지 않아도 update가 적용됨. 이를 더킹 체크라함.
  - findById()를 하게 되면 , 현재 담고 있는게 있는지 판단하고 조회를 한다 
    - 같은걸 두번 조회를 하면 한번만 조회 됨.(영속성 컨텍스트 안에서 확인함.)

# Test관련
- @DataJpaTest : @SpringBootTest와 다르게 @Entity와 같은 데이터베이스 관련부분만 실행
- @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
  - Replace.NONE 설정이 없으면, 실제 데이터베이스를 이용할 수 없다.


