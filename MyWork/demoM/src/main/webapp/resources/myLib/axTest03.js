'use strict'

function axiMList() {
    let url = "/member/aximlist";

    axios.get(url)
        .then(response => {
            alert('** response : memberList 성공 ');
            document.getElementById('resultArea1').innerHTML = response.data;
        }).catch(err => {
            alert(`response memberList 실패 => ${err.massage}`)
        })
    document.getElementById('resultArea2').innerHTML = '';
}

// ** Ajax Member_PageList *********************
// => axiMList 에 Paging + 검색기능 추가
// => 검색조건 & Paging , Ajax 구현
//    -> 입력된 값들을 서버로 전송요청: axios
//   -> url 완성후 axios 호출

// => 1) 검색조건 입력 후 버튼클릭
//   -> jsp  문서내무의 script 구문을 외부문서로 작성 : EL Tag 적용안됨
//    ${pageMaker.makeQuery(1)} -> ?currPage=1&rowsPerPage=5 
function searchDB() {
    self.location = 'axmcri'
        + '?currPage=1&rowsPerPage=5'
        + '&searchType=' + document.getElementById('searchType').value
        + '&keyword=' + document.getElementById('keyword').value;
    axiMListCri(url);
}

// 2) searchType 을 '전체' 로 변경하면 keyword는 clear 
function keywordClear() {
    if (document.getElementById('searchType').value == "all") {
        document.getElementById('keyword').value = "";
    }
}

// 3) axios code
function axiMListCri(url2) {
    let url = "/member/" + url2;
    alert(`axiMListCri url =${url}`);

    axios.get(url)
        .then(response => {
            alert('** response : memberList 성공 ');
            document.getElementById('resultArea1').innerHTML = response.data;
        }).catch(err => {
            alert(`response memberList 실패 => ${err.massage}`)
        })
    document.getElementById('resultArea2').innerHTML = '';
}

// 1.2) idbList(id별 boardList)
// => RESTController, PathVariable 처리 ,List_Data response
// => Server : service, Sql 구문 작성
// => request : id를 path로 전송
// => Response
//      -> 성공 : 반복문, Table 로 List 출력문 완성, resultArea2 에 출력
//      -> 출력자료의 유/무 : Server 에서 status로 502로 처리
//      -> 실패 : resultArea2 clear, alert 으로 에러메세지 출력
function idbList(id) {
    let url = "/rest/idblist/" + id;
    axios.get(url
    ).then(response => {
        alert("** 성공 => resultArea2 에 List 작성");
        console.log("response.data = " + response.data);
        let listData = response.data;
        let resultHtml = `
        <table style="width:100%">
<tr bgcolor="Aquamarine">
	<th>Seq</th><th>Title</th><th>ID</th><th>RegDate</th><th>조회수</th>
</tr>
`
        for (let b of response.data) {
            resultHtml += `
            <tr>
                <td>${b.seq}</td>
                <td>
                    <div style="text-align:left;">
                        <a href="detail?jCode=D&seq=${b.seq}">${b.title}</a>
                        ${b.title}
                    </div >
                </td >
                <td>${b.id}</td><td>${b.regdate}</td><td>${b.cnt}</td>
            </tr >
            `
        }
        resultHtml += '</table>';
        document.getElementById('resultArea2').innerHTML = resultHtml;
    }).catch(err => {
        if (err.response.status == "502") {
            alert("조회할 자료 없음")
            document.getElementById('resultArea2').innerHTML = "";
        } else
            alert("시스템 오류");
    });
}

//	2.2) axiDelete('${m.id}')
//		=> 요청명 : "rest/axidelete/" PathVariable 적용
//		=> response : 성공/실패 여부만 전달받음, 그러므로 RESTController로
//		=> 성공 : Deleted 로 변경, onclick 이벤트 해제
function axiDelete(id){
	let url = "/rest/axidelete/"+id;
	axios.delete(url
	).then(response => {
		
	}).catch(err => {
		if(err.response.status == '502')
			alert(err.response.data)
		else 
			alert('시스템 오류')
	})
}

function showMemberList(event){
	console.log(event.pageX);
	console.log(event.pageY);
	let url = "/rest/aximlista/"+id;
}