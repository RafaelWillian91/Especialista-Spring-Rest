function consultarFormaPagamento(){
    
    $.ajax(
        {
            url: "http://localhost:8080/formas-pagamento",
            type: "get",
            
            success: function(response){
            preencherTabela(response);
        }
        }
    );
    
}

function cadastrar(){
    var formaPagamentoJson = JSON.stringify ({
        "descricao":  $("#campo-descricao").val()
    });
    
   console.log(formaPagamentoJson);
    
    $.ajax({
        url: "http://localhost:8080/formas-pagamento",
            type: "post",
            data: formaPagamentoJson,
            contentType: "application/json",
            
            success: function(response) {
                alert("Foma de Pagamento adicionada!!!!");
                consultarFormaPagamento();
            },
            
            error: function(error) {
                if(error.status == 400){
                    var problem = JSON.parse(error.responseText);
                    alert(problem.userMessage);
                }else{
                    alert("Erro ao cadastrar forma de pagamento!")
                }
            }
    });
}

function preencherTabela (formasPagamento) {
    $("#tabela tbody tr").remove();
    
    $.each(formasPagamento, function(i, formasPagamento){
        var linha = $("<tr>");
        
        linha.append(
            $("<td>").text(formasPagamento.id),
            $("<td>").text(formasPagamento.descricao)
        );
        
        linha.appendTo("#tabela");
    });
    
}


$("#btn-consultar").click(consultarFormaPagamento);
$("#btn-cadastrar").click(cadastrar);