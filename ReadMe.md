# Spring practice

- 실전 예제를 만들어 보고, 프로젝트에 적용시켜본다.
- 충분한 이해와 반복숙달로 내것으로 만든다.
- 나중에 나아게 충분한 밑거름이 될것이다.
- org.supniverse.web :  member 만들기 및 Junit을 통한 테스트
- CRUD_Exam :  게시판 CRUD 및 [페이징 처리][https://github.com/sUpniverse/worm_spring_exam#%ED%8E%98%EC%9D%B4%EC%A7%95-%EC%B2%98%EB%A6%AC] 구현
- REST_Ajax_Exam : Restful 하게 만들기 및 Ajax를 이용한 댓글 달기 등을 구현



***





## Exception

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

    

## 페이징 처리

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




#### 하단의 페이지 번호 만들기

-  PageMaker : 페이지를 나타낼 정보를 모두 가지고 있어야함 (이를 토대로 Class구성)

  - totalCount : 게시물의 총량 (DB를 통해)
  - endPage : page bar의 마지막 번호
  - startPage : page bar에 보일 맨 처음 페이지 (1-10page면 1, 11-20page면 11)
  - prev : startPage 이전에 페이지가 더 있으면 true로 표시
  - next : endPage 이후에 페이지가 더 있으면 true로 표시
  - calData() : 위의 변수들을 계산해 주는 메소드

- Mppaer.xml

  ```xml
  <select id="countPaging" resultType="int">
  	<![CDATA[ 
  		select count(bno) from tbl_board where bno > 0 
  	]]>    
  </select>
  ```

- boardDAOImpl (boardDAO에 선언 후 상속받음)

  ```java
   public int countPaging(Criteria cri) {
  	return session.selectOne(namespace+".countPaging",cri);
   }
  ```

  - boardServiceImpl : 똑같이 boardService에 선언 후 상속받아 dao.countPaging(cri)

- BoardController : listPage메소드에 추가   

   ``` java
  PageMaker pageMaker = new PageMaeker();
  	pageMaker.setCri(cri);
  	model.addAttribute("pageMaker",pageMaker);
   ```



#####Url에 page,perPageNum 나타내기

- JSP내에서 수정

- JavaScript 수정

- PageMaker에서 생성

  - UriComponents를 이용하여 붙여준다.

    ```java
    public String makeQuery(int page) {
    	UriComponents uriComponents = 
    				UriComponentsBuilder.newInstance()
    				.queryParam("page",page);
    				.queryParam("pagePerNum",cri.pagePerNum)
    				.build();
    	return uriComponents.toUriString();
    }
    ```

- listPage에서 해당하는 모든 링크에 makeQuery를 넣어준다.

  ```jsp
  <a href ='/board/readPage${pageMaker.makeQuery(pageMkaer.cri.page)}>
  ```

  

####조회 기능을 눌렀다가 다시 있었던 목록으로 가기

- controller의 read메소드에 `@ModelAttribute("cri")Criteria cri`를 파라미터로 추가

- readPage.jsp 수정

     ```jsp
  <form>
  	<input type="hidden" name="page" value="${cri.page}">
  	<input type="hidden" name="perPageNum" value="${cri.perPageNum}">
  </form>
  
     ```

  - 원래 있던 form에 추가해준다

##### 수정중 or 후 원래 목록으로 가기

- controller부분, get_modify는 조회와 동일, post_modify는 `@ModelAttribute`를 사용하지 않고, `RedirectAttribute`를 사용해서 cri를 넘겨준다.
- form 부분도 조회와 동일   
- 삭제
  - controller의 remove는 post_modify와 동일
  - `@ModelAttribute`를 사용하지 않고, `RedirectAttribute`를 사용해서 cri를 넘겨준다.



## 검색어 처리 및 동적 SQL

- 검색어 bar를 이용해 게시물을 검색

- 페이징처리와 비슷하다

- field (만들어둔 Criteria를 상속하여 사용한다.)

- page

  - perPageNum
  - searchType
  - keyword

- Controller

  - 페이징과 동일

- Jsp 처리

  - (한가지 예시만 듬)

    ```Jsp
    <div>
        <select name="searchType">
    	<option value="t"
                <c:out value="${cri.searchType eq 't' ? 'selected' :''}" />>
    	</select>
    
        <input type="text" name='keyword' id="keywordInput" value='${cri.keyword}'>
             <button id='searchBtn'>Search</button>
             <button id='newBtn'>New Board</button>
    
    </div>
    ```



- 페이징과 마찬가지로 UriComponents를 통해 searchType과 keyword를 url에 포함시킨다.   

- ```java
  // PageMaker.java의 일부 
  public String makeSearch(int page) {
  	UriComponents uriComponents = UriComponentsBuilder.newInstance()
  		.queryParam("page",param)
  		.queryParam("perPageNum",cri.getPerPageNum())  					 
          .queryParam("searchType",((SearchCriteria) cri).getSearchTyep()
  		.queryParam("keyword",encoding(((SearchCriteria) cri).getKeyword())).build();
  	return uriCompoents.toUriString();
  }
  ```

​    

- MyBatis sql문 처리 후 페이징과 같이 조회,삭제,수정,등록의 기능들을 수정한다. 

 

 ### MyBatis 동적 SQL

- mybatis의 표현식을 이용한 sql문 처리

- SQL문 사용을 위해 각 메소드 수정(DAO,Service)  

- Mapper

     ```xml
  <select id="listSearch" resultType="BoardVO">
          <![CDATA[
          select * from tbl_board where bno > 0
          ]]>
  	<if test="searchType == 't'.toString()" >
  	 and title like CONCAT('%',#{keyword},'%')
  	</if>
      
  	<![CDATA[
  		order by bno desc limit #{pageStart},#{perPageNum}
  	]]>
  </select>
  
     ```

  

## Ajax를 이용한 댓글 처리

### Rest

- [Rest의 이해 : URI가 하나의 고유한 리소스를 대표한다. ][https://blog.npcode.com/2017/03/02/%EB%B0%94%EC%81%9C-%EA%B0%9C%EB%B0%9C%EC%9E%90%EB%93%A4%EC%9D%84-%EC%9C%84%ED%95%9C-rest-%EB%85%BC%EB%AC%B8-%EC%9A%94%EC%95%BD]

- RestController

  - REST방식의 데이터처리 애노테이션

    

    