package dev.mvc.cate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.categrp.CategrpProcInter;
import dev.mvc.categrp.CategrpVO;

@Controller
public class CateCont {
    @Autowired
    @Qualifier("dev.mvc.categrp.CategrpProc")
    private CategrpProcInter categrpProc;

    @Autowired
    @Qualifier("dev.mvc.cate.CateProc")
    private CateProcInter cateProc;
    
    public CateCont() {
        System.out.println("-> CateCont created.");
    }
    
    /**
     * 새로고침 방지, EL에서 param으로 접근
     * @return
     */
    @RequestMapping(value="/cate/msg.do", method=RequestMethod.GET)
    public ModelAndView msg(String url){
      ModelAndView mav = new ModelAndView();

      mav.setViewName(url); // forward
      
      return mav; // forward
    }
    
    /**
     * 등록폼 http://localhost:9091/cate/create.do?categrpno=2
     * 
     * @return
     */
    @RequestMapping(value = "/cate/create.do", method = RequestMethod.GET)
    public ModelAndView create() {
      ModelAndView mav = new ModelAndView();
      mav.setViewName("/cate/create"); // /webapp/WEB-INF/views/cate/create.jsp

      return mav;
    }
    
    /**
     * 등록처리
     * http://localhost:9091/cate/create.do?categrpno=2
     * Exception: FK 전달이 안됨.
     * Field error in object 'cateVO' on field 'categrpno': rejected value [];
     * codes [typeMismatch.cateVO.categrpno,typeMismatch.categrpno,typeMismatch.int,typeMismatch]; 
     * arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [cateVO.categrpno,categrpno];
     * arguments []; default message [categrpno]]; 
     * default message [Failed to convert property value of type 'java.lang.String' to required type 'int' for property 'categrpno';
     * nested exception is java.lang.NumberFormatException: For input string: ""]]
     * @return
     */
    @RequestMapping(value = "/cate/create.do", method = RequestMethod.POST)
    public ModelAndView create(CateVO cateVO) {
      ModelAndView mav = new ModelAndView();

      // System.out.println("-> categrpno: " + cateVO.getCategrpno());
      
      int cnt = this.cateProc.create(cateVO);
      // System.out.println("등록 성공");

      mav.addObject("code", "create_success");
      mav.addObject("cnt", cnt);
      mav.addObject("categrpno", cateVO.getCategrpno());
      mav.addObject("name", cateVO.getName());
      mav.addObject("url", "/cate/msg");  // /cate/msg -> /cate/msg.jsp
      
      // mav.setViewName("redirect:/cate/msg.do");
      // response.sendRedirect("/cate/msg.do");
      
      mav.setViewName("redirect:/cate/list_by_categrpno.do");
      
      return mav;
    }
    
    /**
     * 전체 목록
     * http://localhost:9091/cate/list_all.do 
     * @return
     */
    @RequestMapping(value="/cate/list_all.do", method=RequestMethod.GET )
    public ModelAndView list_all() {
      ModelAndView mav = new ModelAndView();
      
      List<CateVO> list = this.cateProc.list_all();
      mav.addObject("list", list); // request.setAttribute("list", list);

      mav.setViewName("/cate/list_all"); // /cate/list_all.jsp
      return mav;
    }

    /**
     * 카테고리 그룹별 전체 목록
     * http://localhost:9091/cate/list_by_categrpno.do?categrpno=1 
     * @return
     */
    @RequestMapping(value="/cate/list_by_categrpno.do", method=RequestMethod.GET )
    public ModelAndView list_by_categrpno(int categrpno) {
      ModelAndView mav = new ModelAndView();
      
      List<CateVO> list = this.cateProc.list_by_categrpno(categrpno);
      mav.addObject("list", list); // request.setAttribute("list", list);

      CategrpVO  categrpVO = categrpProc.read(categrpno); // 카테고리 그룹 정보
      mav.addObject("categrpVO", categrpVO); 
      
      mav.setViewName("/cate/list_by_categrpno"); // /cate/list_by_categrpno.jsp
      return mav;
    }
    
    /**
     * Categrp + Cate join, 연결 목록
     * http://localhost:9091/cate/list_all_join.do 
     * @return
     */
    @RequestMapping(value="/cate/list_all_join.do", method=RequestMethod.GET )
    public ModelAndView list_all_join() {
      ModelAndView mav = new ModelAndView();
      
      List<Categrp_CateVO> list = this.cateProc.list_all_join();
      mav.addObject("list", list); // request.setAttribute("list", list);

      mav.setViewName("/cate/list_all_join"); // /WEB-INF/views/cate/list_all_join.jsp
      return mav;
    }
    
