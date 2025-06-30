var requestPath = window.location.pathname.replace("#","");
var baseUrl = window.location.origin + "/" + requestPath.split('/')[1] + "/";
var shortBaseUrl = "/" + requestPath.split('/')[1] + "/";
var loaderIcon = "<img src='" + baseUrl + "resources/dist/img/loader.gif' width='40' height='40' alt='loading...'>";
var marginTop = marginBottom = "15px", marginLeft = "15px", marginRight = "15px";
var hostname = $(location).attr('hostname');

function showLoader(reference){
	var instance = $(".loader");
	if(reference) instance = $(reference).find(".loader");
	if (isMdBootstrap()) instance.html(loaderIcon).css({"text-align":'center',"color":'#2874F0', "margin-left" : marginLeft, "margin-right" : marginRight}).show();
	else instance.html(loaderIcon).css({"margin-top":marginTop,"margin-bottom":marginBottom, "margin-left" : marginLeft, "text-align":'center',"color":'#2874F0'}).show();
}

function hideLoader(reference){
	var instance = $(".loader");
	if(reference) instance = $(reference).find(".loader");
	instance.html("").hide();
}

function showButtonLoader(instance = "#btnFilter", text) {
	$(instance).attr("val", $(instance).html());
	$(instance).html("<i class=\"fa fa-spinner fa-spin\"></i>" + (text ? " " + text + "..." : ""));
	$(instance).prop("disabled", true);
}

function hideButtonLoader(instance = "#btnFilter") {
	$(instance).html($(instance).attr("val"));
	$(instance).find(".waves-ripple").remove();
	$(instance).prop("disabled", false);
}