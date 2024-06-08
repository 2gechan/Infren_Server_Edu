# 서버 개발자 되기

## 스프링 빈이란?
- 서버가 시작되면, 스프링 서버 내부에 거대한 컨테이너를 만들게 된다.
- 컨테이너 안에는 클래스가 들어가게 된다. 이때, 컨테이너 안에 들어간 클래스를 스프링 빈이라고 한다.
- 이때 다양한 정보도 함께 들어있고, 인스턴스화도 이루어진다.
- ex) UserController에서 JdbcTemplate에 new JdbcTemplate를 한적이 없지만 프로젝트 생성시 설정해주었던 Dependency 설정을 통해 JdbcTemplate를 스프링 컨테이너에 넣어놓은 뒤 의즌성을 자동으로 주입해주어 인스턴스화가 이루어진다.

## 스프링 빈을 등록하는 방법
- `@Configuration` 클래스에 붙이는 어노테이션, `@Bean`을 사용할 때 함께 사용해 주어야 한다.
- `@Bean` 메소드에 붙이는 어노테이션, 메소드에서 반환되는 객체를 스프링 빈에 등록한다.

- `@Repsository`, `@Service` 등의 어노테이션은 개발자가 직접 만든 클래스에 사용되고,
- `@configuration`이나 `@Bean`은 외부 라이브러라니 프레임워크에서 만든 클래스를 등록할 때 사용된다. 

- `@Component` 주어진 클래스를 '컴포넌트'로 간주한다. 이 클래스들은 스프링 서버가 뜰 때 자동으로 감지된다.
- `@Component`는 컨트롤러, 서비스, 리포지토리가 아닌 개발자가 직접 작성한 클래스를 스프링 빈으로 등록할 때 사용된다.

## 스프링 빈을 주입 받는 방법
1. 생성자를 통해 주입받기 (예전에는 @Autowired 어노테이션을 통해 생성자에 스프링 빈을 자동으로 등록하도록 설정하였지만, 현재는 생성자 방법일 때 바로 주입되도록 변경됐다.) 가장 권장되는 방법
2. Setter와 @Autowired 사용 : 누군가 setter를 사용하면 오작동할 수 있다.
3. 필드에 @Autowired 사용 : 테스트가 어려워질 수 있다.

## @Qualifier
- 한 인터페이스에 여러 `@Service`, `@Repository`가 구현되었을 때 해당 `@Service`나 `@Repository`를 어떤걸 사용할지 명시하여 스프링 빈을 주입해줄 수 있다.
- A 구현체에 @Primary, B구현체에 @Qualifier가 있고 A,B가 구현한 인터페이스를 C에서 생성자를 통해 주입할 경우 C 생성자의 @Qualifier가 등록되어 있다면 B구현체가 등록되게 된다.

## JPA
- `Spring Data JPA` : 복잡한 JPA 코드를 스프링과 함꼐 쉽게 사용할 수 있도록 도와주는 라이브러리
- `save` : 주어지는 객체를 저장하거나 업데이트 시켜준다.
- `findAll` : 주어지는 객체가 매핑된 테이블의 모든 데이터를 가져온다.
- `findById` : id(pk)를 기준으로 특정한 1개의 데이터를 가져온다.

```
JPA 사용시 계층 구조
Spring Data JPA
JPA (ORM)
Hibernate (JPA 구현체)
JDBC
```
### JPA 사용시 By 앞에 들어갈 수 있는 구절
- find : 1건을 가져온다. 반환 타입은 객체가 될 수도 있고, Optional<타입>이 될 수도 있다.
- findAll : 쿼리의 결과물이 N개인 경우 사용. List<타입> 반환
- exists : 쿼리 결과가 존재하는지 확인. 반환 타입은 boolean
- count : 쿼리 결과 개수. 반환 타입 long
- 각 구절별 AND, OR 조합로 조합할 수도 있다.

#### By 뒤에 들어갈 수 있는 구절
- GreaterThan : 초과
- GreaterThanEqual : 이상
- LessThan : 미만
- LessThanEqual : 이하
- Between : 사이에
- StartsWith : ~로 시작하는
- EndsWith : ~로 끝나는

### @Transactional
- @Transactional Annotation이 붙은 함수는 mysql 내의 start transaction을 해준다
- 함수가 예외 없이 내부 로직이 전부 수행된다면 commit
- 예외가 발생하거나 문제가 발생하면 rollback


#### 변경 감지(dirty check)
- 트랜잭션이 시작하게 되면 영속성 컨텍스트가 생성된다.
- 영속성 컨텍스트 안에서 변경 감지(dirty check) 하게 되면 명시적으로 save를 해주지 않더라도 트랜잭션이 끝날 때 자동으로 업데이트 해주게 된다.

#### 쓰기 지연
- 저장, 삭제, 업데이트 등을 로직에 따라 개별적으로 수행하는게 아닌 한번에 모아서 수행

#### 1차 캐싱
- Id가 1인 유저를 불러 왔을 경우, 영속성 컨텍스트에 기억하고 있다가 재 조회 하는 경우 수행하지 않고 캐싱해 놓은 객체를 불러온다. 이렇게 캐싱된 객체는 완전히 동일하다.