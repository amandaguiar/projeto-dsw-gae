function ajaxCall(url, postData, async, success) {
	$.ajax({
		url: url,
		type: 'POST',
		dataType: 'json',
		data: postData,
		async: async,
		success: function (data) {
			if (data.Result == "OK")
				success(data.data);
			else
				alert(data.message);
		},
		error: function () {
			alert("Erro ao conectar com o servidor");
		}
	});
}

function ajaxCallNoError (url, postData, async, success) {
	$.ajax({
		url: url,
		type: 'POST',
		dataType: 'json',
		data: postData,
		async: async,
		success: function (data) {
			if (data.Result == "OK")
				success(data.data);
		},
		error: function () {
		}
	});
}

function capturaComponentes(controleSistema, controleComponente, selecionado) 
{
	ajaxCall("/common/listaComponentes.do", "sistema=" + $("#" + controleSistema).val(), true, function(data) {
		s = "";			
		$(data).each(function() { s = s + "<option value=\"" + this + "\">" + this + "</option>"; });
		$('#' + controleComponente).html(s);
		$('#' + controleComponente).val(selecionado);
		$('#' + controleComponente).trigger('change');
	});
}