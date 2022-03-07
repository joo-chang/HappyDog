package dev.mvc.categrp;

import java.util.List;

public interface CategrpProcInter {
    /**
     * 등록
     * insert id="create" parameterType="dev.mvc.categrp.CategrpVO"
     * @param categrpVO
     * @return 등록된 레코드 갯수
     */
    public int create(CategrpVO categrpVO);
   
    /**
     * 등록 순서별 목록
     * select id="list_categrpno_asc" resultType="dev.mvc.categrp.CategrpVO"
     * @return
     */
    public List<CategrpVO> list_categrpno_asc();
    
    /**
     * 출력 순서별 목록
     * select id="list_seqno_asc" resultType="dev.mvc.categrp.CategrpVO"
     * @return
     */
    public List<CategrpVO> list_seqno_asc();
    
    /**
     * 조회
     * select id="read" resultType="dev.mvc.categrp.CategrpVO" parameterType="int"
     * @param categrpno 카테고리 그룹 번호, PK
     * @return CategrpVO
     */
    public CategrpVO read(int categrpno);
   
    /**
     * 수정 처리
     * update id="update" parameterType="dev.mvc.categrp.CategrpVO"
     * @param categrpVO
     * @return 수정된 레코드 갯수
     */
    public int update(CategrpVO categrpVO);

    /**
     * 삭제 처리
     * delete id="delete" parameterType="int"
     * @param categrpno
     * @return 처리된 레코드 갯수
     */
    public int delete(int categrpno);
    
    /**
     * 출력 순서 상향
     * update id="update_seqno_up" parameterType="int"
     * @param categrpno
     * @return 처리된 레코드 갯수
     */
    public int update_seqno_up(int categrpno);
   
    /**
     * 출력 순서 하향
     * update id="update_seqno_down" parameterType="int"
     * @param categrpno
     * @return 처리된 레코드 갯수
     */
    public int update_seqno_down(int categrpno);
    
    /**
     * visible 수정
     * update id="update_visible" parameterType="dev.mvc.categrp.CategrpVO
     * @param categrpVO
     * @return
     */
    public int update_visible(CategrpVO categrpVO);
    
}




