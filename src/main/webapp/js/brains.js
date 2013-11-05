$(document).ready(function(){

	
	var sidebar = $('.sidebar');
	var container = $('.container');
	
	// toggle sidebar on click
	$('.toggle-aside').on('click',function(e){
		e.preventDefault();
		if(sidebar.hasClass('sidebar-open')){
			sidebar.removeClass('sidebar-open').addClass('sidebar-closed');
			container.removeClass('container-margin').addClass('container-nomargin');
		}
		else{
			sidebar.removeClass('sidebar-closed').addClass('sidebar-open');
			container.removeClass('container-nomargin').addClass('container-margin');
		}
	});

});