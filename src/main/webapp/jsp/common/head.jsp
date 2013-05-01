<title>${title}</title>
<link href="/shiro_hai/js/lib/bootstrap/css/bootstrap.css" media="screen" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="/shiro_hai/js/lib/jquery/jquery.js"></script>
<script type="text/javascript" src="/shiro_hai/js/lib/mustache/mustache.js"></script>
<script type="text/javascript" src="/shiro_hai/js/lib/jstree/jstree.min.js"></script>
<script type="text/javascript" src="/shiro_hai/js/lib/json2.js"></script>

<script type="text/javascript">

Types = {};
TypesTransport = (function(){
	
	var baseUrl = '/shiro_hai/app/user/list';
	
	function get(max, offset){
		return $.ajax({
			url   : baseUrl + getParams(max, offset), 
			type  : 'get',
			cache : false
		});
	}

	function getParams(max, offset){
		if(max && offset >= 0) {
			return "?max=" + max + "&offset=" + offset;
		}
		return "";
	}
	
	return {
		get : get
	}
	
})();

</script>

