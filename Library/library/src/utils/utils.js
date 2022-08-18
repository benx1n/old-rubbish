//检查登录
function loginCheck() {
	let userInfo = uni.getStorageSync("userInfo")
	if (userInfo == null || userInfo === '') {
		console.log("未登录");
		setTimeout(function() {
			uni.redirectTo({
				url: "/pages/login/login"
			})
		}, 100)

	}
}

function getStatus(status) {
	let json = {}
	if (status === "0") {
		json.color = "orange"
		json.statusName = "待借"
	}
	if (status == "1") {
		json.color = "blue"
		json.statusName = "借阅中"
	}
	if (status == "2") {
		json.color = "red"
		json.statusName = "逾期"
	}
	if (status == "3") {
		json.color = "red"
		json.statusName = "逾期归还"
	}
	if (status == "4") {
		json.color = "green"
		json.statusName = "归还"
	}
	if (status == "7") {
		json.color = "green"
		json.statusName = "待确认归还"
	}
	return json;

}
export {
	loginCheck,getStatus
}
