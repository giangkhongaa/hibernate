$(document).ready(function() {
	var timer;
	$('#search').keyup(function(e) {

		clearTimeout(timer);
		timer = setTimeout(function() {
			var userName = $('#search').val();
			// delete then keyup
			$('tr[id*="tr_"]').remove();
			// case char fisrt is space
			if (userName.replace(" ", "").length == 0) {
				return;
			}

			if (userName.length > 0) {
				loadTable(userName);
			} else {// delete data display
				$('tr[id*="tr_"]').remove();
			}
		}, 1000);

	});
});

function loadTable(userName) {
	$
			.ajax({
				dataType : "json",
				// url mapping with controller
				url : 'http://localhost:8181/spring/student/accountsearch/'
						+ userName,
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'GET',
				data : userName,
				success : function(responce) {
					if (responce.length == 0) {
						alert("Không tìm thấy dữ liệu");
					}
					$.each(
									responce,
									function(key, user) {
										var htmlResut = "<tr" + " id ='"
												+ "tr_" + user.id + "' " + ">";
										htmlResut += "<td" + " id ='" + "tdID_"
												+ user.id + "' " + ">"
												+ user.id + "</td>";
										htmlResut += "<td" + " id ='"
												+ "tdName_" + user.id + "' "
												+ ">" + new Option(user.username).innerHTML + "</td>";
										htmlResut += "<td" + " id ='"
												+ "tdPass_" + user.id + "' "
												+ "class='passWidth'>" + new Option(user.password).innerHTML + "</td>";
										htmlResut += "<td" + " id ='"
												+ "tdRole_" + user.id + "' "
												+ ">" + user.role + "</td>";
										htmlResut += "<td"
												+ " id ='"
												+ "tdBtn_"
												+ user.id
												+ "' "
												+ ">"
												+ "<input id="
												+ user.id
												+ " type='submit' value='Edit' onclick='editData("
												+ user.id + ")'>" + "</td>";
										htmlResut += "</tr>";
										$('#tbody').append(htmlResut);
									})
				},
				error : function() {
					alert("Không tìm thấy thông tin!!!");
				}
			});
}

function editData(key) {
	var nameButton = $('#' + key).val();
	if (nameButton == 'Update') {
		var data = {
			id : $("#tdID_" + key).text(),
			username : $("#inputName_" + key).val(),
			password : $("#inputPass_" + key).val(),
			role : $("#gate").val()
		};

		var json = JSON.stringify(data);
		$.ajax({
					type : "POST",
					url : 'http://localhost:8181/spring/student/update/user',
					data : json,
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					dataType : 'json',
					success : function(user) {
						
						$('#tdName_' + key).html(
								"<td" + " id ='" + "tdName_" + user.id + "' "
										+ ">" + new Option(user.username).innerHTML + "</td>");
						$('#tdPass_' + key).html(
								"<td" + " id ='" + "tdPass_" + user.id + "' "
										+ "class='passWidth' >" +new Option(user.password).innerHTML  + "</td>");
						$('#tdRole_' + key).html(
								"<td" + " id ='" + "tdRole_" + user.id + "' "
										+ ">" + user.role + "</td>");
						$('#tdBtn_' + key)
								.html(
										"<td>"
												+ "<input id="
												+ user.id
												+ " type='submit' value='Edit' onclick='editData("
												+ user.id + ")'>" + "</td>");

					},
					error : function(e) {
						alert('Error: ' + e);
					}
				});
	} else {
		$('#tdName_' + key).html(
				"<td" + " id ='" + "tdName_" + key + "' " + ">" + "<input maxlength='20' " 
						+ " id ='" + "inputName_" + key + "' "
						+ "type='text' value=" + new Option($('#tdName_' + key).text()).innerHTML 
						+ " >" + "</td>");
		$('#tdPass_' + key).html(
				"<td" + " id ='" + "tdPass_" + key + "' " + ">" + "<input"
						+ " id ='" + "inputPass_" + key + "' "
						+ " class='passWidth' type='text' value="
						+ new Option($('#tdPass_' + key).text()).innerHTML + " >" + "</td>");

		var roleSelect = $('#tdRole_' + key).text();
		$('#tdRole_' + key).html(
				"<td" + " id ='" + "tdRole_" + key + "' " + ">"
						+ "<select id='gate'>"
						+ "<option value='ROLE_ADMIN'>ROLE_ADMIN</option>"
						+ "<option value='ROLE_USER'>ROLE_USER</option>"
						+ "</select>" + "</td>");
		$("#gate").val(roleSelect);

		$('#' + key).val('Update');
	}

}
