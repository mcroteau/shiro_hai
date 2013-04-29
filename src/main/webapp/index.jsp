<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>

<body>

<div class="container">

	<div class="row">
		
		<div class="span6" >
			<h1>Welcome</h1>
		</div>
		
		<div class="span1"></div>
		
		<div class="span5" id="create">

		</div>
		
	</div>
	
</div>




<script type="text/javascript">
	
	var $container = $('.container'),
		$list = $('#list'),
	    $create = $('#create'),
	    $button = $('#newtype'),
	    $template = $('#template').text();
	
	var $name = $('#name'),
		$quantity = $('#quantity'),
		$price = $('#price');
		
		
	$(document).ready(function(){
		
		$container.click(delegate)
		
		function delegate(event){
			console.log('delegate');
			var $target = $(event.target);
			if($target.hasClass('addtype')){
				save();
			}
			
			if($target.hasClass('icon-remove')){
				deleteType($target.attr('data-id'));
			}
			
		}
		
		
		
		function deleteType(id){
			$.ajax({
				url         : 'api/type/' + id,
				type        : "delete",
				contentType : "application/json",
				success     : loadTypes
			});			
		}
		
		
		
		function save(){
			if($name.val() != ""
				&& $quantity.val() != ""
					&& $price.val() !=""){
				
				var data = {
					id       : 0,
					name     : $name.val(),
					quantity : parseInt($quantity.val()),
					price    : parseInt($price.val())
				};
				
				$.ajax({
					url         : 'api/type',
					type        : "post",
					data        : JSON.stringify(data),
					dataType    : "text",
					contentType : "application/json",
					success     : loadTypes
				});
				
			}else{
				alert('errors on form');
			}
		}
		
		
		function updateType(event){
			
			var $target = $(event.target);
			var targetId = $target.attr('id');
			var targetValue = $target.html();
			var $row = $($target.parent('tr'));
			var id = $row.attr('data-id');
			var data = getRowData($row);
				
			console.log(targetId, lookup[targetId], targetValue);
			
			if(lookup[targetId] != targetValue){
				update(id, data).done(loadTypes).fail(loadTypes);
			}	
				
		}
		
		
		function update(id, data){
			console.log('update....');
			return $.ajax({
				url         : 'api/type/' + id,
				type        : "put",
				data        : JSON.stringify(data),
				dataType    : "text",
				contentType : "application/json"
			});	
		}
		
		
		function getRowData($row){
			return {
				id       : 0,
				name     : $row.find('.name').html(),
				quantity : $row.find('.quantity').html(),
				price    : $row.find('.price').html(),
			};
		}
		
		
		var lookup = {};		
		function loadTypes(){
			$list.empty();
			lookup = {};
			$.ajax({
				url : 'api/type',
				success : function(data){
					var json = JSON.parse(data);
					$(json).each(function(index, type){
						var html = Mustache.to_html($template, type);
						$list.append(html);
						addToLookup(type);
					});		
					$list.find('td.editable').focusout(updateType);
					clearForm();
				}
			});			
		}
		
		
		
		function addToLookup(type){
			lookup["name" + type.id] = type.name;
			lookup["quantity" + type.id] = type.quantity;
			lookup["price" + type.id] = type.price;
		}
		
		function clearForm(){
			console.log($('input '))
			$('input ').val("");
		}
		
		
		loadTypes();

		
	});


</script>
	
</body>
</html>