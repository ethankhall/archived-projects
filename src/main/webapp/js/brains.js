$(document).ready(function(){

	var alerts = $('.icon-alerts');
	var sidebar = $('.sidebar');
	var container = $('.container');
	var notifications = $('.notifications');
	
	$('.notifications').hide();	
	
	
	alerts.click(function(e) {
		e.preventDefault();
		notifications.toggle();
	});
	notifications.mouseleave(function(e) {
		e.preventDefault();
		notifications.hide();
	});
	
    $('.toggle-aside').click(function() {
        sidebar.toggleClass('slide-in');
		container.toggleClass('slid');
    });	

});

