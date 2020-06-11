$(document).ready(function () {
	moment.locale('pt-BR');
    var table = $('#table-reservas').DataTable({
    	searching: true,
    	order: [[ 1, "asc" ]],
    	lengthMenu: [5, 10, 15, 25],
        processing: true,
        serverSide: true,
        responsive: true,
        ajax: {
            url: '/agendamentos/datatables/server',
            data: 'data'
        },
        
        columns: [
            {data: 'id'},
            {data: 'tipoServico'},
            {data: 'titulo'},
            {data: 'nome'},
            {data: 'dataInicio', render: 
				function(dataInicio) {
					return moment( dataInicio ).format('LL'); 
				},
				orderable : false,
			},
			{data: 'dataFim', render: 
				function(dataFim) {
					return moment( dataFim ).format('LL'); 
				},
				orderable : false,
			}
        ]
    });
});