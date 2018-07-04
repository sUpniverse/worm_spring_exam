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

