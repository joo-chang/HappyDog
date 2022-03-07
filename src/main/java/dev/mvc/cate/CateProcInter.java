package dev.mvc.cate;

import java.util.List;

public interface CateProcInter {
    /**
     * 등록
     * insert id="create" parameterType="dev.mvc.cate.CateVO"
     * @param cateVO
     * @return 등록된 갯수
     */
    public int create(CateVO cateVO);
    
    /**
     *  전체 목록
     * @return
     */
    public List<CateVO> list_all();  
    
    /**
     *  categrpno별 목록
     * @return
     */
    public List<CateVO> list_by_categrpno(int categrpno);   
    
    /**
     * Categrp + Cate join, 연결 목록
     * @return
     */
    public List<Categrp_CateVO> list_all_join();  
    
    /**
     * 조회, 수정폼
     * @param cateno 카테고리 번호, PK
     * @return
     */
    public CateVO read(int cateno);
    
    /**
     * 수정 처리
     * @param cateVO
     * @return
     */
    public int update(CateVO cateVO);
    
    /**
     * 삭제 처리 
     * @param cateno
     * @return
     */
    public int delete(int cateno);
    
}

