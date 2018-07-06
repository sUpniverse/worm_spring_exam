# Spring practice

### CRUD_exam

#### Exception

- 발생한 Exception을 화면으로 전달해 주는 목적

- @ControllerAdvice 를 추가한 Class의 메소드는 발생한 Exception 객체의 타입만을 파라미터로 사용 가능

  - Excepiton 객체 타입의 파라미터를 이용한 메소드만 쓸 수 있다.
  - ModelAndView 타입을 사용하는 형태로 작성해 주어야 한다.
    - 하나의 객체에 Model 데이터와 View의 처리를 동시에 할 수 있는 객체

  ```java
  @ControllerAdvice
  public class CommonExceptionAdvice {
  	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);
  	
  	@ExceptionHandler(Exception.class)
  	private ModelAndView errorModelAndView(Exception ex) {
  		ModelAndView modelAndView = new ModelAndView();
  		modelAndView.setViewName("/error_common"); <!-- error_common.jsp를 만듬 -->
  		modelAndView.addObject("exception", ex);
  		
  		return modelAndView;
  	}
  }
  ```

  - ExceptionHandler를 통해 Exception.class를 받아옴
  - 메소드의 파라미터를 보면 Exception을 받아오고 있음
  - 메소드에서 ModelAndView 객체를 생성하고 반환하고 있음

### 페이징 처리

- 단계별 접근

  - uri를 통해 파라미터로 페이지 번호등을 줘서 이동하는 구현
  - 1 2 3 등의 페이지 번호를 눌러서 이동하는 구현
  - 글을 눌렀다가 다시 뒤로갈때 있던 페이지에 머물게 하는법 구현  

- 구현

  - mysql

     ```mysql
   select * from tbl_board where order by XXX limit 시작데이터번호,데이터 갯수
     ```

- Class 생성으로 여러개의 파라미터 처리하기

  - page번호 , page당 개시물 갯수 size 등의 정보를 갖는다 (파라미터가 늘어났을때 객체로 전달하기위한 목적)
  - Sql 구현시 인라인 파라미터가 존재함 그래서 getPageStart(), getPerPageNum() 메소드를 구현해 놓는다. (getter 및 setter도 만듬) 

- Mapper.xml

   ```xml
  <select id="listCriteria" resultType="BoardVO">
     select * from tbl_board where order by XXX limit #{pageStart},#{perPageNum} 
  </select>
   ```

- DAO 추가

  - BoardDAO

    ```java
    public List<BoardVO> listCriteria(Criteria cri) thorws Exception;
    ```

  - BoardDAOImpl

            ```java
     public List<BoardVO> listCriteria(Criteria cri) thorws Exception {
     	return session.selectList(".listCriteria",cri);
     }
            ```

- Service 수정

  - BoardService

    ```java
     public List<BoardVO> listCriteria(Criteria cri) throws Exception;
    ```

  - BoardServiceImpl

          ```java
    public List<BoardVO> listCriteria(Criteria cri) throws Exception {
    	return dao.listCriteria(cri); 
    }
          ```