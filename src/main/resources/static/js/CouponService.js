/**
 */
angular.module('app').service('CouponService', function($http, $log) {
	
	this.test = function () {
		$log.debug('test 시작');
		return $http({
			method : 'GET',
			url : '/coupons/test'
		});
	};
	
	this.test2 = function () {
		$log.debug('test 시작');
		return $http({
			method : 'GET',
			url : '/coupons/test2'
		});
	};
});