    /**
     * 조회 + 수정폼 http://localhost:9091/cate/read_update.do
     * 
     * @return
     */
    @RequestMapping(value = "/cate/read_update.do", method = RequestMethod.GET)
    public ModelAndView read_update(int cateno) {
      // int cateno = Integer.parseInt(request.getParameter("cateno"));

      ModelAndView mav = new ModelAndView();
      mav.setViewName("/cate/read_update"); // read_update.jsp

      // 카테고리 정보
      CateVO cateVO = this.cateProc.read(cateno);
      mav.addObject("cateVO", cateVO);
      // request.setAttribute("cateVO", cateVO);
      
      int categrpno = cateVO.getCategrpno();
      
      // 카테고리 그룹 정보
      CategrpVO categrpVO = this.categrpProc.read(categrpno);
      mav.addObject("categrpVO", categrpVO);

      // 카테고리 목록
      List<CateVO> list = this.cateProc.list_by_categrpno(categrpno);
      mav.addObject("list", list);

      return mav; // forward
    }
    
    /**
     * 수정 처리
     * 
     * @param cateVO
     * @return
     */
    @RequestMapping(value = "/cate/update.do", method = RequestMethod.POST)
    public ModelAndView update(CateVO cateVO) {
      ModelAndView mav = new ModelAndView();

      int cnt = this.cateProc.update(cateVO);
      
      if (cnt == 1) {
          mav.addObject("categrpno", cateVO.getCategrpno());
          mav.setViewName("redirect:/cate/list_by_categrpno.do");
      } else {
          mav.addObject("code", "update_fail"); // request에 저장
          mav.addObject("cnt", cnt); // request에 저장
          mav.addObject("cateno", cateVO.getCateno());
          mav.addObject("categrpno", cateVO.getCategrpno());
          mav.addObject("name", cateVO.getName());
          mav.addObject("url", "/cate/msg");  // /cate/msg -> /cate/msg.jsp로 최종 실행됨.
          
          mav.setViewName("redirect:/cate/msg.do"); // 새로고침 문제 해결, request 초기화
          
      }
      
      return mav;
    }
    
    /**
     * 조회 + 삭제폼 http://localhost:9091/cate/read_delete.do
     * 
     * @return
     */
    @RequestMapping(value = "/cate/read_delete.do", method = RequestMethod.GET)
    public ModelAndView read_delete(int cateno) {
      // int cateno = Integer.parseInt(request.getParameter("cateno"));
      ModelAndView mav = new ModelAndView();
      mav.setViewName("/cate/read_delete"); // read_delete.jsp

      CateVO cateVO = this.cateProc.read(cateno);
      mav.addObject("cateVO", cateVO);
      // request.setAttribute("cateVO", cateVO);
      int categrpno = cateVO.getCategrpno();
      
      CategrpVO categrpVO = this.categrpProc.read(categrpno);
      mav.addObject("categrpVO", categrpVO);
      

      List<CateVO> list = this.cateProc.list_by_categrpno(categrpno);
      mav.addObject("list", list);

      return mav; // forward
    }
    
    /**
     * 삭제 처리
     * 
     * @param cateVO
     * @return
     */
    @RequestMapping(value = "/cate/delete.do", method = RequestMethod.POST)
    public ModelAndView delete(int cateno) {
      ModelAndView mav = new ModelAndView();
      
      // 삭제될 레코드 정보를 삭제하기전에 읽음
      CateVO cateVO = this.cateProc.read(cateno); 
      
      int cnt = this.cateProc.delete(cateno);
      
      if (cnt == 1) {
          mav.addObject("categrpno", cateVO.getCategrpno());
          mav.setViewName("redirect:/cate/list_by_categrpno.do");
      } else {
          mav.addObject("code", "delete_fail"); // request에 저장
          mav.addObject("cnt", cnt); // request에 저장
          mav.addObject("cateno", cateVO.getCateno());
          mav.addObject("categrpno", cateVO.getCategrpno());
          mav.addObject("name", cateVO.getName());
          mav.addObject("url", "/cate/msg");  // /cate/msg -> /cate/msg.jsp로 최종 실행됨.
          
          mav.setViewName("redirect:/cate/msg.do"); // 새로고침 문제 해결, request 초기화
          
      }
      
      return mav;
    }
    
}






