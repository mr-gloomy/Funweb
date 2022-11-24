package com.itwillbs.admin.goods.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.admin.goods.db.AdminGoodsDAO;
import com.itwillbs.admin.goods.db.GoodsDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class AdminGoodsAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : AdminGoodsAddAction_execute() 호출 ");
		
		// 한글처리(생략)
		
		// 첨부파일
		// 1) upload 폴더 생성(가상경로)
		// 파일이 저장되는 실제 경로(tomcat-서버)
		// request.getRealPath("");
		
		ServletContext CTX = request.getServletContext();
		String realPath = CTX.getRealPath("/upload");
		System.out.println(" M : realPath : "+realPath);
		// 2) 업로드크기 제어 (10MB)
		int maxSize = 10 * 1024 * 1024;
		// 3) 라이브러리 설치 (cos.jar)
		// 4) MultipartRequest객체 생성(업로드)
		MultipartRequest multi
						= new MultipartRequest(
								request,
								realPath, 
								maxSize, 
								"utf-8", 
								new DefaultFileRenamePolicy()
								);
		System.out.println(" M : 첨부파일 업로드 완료! ");
		
		// 전달정보 저장 (DTO)
		GoodsDTO dto = new GoodsDTO();
		// gno는 안받아옴
		dto.setCategory(multi.getParameter("category"));
		dto.setName(multi.getParameter("name"));
		dto.setContent(multi.getParameter("content"));
		dto.setSize(multi.getParameter("size"));
		dto.setColor(multi.getParameter("color"));
		dto.setAmount(Integer.parseInt(multi.getParameter("amount")));
		dto.setPrice(Integer.parseInt(multi.getParameter("price")));
		dto.setBest(0); // 0 : 일반상품, 1 : 인기상품
		
		String img = multi.getFilesystemName("file1")+","
				+multi.getFilesystemName("file2")+","
				+multi.getFilesystemName("file3")+","
				+multi.getFilesystemName("file4"); 
		System.out.println(" M : img : "+img);
		dto.setImage(img);
		// DAO - 상품등록메서드 
		AdminGoodsDAO dao = new AdminGoodsDAO();
		dao.insertGoods(dto);
		
		// 페이지이동
		ActionForward forward = new ActionForward();
		forward.setPath("./AdminGoodsList.ag");
		forward.setRedirect(true);
		return forward;
	}

}
