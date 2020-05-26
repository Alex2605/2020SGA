$(document).ready(function () {
	moment.locale('pt-BR');
    var table = $('#table-dependentes').DataTable({
    	searching: true,
    	order: [[ 1, "asc" ]],
    	lengthMenu: [5, 10, 15, 25],
        processing: true,
        serverSide: true,
        responsive: true,
        ajax: {
            url: '/dependentes/datatables/server',
            data: 'data'
        },
        columns: [
            {data: 'id'},
            {data: 'nome'},
            {data: 'dataNascimento'},
            {data: 'tipoDependente'},
            {orderable: false, 
             data: 'id',
                "render": function(id) {
                    return '<a class="btn btn-success btn-sm btn-block" href="/dependentes/editar/'+ 
                    	id +'" role="button"><i class="fas fa-edit"></i></a>';
                }
            },
            {orderable: false,
             data: 'id',
                "render": function(id) {
                    return '<a class="btn btn-danger btn-sm btn-block" href="/dependentes/excluir/'+ 
                    	id +'" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fas fa-times-circle"></i></a>';
                }               
            }
        ]
    });
});