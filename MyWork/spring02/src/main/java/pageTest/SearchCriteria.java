package pageTest;

import lombok.Data;

@Data
public class SearchCriteria extends Criteria {
	private String searchType = "all"; // 컬럼을 선택
	private String keyword;
	// checkbox 와 같은 타입은 배열의 형태로 전달됨
	private String[] check;
} // class
