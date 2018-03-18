/**
 * HTTP 인터셉트
 */
app.factory('HttpInterceptor', function($rootScope, $q, $window) {
	
	return {
		'request': function(config) {
			return config;
		},
		'requestError': function(rejection) {
			return $q.reject(rejection);
		},
		'response': function(response) {
			return response;
		},
		'responseError': function(error) {
			
			if(error.data.path && error.data.path != '/coupons/dummy' && error.data.message) {
				//예외를 받으면 메시지를 추출하고 사용자에게 알려준다.
				$window.alert(error.data.message);
			}
			
			return $q.reject(error);
		}
	};
})
.config(function($httpProvider) {
	$httpProvider.interceptors.push('HttpInterceptor');
});