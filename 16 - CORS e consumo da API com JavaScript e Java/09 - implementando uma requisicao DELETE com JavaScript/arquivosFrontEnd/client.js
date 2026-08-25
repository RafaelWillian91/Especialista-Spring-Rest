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
                $("#campo-descricao").val("");
            },
            
            error: function(error) {
                if(error.status == 400){
                    var problem = JSON.parse(error.responseText);
                    alert(problem.userMessage);
                }else{
                    alert("Erro ao cadastrar forma de pagamento!");
                }
            }
    });
}



function excluir(formaPagamento){
   
      $.ajax( {
         url:"http://localhost:8080/formas-pagamento/" + formaPagamento.id,
         type:"delete",
         
         success(response){
            alert("Forma de pagamento removida: " + formaPagamento.descricao);
            consultarFormaPagamento();
            
         },
         
         error: function(error) {
                if (error.status >= 400 && error.status <= 499) {
                    var problem = JSON.parse(error.responseText);
                    alert(problem.userMessage);
                }else{
                    alert("Erro ao excluir forma de pagamento!");
                }
            }
   
      });
   }




function preencherTabela (formasPagamento) {
    $("#tabela tbody tr").remove();
    
    $.each(formasPagamento, function(i, formasPagamento){
        var linha = $("<tr>");
        
        var linkAcao =  $("<a href='#'>")
               .text("Excluir")
               .click(function(event){
                  event.preventDefault();
                  excluir(formasPagamento);
               });
        
        linha.append(
            $("<td>").text(formasPagamento.id),
            $("<td>").text(formasPagamento.descricao),
            $("<td>").append(linkAcao)
        );
        
        linha.appendTo("#tabela");
    });
    
}


$("#btn-consultar").click(consultarFormaPagamento);
$("#btn-cadastrar").click(cadastrar);