function update(userId, event) {
	event.preventDefault(); //폼태그 액션 막기
	let data = $("#profileUpdate").serialize();

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