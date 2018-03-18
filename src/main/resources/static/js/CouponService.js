/**
 * 쿠폰 정보를 조회하고 정보를 저장하는 api 호출을 처리하는 서비스
 */
angular.module('app').service('CouponService', function($http, $log) {

	/**
	 * 저장된 쿠폰을 페이징 조회
	 */
	this.pagedCoupons = function (pageCondition) {
		$log.debug('페이지 조건:', pageCondition);
		return $http({
			method : 'GET',
			url : '/coupons/page' + pageCondition.query()
		});
	};
	
	/**
	 * 쿠폰을 저장(재시도 10회)
	 */
	this.saveCouponWithRetry = function (email) {
		$log.debug('이메일:', email);
		return $http({
			method : 'POST',
			url : '/coupons/retry',
			data: {
				email: email
			}
		});
	};
});