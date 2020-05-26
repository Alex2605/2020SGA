/*
$(document).ready(function () {
}); 
*/

function updateServico() {
	var select = document.getElementById('tipoServico');
	var tipoServico = select.options[select.selectedIndex];
	moment.locale('pt-BR');
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
                    return '<a class="btn btn-success btn-sm btn-block" href="/especialidades/editar/'+ 
                    	id +'" role="button"><i class="fas fa-edit"></i></a>';
                }
            },
            {orderable: false,
             data: 'id',
                "render": function(id) {
                    return '<a class="btn btn-danger btn-sm btn-block" href="/especialidades/excluir/'+ 
                    	id +'" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fas fa-times-circle"></i></a>';
                }               
            }
        ]
    });
}

