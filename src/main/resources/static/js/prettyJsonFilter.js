/**
 * 오브젝트를 JSON 형태로 이쁘게 보여주는 필터이며, <pre></pre> 태그랑 같이 써서 보도록 한다.
 */
app.filter('prettyJson', function() {
	return function (input) {
		var blank = '';
		if(input == null) {
			return blank;
		}
		if(angular.isUndefined(input)) {
			return blank;
		}
		if(!angular.isObject(input)) {
			return input;
		}
		return angular.toJson(input, true);
	};
});