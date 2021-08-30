function update(userId, event) {
	event.preventDefault(); //폼태그 액션 막기
	let data = $("#profileUpdate").serialize(); // key value 형태로 데이터 받아올 때 씀
	//formdata를 쓰지 않는 이유는 글자나 사진을 섞어서 쓰는게 formdata이기 때문(사진 쓸 땐 formdata를 쓰고 아니라면 serialize)
	$.ajax({
		type: "PUT",
		url: `/api/user/${userId}`,
		data: data,
		contentType: "application/x-www-form-urlencoded; charset = utf-8",
		dataType: "json"
	}).done(res => {
		console.log("성공", res);
		location.href = `/user/${userId}`;
		alert("회원정보 변경이 완료되었습니다.");
	}).fail(error => {
		if (error.data == null) {
			alert(error.responseJSON.message);
		} else {
			alert(JSON.stringify(error.responseJSON.data));
		}
	});
}