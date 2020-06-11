$(document).ready(function () {
  $("#exibe-servicos").hide();
}); 


function updateServico() {
    $("#exibe-servicos").show();
	var select = document.getElementById('tipoServico');
	var tipoServico = select.options[select.selectedIndex];
	moment.locale('pt-BR');
	console.log(">> "+tipoServico.value) ;
	if (!tipoServico.value) 
		{
		console.log("Atributo vazio");
		$("#exibe-servicos").hide();
		} 
	else {
		    var table = $('#table-especializacao').DataTable({
		    	destroy: true,
		    	searching: true,
		    	order: [[ 1, "asc" ]],
		    	lengthMenu: [5, 10, 15, 25],
		        processing: true,
		        serverSide: true,
		        responsive: true,
		        ajax: {
		            url: '/servicos/datatables/server/teste/'+tipoServico.value,
		            data: 'data'
		        },
		        columns: [
		            {data: 'id'},
		            {data: 'titulo'},
		            {orderable: false, 
		             data: 'id',
		                "render": function(id) {
		                    return '<a class="btn btn-success btn-sm btn-block" href="/servicos/editar/'+ 
		                    	id +'" role="button"><i class="fas fa-edit"></i></a>';
		                }
		            },
		            {orderable: false,
		             data: 'id',
		                "render": function(id) {
		                    return '<a class="btn btn-danger btn-sm btn-block" href="/servicos/excluir/'+ 
		                    	id +'" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fas fa-times-circle"></i></a>';
		                }               
		            }
		        ]
		    });
	     }
}

