//datatables - lista de médicos
$(document).ready(function() {
	moment.locale('pt-BR');
	var table = $('#table-associados').DataTable({
		searching : true,
		lengthMenu : [ 5, 10 ],
		processing : true,
		serverSide : true,
		responsive : true,
		ajax : {
			url : '/u/datatables/server/associados',
			data : 'data'
		},
		columns : [
				{data : 'id'},
				{data : 'nome'},
				{data: 'dataInscricao', render: 
					function(dataInscricao) {
						return moment( dataInscricao ).format('LLL'); 
					},
					orderable : false,
				},				
/*
				{	data : 'tipoAssociado',									 
					render : function(tipoAssociado) {
						var aux = new Array();
						$.each(tipoAssociado, function( index, value ) {
							  aux.push(value.desc);
						});
						return aux;
					},
					orderable : false,
				},
*/				
				{	orderable : false,
					data : 'id',	
					render : function(id) {
						return ''.concat('<a class="btn btn-success btn-sm btn-block"', ' ')
								 .concat('href="').concat('/associados/editar/credenciais/associado/').concat(id, '"', ' ') 
								 .concat('role="button" title="Editar" data-toggle="tooltip" data-placement="right">', ' ')
								 .concat('<i class="fas fa-edit"></i></a>');
					},
					
				},
	            {orderable: false,
		             data: 'id',
		                "render": function(id) {
		                    return '<a class="btn btn-danger btn-sm btn-block" href="/associados/excluir/'+ 
		                    	id +'" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fas fa-times-circle"></i></a>';
		                }               
		            }				
				/*,
				{data: 'data_inscricao', render: 
					function(data_inscricao) {
						return moment( data_inscricao ).format('LLL'); 
					}
			}
				
				
				{	data : 'tipo_associado',									 
					render : function(tipo_associado) {
						var aux = new Array();
						$.each(tipo_associado, function( index, value ) {
							  aux.push(value.desc);
						});
						return aux;
					},
					orderable : false,
				} 					
			
				{	data : 'ativo', 
					render : function(ativo) {
						return ativo == true ? 'Sim' : 'Não';
					}
				},
				{	data : 'perfis',									 
					render : function(perfis) {
						var aux = new Array();
						$.each(perfis, function( index, value ) {
							  aux.push(value.desc);
						});
						return aux;
					},
					orderable : false,
				},
				{	data : 'id',	
					render : function(id) {
						return ''.concat('<a class="btn btn-success btn-sm btn-block"', ' ')
								 .concat('href="').concat('/u/editar/credenciais/usuario/').concat(id, '"', ' ') 
								 .concat('role="button" title="Editar" data-toggle="tooltip" data-placement="right">', ' ')
								 .concat('<i class="fas fa-edit"></i></a>');
					},
					orderable : false
				}*/
		]
	});
	
    $('#table-associados tbody').on('click', '[id*="dp_"]', function () {
    	var data = table.row($(this).parents('tr')).data();
    	var aux = new Array();
		$.each(data.tipo_associado, function( index, value ) {
			  aux.push(value.id);
		});
//		document.location.href = '/u/editar/dados/usuario/' + data.id + '/perfis/' + aux;
    } );	
	
});	

$('.pass').keyup(function(){
	$('#senha1').val() === $('#senha2').val()
	    ? $('#senha3').removeAttr('readonly')
	    : $('#senha3').attr('readonly', 'readonly');
});








