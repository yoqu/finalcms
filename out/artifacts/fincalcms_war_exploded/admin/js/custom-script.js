/*================================================================================
	Item Name: Materialize - Material Design Admin Template
	Version: 3.1
	Author: GeeksLabs
	Author URL: http://www.themeforest.net/user/geekslabs
================================================================================

NOTE:
------
PLACE HERE YOUR OWN JS CODES AND IF NEEDED.
WE WILL RELEASE FUTURE UPDATES SO IN ORDER TO NOT OVERWRITE YOUR CUSTOM SCRIPT IT'S BETTER LIKE THIS. */

var FinalDialog = {
	show:function(){
		$("body").append('<div class=lean-overlay style="background: rgba(0,0,0,0.1); display: block;"><div style="position: fixed; left: 50%;top: 50%;"><div class="preloader-wrapper big active"><div class="spinner-layer spinner-blue"><div class="circle-clipper left"><div class=circle></div></div><div class=gap-patch><div class=circle></div></div><div class="circle-clipper right"><div class=circle></div></div></div><div class="spinner-layer spinner-red"><div class="circle-clipper left"><div class=circle></div></div><div class=gap-patch><div class=circle></div></div><div class="circle-clipper right"><div class=circle></div></div></div><div class="spinner-layer spinner-yellow"><div class="circle-clipper left"><div class=circle></div></div><div class=gap-patch><div class=circle></div></div><div class="circle-clipper right"><div class=circle></div></div></div><div class="spinner-layer spinner-green"><div class="circle-clipper left"><div class=circle></div></div><div class=gap-patch><div class=circle></div></div><div class="circle-clipper right"><div class=circle></div></div></div></div></div></div>');
	},
	close:function(){
		$(".lean-overlay").remove();
	}

};