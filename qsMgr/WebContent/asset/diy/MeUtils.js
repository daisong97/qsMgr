$.Alert = function(msg, fn) {
	$.messager.alert('提示', msg, '', fn);
}
String.isEmpty = function(s) {
	if (s == null ||  $.trim(s) == '') {
		return true;
	}
	return false;
}
function doResult(data) {
	if (data.status == 1) {
		$.Alert(data.msg, function() {
			window.location.reload();
		});
	} else {
		$.Alert(data.msg);
	}
}
function btDoResult(data) {
	if (data.status == 1) {
		$.btAlert(data.msg, function() {
			//window.location.reload();
		});
	} else {
		$.btAlert(data.msg);
	}
}
$.btAlert = function(msg, fn) {
	var alertId = "_____alertModel_1";
	$("#" + alertId).remove();
	var html = +'<div id="alertModal" class="modal fade bs-example-modal-sm" tabindex="-1">'
			+ '	<div class="modal-dialog modal-sm"> '
			+ '<div class="modal-content"> '
			+ data.msg
			+ '<div class="modal-footer">'
			+ '    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>'
			+ '</div>' + '</div>' + '</div>';
	$("body").append(html);
	$('#' + "alertId").modal('show');
	$('#' + alertId).on('hidden.bs.modal', fn);
}