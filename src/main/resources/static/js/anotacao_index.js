function novaCategoria() {
	let nova = prompt("Digite o nome da nova categoria");
	if (nova.length > 0) {
		window.location.replace("/salvarCategoria/" + nova);
	} else {
		alert("Nome é obirgatório");
	}
}

function mudarCategoria() {
	let categoriaSelecionada = document.getElementById("categ").value;
	window.location.replace("/categoriaSelecionada/" + categoriaSelecionada);
}

document.addEventListener('DOMContentLoaded', function() {
	var elems = document.querySelectorAll('select');
    var instances = M.FormSelect.init(elems);
});

function removerAnotacao(id) {
	
	if (confirm("Deseja realmente apagar a anotação?")) {
		window.location.replace("/apagar/" + id);
	}
}