function checkDateSearch(dateOnChange) {
	var dateStartStr = document.getElementById("datepickerStart").value;
	var dateEndStr = document.getElementById("datepickerEnd").value;
	if (!moment(dateStartStr, 'MM/DD/YYYY', true).isValid()
			&& dateStartStr != "") {
		alert("Nhập ngày sai format: MM/DD/YYYY ở from");
		document.getElementById("datepickerStart").value = "";
		return;
	}

	// check format date
	if (!moment(dateEndStr, 'MM/DD/YYYY', true).isValid() && dateEndStr != "") {
		alert("Nhập ngày sai format: MM/DD/YYYY ở to");
		document.getElementById("datepickerEnd").value = "";
		return;
	}
	// check date to larger than date from
	if (dateStartStr != "" && dateEndStr != "") {
		var dateStart = new Date(dateStartStr);
		var dateEnd = new Date(dateEndStr);
		if (dateStart.getTime() > dateEnd.getTime()) {
			alert("Ngày từ phải nhỏ hơn ngày đến!!!");
			document.getElementById(dateOnChange).value = "";
			return;
		}
	}
	// check date form null then date to no null
	if (dateStartStr == "" && dateEndStr != "") {
		alert("Vui lòng chọn ngày bắt đầu trước!!!");
		document.getElementById("datepickerEnd").value = "";
		document.getElementById("datepickerStart").value = "";
		return;
	}
}
function checkScoreSearch() {
	var scoreStr = document.getElementById("averageScore").value;
	if (scoreStr == "") {
		alert("Điểm không được bỏ trống!!!");
		document.getElementById("averageScore").value = "0.0";
	} else {
		var score = parseFloat(scoreStr);
		if (isNaN(score)) {
			alert("Điểm phải là kí tự số!!!");
			document.getElementById("averageScore").value = "0.0";
		}
	}

}
function alertName(message) {
	if (message !== 'null')
		alert(message);
}



//var entityMap = {
//	'<' : '&lt;',
//	'>' : '&gt;',
//	'"' : '&quot;',
//	"'" : '&#39;',
//	'/' : '&#x2F;',
//	'`' : '&#x60;',
//	'=' : '&#x3D;'
//};
//
//function escapeHtml() {
//	var nameStudent = document.getElementById("studentName").value;
//	var codeStudent = document.getElementById("studentCode").value;
//	var address = document.getElementById("studentAddress").value;
//	document.getElementById("studentName").value = String(nameStudent).replace(
//			/[<>"'`=\/]/g, function fromEntityMap(s) {
//				return entityMap[s];
//			});
//	document.getElementById("studentCode").value = String(codeStudent).replace(
//			/[<>"'`=\/]/g, function fromEntityMap(s) {
//				return entityMap[s];
//			});
//	document.getElementById("studentAddress").value = String(address).replace(
//			/[<>"'`=\/]/g, function fromEntityMap(s) {
//				return entityMap[s];
//			});
//
//}
