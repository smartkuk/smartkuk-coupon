/**
 */
angular.module('app').controller('CouponCtrl', function($scope, $log, CouponService) {
	
	$scope.views = {
			userEmail: null
	};
	
	$scope.test = function () {
		$log.debug('test 시작');
		CouponService.test().then(function success(response) {
			$log.debug('response:', response);
		}, function error(error) {
			$log.error('error:', error);
		});
	};
	
	$scope.test2 = function () {
		$log.debug('test 시작');
		CouponService.test2().then(function success(response) {
			$log.debug('response:', response);
		}, function error(error) {
			$log.error('error:', error);
		});
	};
});