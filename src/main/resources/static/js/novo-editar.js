var idAnotacao;
var idUsuario;
var anotacao;
var btnAcao = document.querySelector("#acao");
//======================================================

//======================================================
//Salvar/Editar dados
function validar(anotacao) {
    if (anotacao.titulo.length == 0) {
        alert("Informe o titulo da anotação");
        return false;
    } else if (anotacao.descricao.length == 0) {
        alert("Informe a descrição da anotação");
        return false;
    } else  {
        return true;
    }
}

function savarEditarDados() {
    var anotacao = new Object();
    anotacao.titulo = document.getElementById("tit").value;
    anotacao.descricao = document.getElementById("descricao").value;
    anotacao.idUsuario = idUsuario;
    if (validar(anotacao)) {
        var request = new XMLHttpRequest();

        if (idAnotacao > 0) {
            //Atualização
            var endPoint = "http://localhost:9090/anotacao/" + idAnotacao;
            request.open("PUT", endPoint, true);
            request.setRequestHeader('Content-type','application/json; charset=utf-8');
            request.onload = function() {
                if (request.status == 200) {
                    alert("Dados atualizados com sucesso");
                    window.location.href="anotacao.html?"+ idUsuario;
                } else {
                    alert("Erro interno...");
                }
            }
            var json = JSON.stringify(anotacao);
            request.send(json);            
        } else {
            //Nova anotação
            var endPoint = "http://localhost:9090/anotacao";
            request.open("POST", endPoint, true);
            request.setRequestHeader('Content-type','application/json; charset=utf-8');
            request.onload = function() {
                if (request.status == 201) {
                    alert("Dados salvo com sucesso");
                    window.location.href="anotacao.html?"+ idUsuario;
                } else {
                    alert("Erro interno...");
                }
            }
            var json = JSON.stringify(anotacao);
            request.send(json);
        }
    }
}

btnAcao.onclick = savarEditarDados;

//======================================================
function recuperarDados() {
    var endPoint = "http://localhost:9090/anotacao/unica/" + idAnotacao;
    var request = new XMLHttpRequest();
    request.open("GET", endPoint, true);
    request.onload = function (e) {
        if (request.status == 200) {
            anotacao = JSON.parse(request.responseText);
            document.getElementById("tit").value = anotacao.titulo;
            document.getElementById("descricao").value = anotacao.descricao;
            $(document).ready(function() {
                M.updateTextFields();
              });
        }
    }
    request.send();
}

function montarTela() {
    var titulo = document.getElementById("titulo");
    var i = document.createElement("i");
    i.setAttribute("class", "material-icons prefix");
    i.appendChild(document.createTextNode("attach_file"));
    titulo.appendChild(i);

    var acao = document.getElementById("acao");

    if (idAnotacao == 0) {
        titulo.appendChild(document.createTextNode(" Nova anotação"));
        acao.setAttribute("title", "Salvar anotação");
        acao.appendChild(document.createTextNode("Salvar"));
    } else {
        titulo.appendChild(document.createTextNode(" Atualizar anotação"));
        acao.setAttribute("title", "Atualizar anotação");
        acao.appendChild(document.createTextNode("Atualizar"));
        recuperarDados();
    }
}

function recuperarId() {
    var url = window.location.search.replace("?", "");
    ids = url.split("?");
    idAnotacao = ids[0];
    idUsuario = ids[1];
}

function iniciar() {
    recuperarId();
    montarTela();
}

iniciar();